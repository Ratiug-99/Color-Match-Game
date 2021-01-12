package com.ratiug.dev.colormatchgame.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ratiug.dev.colormatchgame.InternetUtils;
import com.ratiug.dev.colormatchgame.R;
import com.ratiug.dev.colormatchgame.SharedPreferencesHelper;
import com.ratiug.dev.colormatchgame.User;
import com.ratiug.dev.colormatchgame.UserDao;

import java.util.Objects;


public class WelcomeActivity extends AppCompatActivity {
    public static final String TAG = "DBG | SplashScreen ";
    UserDao userDao = new UserDao();
    private SharedPreferencesHelper mSharedPreferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSharedPreferencesHelper = new SharedPreferencesHelper(this);
        checkThemeApp();
        checkLocale();
        setFullScreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Handler handler = new Handler();
        handler.postDelayed(this::selectActivity, 1000);
    }


    private void selectActivity() {
        if (mSharedPreferencesHelper.getTokenId() != null) {
                openMainActivity();
        } else {
            openLoginActivity();
        }
    }



    private void openLoginActivity() {
        Log.d(TAG, "openLoginActivity");
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        Animatoo.animateFade(this);
        finish();
    }

    private void openMainActivity() {
        Log.d(TAG, "openMainActivity");
        userDao.updateUserInfo(this);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        Animatoo.animateFade(this);
        finish();
    }

    private void checkThemeApp(){
        Log.d(TAG, "checkThemeApp: " + mSharedPreferencesHelper.getTheme());
        if (mSharedPreferencesHelper.getTheme() == -1){

            int currentNightMode = getApplicationContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            switch (currentNightMode) {
                case Configuration.UI_MODE_NIGHT_NO:
                    mSharedPreferencesHelper.setTheme(1);
                    break;
                case Configuration.UI_MODE_NIGHT_YES:
                    mSharedPreferencesHelper.setTheme(2);
                    break;
        }
        }
        setThemeApp();
        Log.d(TAG, "checkThemeApp: " + mSharedPreferencesHelper.getTheme());
    }

    private void setThemeApp() {
        Log.d(TAG, "setThemeApp: " + mSharedPreferencesHelper.getTheme());
        AppCompatDelegate.setDefaultNightMode(mSharedPreferencesHelper.getTheme());
    }

    private void checkLocale() {
        String prefLanguage = mSharedPreferencesHelper.getLanguage().trim();
        Log.d(TAG, "checkLocale: " + prefLanguage);
        if (prefLanguage.equals("")) {
            Log.d(TAG, "checkLocale: ");
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