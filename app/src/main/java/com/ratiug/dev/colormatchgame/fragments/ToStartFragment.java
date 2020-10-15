package com.ratiug.dev.colormatchgame.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.ratiug.dev.colormatchgame.R;
import com.ratiug.dev.colormatchgame.activities.SettingsActivity;

public class ToStartFragment extends Fragment {
    private Button btnStartGame, btnSettings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_to_start, container, false);
        btnStartGame = view.findViewById(R.id.btn_start_game);
        btnSettings = view.findViewById(R.id.btn_settings);
        btnStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame();
            }
        });
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettings();
            }
        });
        return view;
    }

    private void openSettings() {
        Intent intent = new Intent(getActivity(), SettingsActivity.class);
        startActivity(intent);
    }

    private void startGame() {
        GameFragment game = new GameFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_container, game)
                .addToBackStack(null)
                .commit();
    }
}