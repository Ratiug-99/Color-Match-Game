package com.ratiug.dev.colormatchgame.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.ratiug.dev.colormatchgame.R;
import com.ratiug.dev.colormatchgame.SharedPreferencesHelper;

import java.util.Objects;

import static java.lang.String.valueOf;


public class FinishGameFragment extends Fragment { //getRecordFromDB normal
    public static final String KEY_SCORE = "KEY_SCORE";
    private TextView tvScore, tvRecord;
    private LinearLayout btnRestart;
    private SharedPreferencesHelper sharedPreferencesHelper;
    int selectedOptionsColor, record;
    SharedPreferencesHelper mSharedPreferencesHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedPreferencesHelper = new SharedPreferencesHelper(Objects.requireNonNull(getContext()));
        String[] countColorOptions = getContext().getResources().getStringArray(R.array.count_colors_for_select);
        selectedOptionsColor = Integer.parseInt(countColorOptions[mSharedPreferencesHelper.getCountColors()]);
        getRecordFromDB();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_finish_game, container, false);

        tvScore = v.findViewById(R.id.tv_score);
        tvRecord = v.findViewById(R.id.tv_record);
        btnRestart = v.findViewById(R.id.btn_restart_game);
        sharedPreferencesHelper = new SharedPreferencesHelper(Objects.requireNonNull(getContext()));

        tvRecord.setText(getContext().getResources().getText(R.string.record)  + valueOf(record));
        assert getArguments() != null;
        tvScore.setText((getContext().getResources().getText(R.string.your_score) + valueOf(getArguments().getInt(KEY_SCORE))));
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restartGame();
            }
        });
        return v;
    }

    private void getRecordFromDB() {
        switch (selectedOptionsColor) {
            case 4: record = mSharedPreferencesHelper.getRecord_4(); break;
            case 6: record = mSharedPreferencesHelper.getRecord_6(); break;
            case 8: record = mSharedPreferencesHelper.getRecord_8(); break;
            case 10: record = mSharedPreferencesHelper.getRecord_10(); break;
        }
    }

    private void restartGame() {
        GameFragment game = new GameFragment();
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_container, game)
                .commit();
    }
}

