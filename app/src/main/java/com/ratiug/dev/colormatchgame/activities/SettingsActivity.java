package com.ratiug.dev.colormatchgame.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;

import com.ratiug.dev.colormatchgame.R;
import com.ratiug.dev.colormatchgame.SharedPreferencesHelper;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {
    public static final String TAG = "DBG | SettingsActivity";

    Spinner spinnerCountColors;
    RadioButton rbEnglish, rbRussian, rbUkrainian;
    CheckBox cbVibrationStatus;
    SwitchCompat switchTheme;
    SharedPreferencesHelper sharedPreferencesHelper;
    Integer selected_count_colors;
    Boolean vibrationStatus;
    String language;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferencesHelper = new SharedPreferencesHelper(this);
        language = sharedPreferencesHelper.getLanguage();
        super.onCreate(savedInstanceState);
        Locale myLocale = new Locale(language);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        setContentView(R.layout.activity_settings);

        selected_count_colors = sharedPreferencesHelper.getCountColors();
        vibrationStatus = sharedPreferencesHelper.getVibrationStatus();
        cbVibrationStatus = findViewById(R.id.cb_vibration);





        rbEnglish = findViewById(R.id.rb_english);
        rbEnglish.setOnClickListener(radioButtonClickListener);
        rbRussian = findViewById(R.id.rb_russian);
        rbRussian.setOnClickListener(radioButtonClickListener);
        rbUkrainian = findViewById(R.id.rb_ukrainian);
        rbUkrainian.setOnClickListener(radioButtonClickListener);


        setLanguageRB(language);
       // setLocale(language);

        switchTheme = findViewById(R.id.switch_theme);
        int theme = sharedPreferencesHelper.getTheme();
        if (theme == 1){
            switchTheme.setChecked(true);
        } else if (theme == 2){
            switchTheme.setChecked(false);
        }

        spinnerCountColors = findViewById(R.id.s_colors_count);
        ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this,
                R.array.count_colors_for_select, R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinnerCountColors.setAdapter(adapter);

        spinnerCountColors.setSelection(selected_count_colors);
        cbVibrationStatus.setChecked(vibrationStatus);

        spinnerCountColors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sharedPreferencesHelper.setCountColors(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        cbVibrationStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sharedPreferencesHelper.setVibrationStatus(b);
            }
        });

        switchTheme.setOnCheckedChangeListener(new SwitchCompat.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); //1
                    sharedPreferencesHelper.setTheme(AppCompatDelegate.getDefaultNightMode());
                    Log.d(TAG, "onCheckedChanged: " + AppCompatDelegate.getDefaultNightMode());
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES); //2
                    sharedPreferencesHelper.setTheme(AppCompatDelegate.getDefaultNightMode());
                    Log.d(TAG, "onCheckedChanged: " + AppCompatDelegate.getDefaultNightMode());
                }
            }
        });
    }


    private void setLanguageRB(String language) {
        if (language.equals("")) {
            language = Locale.getDefault().getLanguage();
        }

        switch (language) {
            case "en":
                rbEnglish.setChecked(true);
                Log.d(TAG, "setLanguageRB: en+");
                break;
            case "ru":
                rbRussian.setChecked(true);
                Log.d(TAG, "setLanguageRB: ru+");
                break;
            case "uk":
                rbUkrainian.setChecked(true);
                Log.d(TAG, "setLanguageRB: uk+");
                break;
        }

    }

    private void setLocale(String lang) {
        Log.d(TAG, "setLocale: " + lang);
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, SettingsActivity.class);
        sharedPreferencesHelper.setLanguage(lang);
        finish();
        startActivity(refresh);
    }

    View.OnClickListener radioButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RadioButton rb = (RadioButton) view;
            switch (rb.getId()) {
                case R.id.rb_english:
                    setLocale("en");
                    ;
                    break;
                case R.id.rb_russian:
                    setLocale("ru");
                    ;
                    break;
                case R.id.rb_ukrainian:
                    setLocale("uk");
                    break;
            }
        }
    };
}