package com.ratiug.dev.colormatchgame;

import java.io.Serializable;

public class User implements Serializable {

    public String email;
    public String name;
    public int record;

    public User() {
    }

    public User(String mEmail, String mName) {
        this.email = mEmail;
        this.name = mName;
    }

    public User(String email, String name, int record) {
        this.email = email;
        this.name = name;
        this.record = record;
    }
}
