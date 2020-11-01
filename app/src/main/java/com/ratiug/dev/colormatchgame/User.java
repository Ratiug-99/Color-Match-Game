package com.ratiug.dev.colormatchgame;

public class User{

    public String email;
    public String name;
    public String record;
    public String uri;

    public User(String email, String name, String record, String uriImage) {
        this.email = email;
        this.name = name;
        this.record = record;
        this.uri = uriImage;
    }

    public User() {
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
