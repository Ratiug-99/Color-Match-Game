package com.ratiug.dev.colormatchgame;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimerService extends Service {
    MyBinder mBinder = new MyBinder();
    public static final String TAG = "DBG | TimerService";
    CountDownTimer countDownTimer;

    @Override
    public void onCreate() {
        startTimer();
        super.onCreate();
    }

    private void startTimer(){
        countDownTimer = new CountDownTimer(61000,1000) {
            @Override
            public void onTick(long mlsToFinish) {
                Log.d(TAG, "onTick: +");

                sendBroadcast(new Intent(GameFragment.KEY_BROADCAST_RECEIVER_TICK)
                        .putExtra(GameFragment.KEY_TIME_VALUE, convertMlsToCorrectDateFormateString(mlsToFinish)));



            }
            @Override
            public void onFinish() {
                sendBroadcast(new Intent(GameFragment.KEY_BROADCAST_RECEIVER_TICK).putExtra(GameFragment.KEY_TIME_VALUE, "Finish"));
            }
        }.start();
    }

    private String  convertMlsToCorrectDateFormateString(long mls) {
        SimpleDateFormat formatter = new SimpleDateFormat("mm:ss", Locale.UK);
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = new Date(mls);
       return  formatter.format(date);
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

    class  MyBinder extends Binder {
        TimerService getService(){
            return TimerService.this;
        }
    }

}

