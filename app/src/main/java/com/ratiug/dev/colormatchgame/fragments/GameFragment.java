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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.ratiug.dev.colormatchgame.R;
import com.ratiug.dev.colormatchgame.SharedPreferencesHelper;
import com.ratiug.dev.colormatchgame.TimerService;

import java.util.Random;

import static android.content.Context.VIBRATOR_SERVICE;
import static java.lang.String.valueOf;

public class GameFragment extends Fragment {
    public static final String TAG = "DBG | GaeFragment";

    public static final String KEY_BROADCAST_RECEIVER_TICK = "com.ratiug.dev.colormatchgame.tick.timer";
    public static final String KEY_TIME_VALUE = "KEY_TIME_VALUE";
    public int rightAnswer, wrongAnswer, record;
    int color1;
    int valueColor1;
    int oldColor1 = -1;
    int oldValueColor1 = -1;
    Boolean newRecord = false;
    SharedPreferencesHelper mSharedPreferencesHelper;
    int[] colors;
    String[] color_names;
    TimerService timerService;
    ServiceConnection serviceConnection;
    BroadcastReceiver broadcastReceiver;
    private TextView tvNameRight, tvNameWrong, tvRightValue, tvWrongValue, tvRecordValue, tvColor1, tvTimeLeft;
    private Button btnYes, btnNo;
    private boolean differentValues = true;
    private boolean correctAnswer = false;

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

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                String value = intent.getStringExtra(KEY_TIME_VALUE);
                if (!value.equals("Finish")) {
                    tvTimeLeft.setText(getContext().getText(R.string.time_left_00_00) + value);
                } else {

                        if (Build.VERSION.SDK_INT >= 26) {
                            ((Vibrator) getActivity().getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(200,10));
                        } else {
                            ((Vibrator) getActivity().getSystemService(VIBRATOR_SERVICE)).vibrate(200);
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
                        args.putInt(FinishGameFragment.KEY_SCORE,rightAnswer);
                        finishGameFragment.setArguments(args);
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fl_container,finishGameFragment)
                                .addToBackStack(null)
                                .commit();
                    }
                }

            }
        };
        mSharedPreferencesHelper = new SharedPreferencesHelper(getContext());
        record = mSharedPreferencesHelper.getRecord();
        showRightWrongAnswerViews();
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        tvColor1 = view.findViewById(R.id.tv_color_one);
        btnYes = view.findViewById(R.id.btnYes);
        btnNo = view.findViewById(R.id.btnNo);
        tvTimeLeft = view.findViewById(R.id.tv_time_left);

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


        do {
            color1 = rnd.nextInt(colors.length);
            valueColor1 = rnd.nextInt(color_names.length);
            if (color1 != oldColor1 || valueColor1 != oldValueColor1) {
                differentValues = true;
                oldColor1 = color1;
                oldValueColor1 = valueColor1;
            } else {
                differentValues = false;
            }

        } while (!differentValues);

        correctAnswer = color1 == valueColor1;
        tvColor1.setText(color_names[valueColor1]);
        tvColor1.setTextColor(colors[color1]);
    }

    private void plusRight() {
        rightAnswer = rightAnswer + 1;
        if (rightAnswer > record ) {
            setRecord();
        }
        tvRightValue.setText(valueOf(rightAnswer));
    }

    private void plusWrong() {
        wrongAnswer = wrongAnswer + 1;
        if (Build.VERSION.SDK_INT >= 26) {
            ((Vibrator) getActivity().getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(100,100));
        } else {
            ((Vibrator) getActivity().getSystemService(VIBRATOR_SERVICE)).vibrate(100);
        }
        if (wrongAnswer == 10){
            GameOverFragment gameOverFragment = new GameOverFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container, gameOverFragment)
                    .addToBackStack(null)
                    .commit();
            long[] mice = {0,400,100,500};
            if (Build.VERSION.SDK_INT >= 26) {
                ((Vibrator) getActivity().getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createWaveform(mice,-1));
            } else {
                ((Vibrator) getActivity().getSystemService(VIBRATOR_SERVICE)).vibrate(400);
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
        getActivity().stopService(new Intent(getContext(), TimerService.class));
        hideRightWrongAnswerViews();
    }


    @Override
    public void onStop() {
        getActivity().unregisterReceiver(broadcastReceiver);
        super.onStop();
    }
}