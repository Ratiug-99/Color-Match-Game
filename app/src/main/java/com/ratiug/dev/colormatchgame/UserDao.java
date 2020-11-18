package com.ratiug.dev.colormatchgame;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserDao extends User {
    FirebaseUser firebaseUser;
    SharedPreferencesHelper mSharedPreferencesHelper;

    public void updateUserInfo(Context context) {
        mSharedPreferencesHelper = new SharedPreferencesHelper(context);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        String ID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String mName = firebaseUser.getDisplayName();
        String mEmail = firebaseUser.getEmail();
        String mUri = firebaseUser.getPhotoUrl().toString();
        String record_4 = String.valueOf(mSharedPreferencesHelper.getRecord_4());
        String record_6 = String.valueOf(mSharedPreferencesHelper.getRecord_6());
        String record_8 = String.valueOf(mSharedPreferencesHelper.getRecord_8());
        String record_10 = String.valueOf(mSharedPreferencesHelper.getRecord_10());

        User newUser = new User(ID, mEmail, mName, record_4,record_6,record_8,record_10, mUri);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference users = database.getReference("Users:");
        users.child(ID).setValue(newUser);
    }
}
