package com.ratiug.dev.colormatchgame;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class GameFragment extends Fragment {
    private TextView tvNameRight,tvNameWrong,tvRightValue,tvWrongValue,tvRecordValue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvNameRight = getActivity().findViewById(R.id.tv_name_right);
        tvNameWrong = getActivity().findViewById(R.id.tv_name_wrong);
        tvRightValue = getActivity().findViewById(R.id.tv_right_value);
        tvWrongValue = getActivity().findViewById(R.id.tv_wrong_value);
        tvRecordValue = getActivity().findViewById(R.id.tv_record_value);
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false);
    }
}