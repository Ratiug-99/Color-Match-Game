package com.ratiug.dev.colormatchgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView tvNameRight,tvNameWrong,tvRightValue,tvWrongValue,tvRecordValue;
    public int rightAnswer,wrongAnswer,record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}