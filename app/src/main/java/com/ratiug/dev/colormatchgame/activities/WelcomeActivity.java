package com.ratiug.dev.colormatchgame.activities;

import android.content.Intent;
import android.content.res.Resources;
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
        mSharedPreferencesHelper = new SharedPreferencesHelper(this);
        setThemeApp();
        checkLocale();
        setFullScreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                selectActivity();
            }
        }, 1000);

    }




    private void selectActivity() {
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
        AppCompatDelegate.setDefaultNightMode(mSharedPreferencesHelper.getTheme());

        Resources res = getResources();
        boolean enableQAurl = res.getBoolean(R.bool.windowLightStatusBarValue);
    }

    private void checkLocale() {
        String prefLanguage = mSharedPreferencesHelper.getLanguage().trim(); // check on first load language and set it to SP
        if (prefLanguage.equals("")) {
            mSharedPreferencesHelper.setLanguage(getResources().getConfiguration().locale.toString().substring(0, 2));
        }
    }

    private void setFullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }
}