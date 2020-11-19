package com.ratiug.dev.colormatchgame.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ratiug.dev.colormatchgame.InternetUtils;
import com.ratiug.dev.colormatchgame.R;
import com.ratiug.dev.colormatchgame.SharedPreferencesHelper;
import com.ratiug.dev.colormatchgame.User;
import com.ratiug.dev.colormatchgame.UserDao;

import java.util.Objects;


public class WelcomeActivity extends AppCompatActivity {
    public static final String TAG = "DBG | SplashScreen ";
    UserDao userDao = new UserDao();
    DatabaseReference reference;
    InternetUtils internetUtils = new InternetUtils();
    private SharedPreferencesHelper mSharedPreferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSharedPreferencesHelper = new SharedPreferencesHelper(this);
        setThemeApp();
        checkLocale();
        setFullScreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                selectActivity();
            }
        }, 1000);

    }


    private void selectActivity() {
        if (mSharedPreferencesHelper.getTokenId() != null) {
            if (internetUtils.isOnline(getApplicationContext())){
                checkRecord();
            }else {
                openMainActivity();
            }
        } else {
            openLoginActivity();
        }
    }

    private void checkRecord() {
        reference = FirebaseDatabase.getInstance().getReference().child("Users:");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange");
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    User p = dataSnapshot1.getValue(User.class);
                    String cUserId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
                    String lUser = p.getUid();
                    if (p.getUid() != null && cUserId.equals(lUser)) {
                        int bdUserRecord_4 = Integer.parseInt(p.getRecord_4());
                        int record_4 = mSharedPreferencesHelper.getRecord_4();
                        int bdUserRecord_6 = Integer.parseInt(p.getRecord_6());
                        int record_6 = mSharedPreferencesHelper.getRecord_6();
                        int bdUserRecord_8 = Integer.parseInt(p.getRecord_8());
                        int record_8 = mSharedPreferencesHelper.getRecord_8();
                        int bdUserRecord_10 = Integer.parseInt(p.getRecord_10());
                        int record_10 = mSharedPreferencesHelper.getRecord_10();

                        if (bdUserRecord_4 > record_4) {
                            mSharedPreferencesHelper.setRecord_4(bdUserRecord_4);
                        } else if (bdUserRecord_6 > record_6) {
                            mSharedPreferencesHelper.setRecord_6(bdUserRecord_6);
                        }
                         else if (bdUserRecord_8 > record_8) {
                            mSharedPreferencesHelper.setRecord_6(bdUserRecord_8);
                        }
                         else if (bdUserRecord_10 > record_10) {
                            mSharedPreferencesHelper.setRecord_6(bdUserRecord_10);
                        }

//                        mSharedPreferencesHelper.setRecord_4(0);
//                        mSharedPreferencesHelper.setRecord_6(0);
//                        mSharedPreferencesHelper.setRecord_8(0);
//                        mSharedPreferencesHelper.setRecord_10(0);

                        openMainActivity();
                        reference.removeEventListener(this);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "onCancelled: " + error);
            }
        });
    }

    private void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        Animatoo.animateFade(this);
        finish();
    }

    private void openMainActivity() {
        userDao.updateUserInfo(this);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        Animatoo.animateFade(this);
        finish();
    }

    private void setThemeApp() {
        AppCompatDelegate.setDefaultNightMode(mSharedPreferencesHelper.getTheme());
    }

    private void checkLocale() {
        String prefLanguage = mSharedPreferencesHelper.getLanguage().trim(); // check on first load language and set it to SP
        Log.d(TAG, "checkLocale: " + prefLanguage);
        if (prefLanguage.equals("")) {
            Log.d(TAG, "checkLocale: ");
            mSharedPreferencesHelper.setLanguage(getResources().getConfiguration().locale.toString().substring(0, 2));
        }
    }

    private void setFullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }
}