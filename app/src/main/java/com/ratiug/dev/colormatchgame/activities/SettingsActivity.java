package com.ratiug.dev.colormatchgame.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.ratiug.dev.colormatchgame.R;
import com.ratiug.dev.colormatchgame.SharedPreferencesHelper;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {
    public static final String TAG = "DBG | SettingsActivity";

    Spinner spinnerCountColors;
    RadioButton rbEnglish, rbRussian, rbUkrainian;
    ImageButton btnExit;
    CheckBox cbVibrationStatus;
    SwitchCompat switchTheme;

    SharedPreferencesHelper sharedPreferencesHelper;
    Integer selected_count_colors;
    Boolean vibrationStatus;
    String language;
    boolean startActivity = false;
    View.OnClickListener radioButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RadioButton rb = (RadioButton) view;
            switch (rb.getId()) {
                case R.id.rb_english:
                    setLocale("en");
                    break;
                case R.id.rb_russian:
                    setLocale("ru");
                    break;
                case R.id.rb_ukrainian:
                    setLocale("uk");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferencesHelper = new SharedPreferencesHelper(this);
        language = sharedPreferencesHelper.getLanguage();
        super.onCreate(savedInstanceState);
        setLocale(language);
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

        switchTheme = findViewById(R.id.switch_theme);
        int theme = sharedPreferencesHelper.getTheme();
        if (theme == 1) {
            switchTheme.setChecked(true);
        } else if (theme == 2) {
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
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES); //2
                    sharedPreferencesHelper.setTheme(AppCompatDelegate.getDefaultNightMode());
                }
            }
        });

        btnExit = findViewById(R.id.btn_exit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
    }

    private void logout() {
        sharedPreferencesHelper.setTokenId(null);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void setLanguageRB(String language) {
        if (language.equals("")) {
            language = Locale.getDefault().getLanguage();
        }

        switch (language) {
            case "en":
                rbEnglish.setChecked(true);
                break;
            case "ru":
                rbRussian.setChecked(true);
                break;
            case "uk":
                rbUkrainian.setChecked(true);
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
        sharedPreferencesHelper.setLanguage(lang);
        if (startActivity) {
            recreate();
        } else {
            startActivity = true;
        }
    }
}