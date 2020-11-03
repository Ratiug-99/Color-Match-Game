package com.ratiug.dev.colormatchgame;

public class User{

    public String uid;
    public String email;
    public String name;
    public String record;
    public String uri;

    public User() {
    }

    public User(String uid, String email, String name, String record, String uri) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.record = record;
        this.uri = uri;
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

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
