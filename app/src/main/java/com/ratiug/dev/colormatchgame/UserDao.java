package com.ratiug.dev.colormatchgame;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserDao extends User {
    public static final String TAG = "DBG | UDAO";
    FirebaseUser firebaseUser;
    SharedPreferencesHelper mSharedPreferencesHelper;
    Boolean update = false;
    private DatabaseReference reference;

    public void updateUserInfo(Context context) {
        mSharedPreferencesHelper = new SharedPreferencesHelper(context);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference().child("Users:/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "ValueEventListener");
                if (dataSnapshot.exists()) {
                    Log.d(TAG, "onDataChange: exists");
                    User p = dataSnapshot.getValue(User.class);
                    //update records
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
                    } else if (bdUserRecord_8 > record_8) {
                        mSharedPreferencesHelper.setRecord_8(bdUserRecord_8);
                    } else if (bdUserRecord_10 > record_10) {
                        mSharedPreferencesHelper.setRecord_10(bdUserRecord_10);
                    }
//                        mSharedPreferencesHelper.setRecord_4(0);
//                        mSharedPreferencesHelper.setRecord_6(0);
//                        mSharedPreferencesHelper.setRecord_8(0);
//                        mSharedPreferencesHelper.setRecord_10(0);
                    update();
                    reference.removeEventListener(this);

                } else {
                    Log.d(TAG, "onDataChange: not exists");
                    update();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "onCancelled: " + error);
            }
        });


    }

    private void update() {
        String ID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String mName = firebaseUser.getDisplayName();
        String mEmail = firebaseUser.getEmail();
        String mUri = firebaseUser.getPhotoUrl().toString();

        String record_4 = String.valueOf(mSharedPreferencesHelper.getRecord_4());
        String record_6 = String.valueOf(mSharedPreferencesHelper.getRecord_6());
        String record_8 = String.valueOf(mSharedPreferencesHelper.getRecord_8());
        String record_10 = String.valueOf(mSharedPreferencesHelper.getRecord_10());

        User newUser = new User(ID, mEmail, mName, record_4, record_6, record_8, record_10, mUri);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference users = database.getReference("Users:");

        users.child(ID).setValue(newUser);
    }

    private void checkRecord() {
        Log.d(TAG, "checkRecord");

    }
}
//