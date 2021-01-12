package com.ratiug.dev.colormatchgame;

import android.util.Log;

public class User{
    public static final String TAG = "DBG | USER";

    public String uid;
    public String email;
    public String name;
    public String record_4;
    public String record_6;
    public String record_8;
    public String record_10;
    public String uri;

    public User() {
    }

    public User(String uid, String email, String name, String record, String uri) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.uri = uri;
    }

    public User(String uid, String email, String name, String record_4, String record_6, String record_8, String record_10, String uri) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.record_4 = record_4;
        this.record_6 = record_6;
        this.record_8 = record_8;
        this.record_10 = record_10;
        this.uri = uri;
        Log.d(TAG, "User: created");
    }

    public String getRecord_4() {
        return record_4;
    }

    public void setRecord_4(String record_4) {
        this.record_4 = record_4;
    }

    public String getRecord_6() {
        return record_6;
    }

    public void setRecord_6(String record_6) {
        this.record_6 = record_6;
    }

    public String getRecord_8() {
        return record_8;
    }

    public void setRecord_8(String record_8) {
        this.record_8 = record_8;
    }

    public String getRecord_10() {
        return record_10;
    }

    public void setRecord_10(String record_10) {
        this.record_10 = record_10;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
