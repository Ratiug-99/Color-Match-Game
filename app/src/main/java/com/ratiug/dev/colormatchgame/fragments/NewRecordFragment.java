package com.ratiug.dev.colormatchgame.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.ratiug.dev.colormatchgame.R;
import com.ratiug.dev.colormatchgame.SharedPreferencesHelper;
import com.ratiug.dev.colormatchgame.UserDao;

public class NewRecordFragment extends Fragment {
    SharedPreferencesHelper sharedPreferencesHelper;
    private TextView tvNewRecord;
    private LinearLayout startGame;
    private UserDao userDao = new UserDao();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferencesHelper = new SharedPreferencesHelper(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_record, container, false);

        tvNewRecord = v.findViewById(R.id.tv_new_record);
        tvNewRecord.setText(getContext().getResources().getString(R.string.new_record) + sharedPreferencesHelper.getRecord());

       userDao.updateUserInfo(getContext());

        startGame = v.findViewById(R.id.startGame);
        showButtonNewGame();

        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restartGame();
            }
        });

        return v;
    }

    private void restartGame() {
        GameFragment game = new GameFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_container, game)
                .commit();
    }

    private void showButtonNewGame() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                startGame.setVisibility(View.VISIBLE);
            }
        }, 2000);
    }

}