package com.ratiug.dev.colormatchgame.activities;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ratiug.dev.colormatchgame.R;
import com.ratiug.dev.colormatchgame.SharedPreferencesHelper;
import com.ratiug.dev.colormatchgame.fragments.ToStartFragment;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "DBG | MAIN ACTIVITY";
    SharedPreferencesHelper mSharedPreferencesHelper;
    boolean doubleBackToExitPressedOnce = false;
    boolean startActivity = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLocale(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate");
        openToStartFragment();
    }

    private void openToStartFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_container, new ToStartFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        int countFragment = getSupportFragmentManager().getBackStackEntryCount();
        if (countFragment == 2) {
            super.onBackPressed();
        } else if (countFragment >= 3) {
            while (countFragment != 2) {
                countFragment = getSupportFragmentManager().getBackStackEntryCount();
                super.onBackPressed();
            }
        } else if (countFragment == 1) {
            if (doubleBackToExitPressedOnce) {
                System.exit(0);
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, R.string.press_back_again, Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {
            finish();
        }
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");
        if (startActivity) {
            setLocale(this);
            recreate();
        } else {
            startActivity = true;
        }
        super.onResume();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
    }

    public void setLocale(Activity context) {
        mSharedPreferencesHelper = new SharedPreferencesHelper(this);
        Configuration conf = getBaseContext().getResources().getConfiguration();
        String prefLanguage = mSharedPreferencesHelper.getLanguage().trim();
        Log.d(TAG, "setLocale: " + conf.locale.getLanguage() + " | " + prefLanguage);
        if (!"".equals(prefLanguage) && !conf.locale.getLanguage().equals(prefLanguage)) {
            Locale locale;
            locale = new Locale(mSharedPreferencesHelper.getLanguage());
            Configuration config = new Configuration(context.getResources().getConfiguration());
            Log.d(TAG, "setLocale: " + locale.getLanguage());
            Locale.setDefault(locale);
            config.setLocale(locale);
            context.getBaseContext().getResources().updateConfiguration(config,
                    context.getBaseContext().getResources().getDisplayMetrics());
        }
    }
}