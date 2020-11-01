package com.ratiug.dev.colormatchgame;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ratiug.dev.colormatchgame.activities.MainActivity;

public class UserDao extends User {
    FirebaseUser firebaseUser;
    SharedPreferencesHelper mSharedPreferencesHelper;

    public void updateUserInfo(Context context){
        mSharedPreferencesHelper = new SharedPreferencesHelper(context);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        String  ID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String mName = firebaseUser.getDisplayName();
        String mEmail = firebaseUser.getEmail();
        String mUri = firebaseUser.getPhotoUrl().toString();



        User newUser = new User(mEmail,mName,String.valueOf(mSharedPreferencesHelper.getRecord()),mUri);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference users = database.getReference("Users:");
        users.child(ID).setValue(newUser);
    }
}
