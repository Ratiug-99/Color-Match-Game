package com.ratiug.dev.colormatchgame.fragments;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.ratiug.dev.colormatchgame.R;
import com.ratiug.dev.colormatchgame.SharedPreferencesHelper;
import com.ratiug.dev.colormatchgame.TimerService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.TimeZone;

import static android.content.Context.VIBRATOR_SERVICE;
import static java.lang.String.valueOf;

public class GameFragment extends Fragment {
    public static final String TAG = "DBG | GaeFragment";

    public static final String KEY_BROADCAST_RECEIVER_TICK = "com.ratiug.dev.colormatchgame.tick.timer";
    public static final String KEY_TIME_VALUE_STRING = "KEY_TIME_VALUE";
    public static final String KEY_TIME_VALUE_LONG = "KEY_TIME_VALUE";
    public int rightAnswer, wrongAnswer, record;
    int color1;
    int valueColor1;
    int oldColor1 = -1;
    int oldValueColor1 = -1;
    Boolean newRecord = false;
    SharedPreferencesHelper mSharedPreferencesHelper;
    int[] colors;
    String[] color_names, countColorOptions;
    TimerService timerService;
    ServiceConnection serviceConnection;
    BroadcastReceiver broadcastReceiver;
    int selectedOptionsColor;
    private TextView tvNameRight, tvNameWrong, tvRightValue, tvWrongValue, tvRecordValue, tvColor1, tvTimeLeft;
    private Button btnYes, btnNo;
    private ProgressBar pbTimeLeft;
    private boolean differentValues = true;
    private boolean correctAnswer = false;
    private boolean vibrationStatus;
    ////
    private int wrongVibration = 250;
    private int gameOverVibration = 1000;
    private int finishVibration = 750;
    ///

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvNameRight = getActivity().findViewById(R.id.tv_name_right);
        tvNameWrong = getActivity().findViewById(R.id.tv_name_wrong);
        tvRightValue = getActivity().findViewById(R.id.tv_right_value);
        tvWrongValue = getActivity().findViewById(R.id.tv_wrong_value);
        tvRecordValue = getActivity().findViewById(R.id.tv_record_value);

        colors = getContext().getResources().getIntArray(R.array.colors);
        color_names = getContext().getResources().getStringArray(R.array.color_names);
        countColorOptions = getContext().getResources().getStringArray(R.array.count_colors_for_select);
        mSharedPreferencesHelper = new SharedPreferencesHelper(getContext());
        record = mSharedPreferencesHelper.getRecord();
        selectedOptionsColor = Integer.parseInt(countColorOptions[mSharedPreferencesHelper.getCountColors()]);
        vibrationStatus = mSharedPreferencesHelper.getVibrationStatus();

        showRightWrongAnswerViews();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        tvColor1 = view.findViewById(R.id.tv_color_one);
        btnYes = view.findViewById(R.id.btnYes);
        btnNo = view.findViewById(R.id.btnNo);
        tvTimeLeft = view.findViewById(R.id.tv_time_left);
        pbTimeLeft = view.findViewById(R.id.pb_time_left);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (correctAnswer) {
                    plusRight();
                } else {
                    plusWrong();
                }
                setColorAndValues();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!correctAnswer) {
                    plusRight();
                } else {
                    plusWrong();
                }
                setColorAndValues();
            }
        });

        return view;
    }

    private void setColorAndValues() {
        Random rnd = new Random();
        boolean choice = rnd.nextBoolean();
        if (choice) {
            do {
                int temp = rnd.nextInt(selectedOptionsColor);
                differentValues = temp != oldColor1;
                color1 = temp;
                valueColor1 = temp;
                oldColor1 = color1;
                oldValueColor1 = valueColor1;
            } while (!differentValues);
        } else {
            do {
                color1 = rnd.nextInt(selectedOptionsColor);
                valueColor1 = rnd.nextInt(selectedOptionsColor);
                if (color1 != oldColor1 || valueColor1 != oldValueColor1) {
                    differentValues = true;
                    oldColor1 = color1;
                    oldValueColor1 = valueColor1;
                } else {
                    differentValues = false;
                }
            } while (!differentValues);
        }
        correctAnswer = color1 == valueColor1;
        Log.d(TAG, "setColorAndValues: " + color1 + " " + valueColor1);
        tvColor1.setText(color_names[valueColor1]);
        tvColor1.setTextColor(colors[color1]);
    }

    private void plusRight() {
        rightAnswer = rightAnswer + 1;

        if (rightAnswer > record) {
            setRecord();
        }
        tvRightValue.setText(valueOf(rightAnswer));
    }

    private void plusWrong() {
        wrongAnswer = wrongAnswer + 1;
        if (vibrationStatus) {
            makeVibration(wrongVibration);
        }
        if (wrongAnswer == 10) {
            GameOverFragment gameOverFragment = new GameOverFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container, gameOverFragment)
                    .addToBackStack(null)
                    .commit();
            if (vibrationStatus) {
                makeVibration(gameOverVibration);
            }
        }
        tvWrongValue.setText(valueOf(wrongAnswer));
    }

    private void setRecord() {
        newRecord = true;
        record = rightAnswer;
        tvRecordValue.setText(valueOf(record));
        mSharedPreferencesHelper.setRecord(record);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        hideRightWrongAnswerViews();
    }


    @Override
    public void onStop() {
        getActivity().unregisterReceiver(broadcastReceiver);
        getActivity().stopService(new Intent(getContext(), TimerService.class));
        hideRightWrongAnswerViews();
        super.onStop();
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume: ");
        showRightWrongAnswerViews();

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String value = convertMlsToCorrectDateFormateString(intent.getLongExtra(KEY_TIME_VALUE_LONG, 0));

                Log.d(TAG, "onReceive: " + value);
                long timeLeft = intent.getLongExtra(KEY_TIME_VALUE_LONG, 0);
                if (!Objects.equals(intent.getStringExtra(KEY_TIME_VALUE_STRING), "Finish")) {
                    tvTimeLeft.setText(getContext().getText(R.string.time_left_00_00) + value);
                    pbTimeLeft.setProgress((int) timeLeft);
                } else {
                   if (vibrationStatus) {
                       makeVibration(finishVibration);
                   }
                    btnNo.setEnabled(false);
                    btnYes.setEnabled(false);
                    if (newRecord) {
                        NewRecordFragment recordFragment = new NewRecordFragment();
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fl_container, recordFragment)
                                .addToBackStack(null)
                                .commit();
                    } else {
                        FinishGameFragment finishGameFragment = new FinishGameFragment();
                        Bundle args = new Bundle();
                        args.putInt(FinishGameFragment.KEY_SCORE, rightAnswer);
                        finishGameFragment.setArguments(args);
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fl_container, finishGameFragment)
                                .addToBackStack(null)
                                .commit();
                    }
                }
            }
        };
        getActivity().registerReceiver(broadcastReceiver, new IntentFilter(KEY_BROADCAST_RECEIVER_TICK));
        setColorAndValues();
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                timerService = ((TimerService.MyBinder) iBinder).getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
            }
        };
        Intent mIntent = new Intent(getContext(), TimerService.class);
        getActivity().bindService(mIntent, serviceConnection, 0);
        getContext().startService(mIntent);
        super.onResume();
    }

    private void makeVibration(int timeToVibration) {
        if (Build.VERSION.SDK_INT >= 26) {
            ((Vibrator) getActivity().getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(timeToVibration, 100));
        } else {
            ((Vibrator) getActivity().getSystemService(VIBRATOR_SERVICE)).vibrate(100);
        }
    }

    private String convertMlsToCorrectDateFormateString(long mls) {
        SimpleDateFormat formatter = new SimpleDateFormat("mm:ss", Locale.UK);
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = new Date(mls);
        return formatter.format(date);
    }

    private void showRightWrongAnswerViews() {
        tvNameRight.setVisibility(View.VISIBLE);
        tvNameWrong.setVisibility(View.VISIBLE);
        tvRightValue.setVisibility(View.VISIBLE);
        tvWrongValue.setVisibility(View.VISIBLE);
    }

    private void hideRightWrongAnswerViews() {
        tvNameRight.setVisibility(View.GONE);
        tvNameWrong.setVisibility(View.GONE);
        tvRightValue.setVisibility(View.GONE);
        tvWrongValue.setVisibility(View.GONE);

        rightAnswer = 0;
        wrongAnswer = 0;

        tvRightValue.setText(valueOf(rightAnswer));
        tvWrongValue.setText(valueOf(wrongAnswer));
    }
}