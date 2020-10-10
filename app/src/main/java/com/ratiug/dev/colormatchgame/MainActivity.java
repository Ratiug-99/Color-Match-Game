package com.ratiug.dev.colormatchgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.String.valueOf;

public class MainActivity extends AppCompatActivity {
    SharedPreferencesHelper mSharedPreferencesHelper;
    private TextView tvRecordValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openToStartFragment();
        tvRecordValue = findViewById(R.id.tv_record_value);
        mSharedPreferencesHelper = new SharedPreferencesHelper(this);
        tvRecordValue.setText(valueOf(mSharedPreferencesHelper.getRecord()));

    }

    private void openToStartFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_container, new ToStartFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        int countFragment = getSupportFragmentManager().getBackStackEntryCount();

        if (countFragment == 1) {

            if (doubleBackToExitPressedOnce) {
              System.exit(0);
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else   {
            super.onBackPressed();
            return;
        }
    }

}