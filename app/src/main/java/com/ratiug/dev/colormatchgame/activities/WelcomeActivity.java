package com.ratiug.dev.colormatchgame.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.ratiug.dev.colormatchgame.R;
import com.ratiug.dev.colormatchgame.SharedPreferencesHelper;
import com.ratiug.dev.colormatchgame.UserDao;

public class WelcomeActivity extends AppCompatActivity {
    public static final String TAG = "DBG | SplashScreen ";
    UserDao userDao = new UserDao();
    private SharedPreferencesHelper mSharedPreferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setThemeApp();

        String prefLanguage = mSharedPreferencesHelper.getLanguage().trim(); //todo create method (check on first load language and set it to SP)
        if (prefLanguage.equals("")) {
            mSharedPreferencesHelper.setLanguage(getResources().getConfiguration().locale.toString().substring(0, 2));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { //todo create method
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mSharedPreferencesHelper = new SharedPreferencesHelper(this);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                selectActivity();
            }
        }, 1000);

    }

    private void selectActivity() {
        Log.d(TAG, "selectActivity: " + mSharedPreferencesHelper.getTokenId());
        if (mSharedPreferencesHelper.getTokenId() != null) {
            userDao.updateUserInfo(this);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        finish();
    }

    private void setThemeApp() {
        mSharedPreferencesHelper = new SharedPreferencesHelper(this);
        AppCompatDelegate.setDefaultNightMode(mSharedPreferencesHelper.getTheme());
    }
}