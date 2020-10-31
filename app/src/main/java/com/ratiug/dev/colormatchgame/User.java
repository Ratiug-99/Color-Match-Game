package com.ratiug.dev.colormatchgame;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class User {
    public FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    public String getUserEmail(){
        if (firebaseUser != null){
            return firebaseUser.getEmail();
        }else{
            return null;
        }
    }
}
