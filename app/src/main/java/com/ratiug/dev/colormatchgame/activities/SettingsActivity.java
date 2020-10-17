package com.ratiug.dev.colormatchgame.activities;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.RadioButton;
import android.widget.Spinner;

import com.ratiug.dev.colormatchgame.R;
import com.ratiug.dev.colormatchgame.SharedPreferencesHelper;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {
    Spinner spinnerCountColors;
    RadioButton rbEnglish, rbRussian, rbUkrainian;
    CheckBox cbVibrationStatus;
    SharedPreferencesHelper sharedPreferencesHelper;
    Integer selected_count_colors;
    Boolean vibrationStatus;
    String language;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        sharedPreferencesHelper = new SharedPreferencesHelper(this);
        selected_count_colors = sharedPreferencesHelper.getCountColors();
        vibrationStatus = sharedPreferencesHelper.getVibrationStatus();
        cbVibrationStatus = findViewById(R.id.cb_vibration);

        rbEnglish = findViewById(R.id.rb_english);
        rbEnglish.setOnClickListener(radioButtonClickListener);
        rbRussian = findViewById(R.id.rb_russian);
        rbRussian.setOnClickListener(radioButtonClickListener);
        rbUkrainian = findViewById(R.id.rb_ukrainian);
        rbUkrainian.setOnClickListener(radioButtonClickListener);
        language = sharedPreferencesHelper.getLanguage();

        setLanguageRB(language);

        spinnerCountColors = findViewById(R.id.s_colors_count);
        ArrayAdapter <?> adapter = ArrayAdapter.createFromResource(this,
                R.array.count_colors_for_select,R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
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
    }

    private void setLanguageRB(String language) {
        if (language.equals("")) {
            language = Locale.getDefault().getLanguage();
        }

        switch (language){
            case "en":rbEnglish.setChecked(true); break;
            case "ru":rbRussian.setChecked(true); break;
            case "uk":rbUkrainian.setChecked(true); break;
        }

    }

    private void setLocale(String lang) {
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
            RadioButton rb = (RadioButton)view;
            switch (rb.getId()){
                case R.id.rb_english: setLocale("en"); ;
                    break;
                case  R.id.rb_russian: setLocale("ru"); ;
                    break;
                case R.id.rb_ukrainian: setLocale("uk");
                    break;
            }
        }
    };
}