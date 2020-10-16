package com.ratiug.dev.colormatchgame.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;

import com.ratiug.dev.colormatchgame.R;
import com.ratiug.dev.colormatchgame.SharedPreferencesHelper;

public class SettingsActivity extends AppCompatActivity {
    Spinner spinnerCountColors;
    CheckBox cbVibrationStatus;
    SharedPreferencesHelper sharedPreferencesHelper;
    Integer selected_count_colors;
    Boolean vibrationStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        sharedPreferencesHelper = new SharedPreferencesHelper(this);
        selected_count_colors = sharedPreferencesHelper.getCountColors();
        vibrationStatus = sharedPreferencesHelper.getVibrationStatus();
        spinnerCountColors = findViewById(R.id.s_colors_count);
        cbVibrationStatus = findViewById(R.id.cb_vibration);
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
}