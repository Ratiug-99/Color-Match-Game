package com.ratiug.dev.colormatchgame.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ratiug.dev.colormatchgame.R;
import com.ratiug.dev.colormatchgame.SharedPreferencesHelper;

public class WelcomeActivity extends AppCompatActivity {
    private SharedPreferencesHelper mSharedPreferencesHelper;
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

    private Runnable selectActivity() {
        Log.d(TAG, "selectActivity: " + mSharedPreferencesHelper.getTokenId());
        if (mSharedPreferencesHelper.getTokenId() != null){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return null;
    }
}