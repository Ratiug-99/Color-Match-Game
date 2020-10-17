package com.ratiug.dev.colormatchgame.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ratiug.dev.colormatchgame.R;
import com.ratiug.dev.colormatchgame.SharedPreferencesHelper;
import com.ratiug.dev.colormatchgame.fragments.ToStartFragment;

import java.util.Locale;

import static java.lang.String.valueOf;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "DBG | MAIN ACTIVITY";
    SharedPreferencesHelper mSharedPreferencesHelper;
    boolean doubleBackToExitPressedOnce = false;
    private TextView tvRecordValue;
    //temp
    boolean startActivity = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mSharedPreferencesHelper = new SharedPreferencesHelper(this);
        AppCompatDelegate.setDefaultNightMode(mSharedPreferencesHelper.getTheme());


        String prefLanguage = mSharedPreferencesHelper.getLanguage().trim();
        if (prefLanguage.equals("")){
            mSharedPreferencesHelper.setLanguage(getResources().getConfiguration().locale.toString().substring(0,2));
        }
        Log.d(TAG, "onCreate: ");                                                      //
        Locale myLocale = new Locale(mSharedPreferencesHelper.getLanguage());          //
        Resources res = getResources();                                                //      apply language
        DisplayMetrics dm = res.getDisplayMetrics();                                   //
        Configuration conf = res.getConfiguration();                                   //
        conf.locale = myLocale;                                                        //
        res.updateConfiguration(conf, dm);                                             //


        setContentView(R.layout.activity_main);
        openToStartFragment();
        tvRecordValue = findViewById(R.id.tv_record_value);
        mSharedPreferencesHelper = new SharedPreferencesHelper(this);
        tvRecordValue.setText(valueOf(mSharedPreferencesHelper.getRecord()));

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
        if (countFragment >= 3) {
            while (countFragment != 2) {
                countFragment = getSupportFragmentManager().getBackStackEntryCount();
                super.onBackPressed();
            }
        } else if (countFragment == 1) {
            if (doubleBackToExitPressedOnce) {
                System.exit(0);
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        if (startActivity) {
            Log.d(TAG, "onResume: +");
            setLocale(this);
            recreate();
        } else {
            Log.d(TAG, "onResume: -");
            startActivity = true;
        }
        super.onResume();
    }

    public  void setLocale(Activity context) {
        mSharedPreferencesHelper = new SharedPreferencesHelper(this);
        Configuration conf = getBaseContext().getResources().getConfiguration();
        String prefLanguage = mSharedPreferencesHelper.getLanguage().trim();
        String currentLanguage = getResources().getConfiguration().locale.toString();
        Log.d(TAG, "setLocale: " + prefLanguage + currentLanguage);
        if (!"".equals(prefLanguage) && !conf.locale.getLanguage().equals(prefLanguage)) {
            Log.d(TAG, "setLocale: +");
        Locale locale;
        locale = new Locale(mSharedPreferencesHelper.getLanguage());
        Configuration config = new Configuration(context.getResources().getConfiguration());
        Locale.setDefault(locale);
        config.setLocale(locale);

        context.getBaseContext().getResources().updateConfiguration(config,
                context.getBaseContext().getResources().getDisplayMetrics());
        recreate();
        }
    }
}