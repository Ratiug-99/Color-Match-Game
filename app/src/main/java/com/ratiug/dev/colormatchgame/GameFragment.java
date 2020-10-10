package com.ratiug.dev.colormatchgame;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.renderscript.Sampler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

import static java.lang.String.valueOf;

public class GameFragment extends Fragment {
    private TextView tvNameRight,tvNameWrong,tvRightValue,tvWrongValue,tvRecordValue,tvColor1,tvColor2;
    private Button btnYes,btnNo;
    private  boolean correctAnswer = false;
    int [] colors;
    String [] color_names;
    public int rightAnswer,wrongAnswer,record;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvNameRight = getActivity().findViewById(R.id.tv_name_right);
        tvNameWrong = getActivity().findViewById(R.id.tv_name_wrong);
        tvRightValue = getActivity().findViewById(R.id.tv_right_value);
        tvWrongValue = getActivity().findViewById(R.id.tv_wrong_value);
        tvRecordValue = getActivity().findViewById(R.id.tv_record_value);
        colors = getContext().getResources().getIntArray(R.array.colors);
        color_names = getContext().getResources().getStringArray(R.array.color_names);
        showRightWrongAnswerViews();
    }

    private void showRightWrongAnswerViews() {
       tvNameRight.setVisibility(View.VISIBLE);
       tvNameWrong.setVisibility(View.VISIBLE);
       tvRightValue.setVisibility(View.VISIBLE);
       tvWrongValue.setVisibility(View.VISIBLE);
    }

    private void hideRightWrongAnswerViews() {
        tvNameRight.setVisibility(View.GONE);
        tvNameWrong.setVisibility(View.GONE);
        tvRightValue.setVisibility(View.GONE);
        tvWrongValue.setVisibility(View.GONE);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view =  inflater.inflate(R.layout.fragment_game, container, false);
        tvColor1 = view.findViewById(R.id.tv_color_one);
        btnYes = view.findViewById(R.id.btnYes);
        btnNo = view.findViewById(R.id.btnNo);
        setColorAndValues();
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (correctAnswer){
                    plusRight();
                }
                else {
                    plusWrong();
                }
                setColorAndValues();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if  (!correctAnswer){
                    plusRight();
                }
                else{
                    plusWrong();
                }
                setColorAndValues();
            }
        });
       return view;
    }

    private void setColorAndValues(){
        Random rnd = new Random();
        int color1 = rnd.nextInt(colors.length);
        int valueColor1 = rnd.nextInt(color_names.length);
        correctAnswer = color1 == valueColor1;
        tvColor1.setText(color_names[valueColor1]);
        tvColor1.setTextColor(colors[color1]);
    }

    private void plusRight(){
        rightAnswer = rightAnswer + 1;
        if (rightAnswer > record){
            record = rightAnswer;
            tvRecordValue.setText(valueOf(record));
        }
        tvRightValue.setText(valueOf(rightAnswer));
    }

    private void plusWrong(){
        wrongAnswer = wrongAnswer + 1;
        tvWrongValue.setText(valueOf(wrongAnswer));
    }
}