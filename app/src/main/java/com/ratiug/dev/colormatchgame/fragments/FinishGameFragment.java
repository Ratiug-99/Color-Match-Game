package com.ratiug.dev.colormatchgame.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ratiug.dev.colormatchgame.R;
import com.ratiug.dev.colormatchgame.SharedPreferencesHelper;

import static java.lang.String.valueOf;


public class FinishGameFragment extends Fragment {
    public static final  String KEY_SCORE = "KEY_SCORE";
    private TextView tvScore, tvRecord;
    private LinearLayout btnRestart;
    private SharedPreferencesHelper sharedPreferencesHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_finish_game, container, false);
        tvScore = v.findViewById(R.id.tv_score);
        tvRecord = v.findViewById(R.id.tv_record);
        btnRestart = v.findViewById(R.id.btn_restart_game);
        sharedPreferencesHelper = new SharedPreferencesHelper(getContext());
        tvRecord.setText(getContext().getResources().getText(R.string.record) + valueOf(sharedPreferencesHelper.getRecord()));
        tvScore.setText((getContext().getResources().getText(R.string.your_score) + valueOf(getArguments().getInt(KEY_SCORE))));

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restartGame();
            }
        });

        return  v;
    }
    private void restartGame() {
        GameFragment game = new GameFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_container, game)
                .commit();
    }
}

