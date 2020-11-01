package com.ratiug.dev.colormatchgame.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import com.ratiug.dev.colormatchgame.R;
import com.ratiug.dev.colormatchgame.SharedPreferencesHelper;
import com.ratiug.dev.colormatchgame.UserDao;

public class WelcomeActivity extends AppCompatActivity {
    private SharedPreferencesHelper mSharedPreferencesHelper;
    UserDao userDao = new UserDao();
    public static final String TAG = "DBG | SplashScreen ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        setContentView(R.layout.activity_welcome);
        mSharedPreferencesHelper = new SharedPreferencesHelper(this);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
               selectActivity();
            }
        }, 1000); //specify the number of milliseconds


    }

    private void selectActivity() {
        Log.d(TAG, "selectActivity: " + mSharedPreferencesHelper.getTokenId());
        if (mSharedPreferencesHelper.getTokenId() != null){
            Intent intent = new Intent(this,MainActivity.class);
            userDao.updateUserInfo(this);
            startActivity(intent);
            finish();
        }
        else{
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}