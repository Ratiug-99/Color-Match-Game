package com.ratiug.dev.colormatchgame;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.ratiug.dev.colormatchgame.fragments.GameFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimerService extends Service {
    public static final String TAG = "DBG | TimerService";
    MyBinder mBinder = new MyBinder();
    CountDownTimer countDownTimer;

    @Override
    public void onCreate() {
        startTimer();
        super.onCreate();
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(61000, 1000) { //61
            @Override
            public void onTick(long mlsToFinish) {

                sendBroadcast(new Intent(GameFragment.KEY_BROADCAST_RECEIVER_TICK)
                        .putExtra(GameFragment.KEY_TIME_VALUE, convertMlsToCorrectDateFormateString(mlsToFinish)));


            }

            @Override
            public void onFinish() {
                sendBroadcast(new Intent(GameFragment.KEY_BROADCAST_RECEIVER_TICK).putExtra(GameFragment.KEY_TIME_VALUE, "Finish"));
            }
        }.start();
    }

    private String convertMlsToCorrectDateFormateString(long mls) {
        SimpleDateFormat formatter = new SimpleDateFormat("mm:ss", Locale.UK);
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = new Date(mls);
        return formatter.format(date);
    }

    @Override
    public void onDestroy() {
        countDownTimer.cancel();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class MyBinder extends Binder {
        public TimerService getService() {
            return TimerService.this;
        }
    }

}

