package com.web.chesswebsite.model;

import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable {
    public String email_encrypt;
    public String password_encrypt;
    public String name;
    public String image;
    public int userType;
    public UUID id;
    public long rank;

    public User(String email, String image, String name) {
        this.email_encrypt = email;
        this.image = image;
        this.name = name;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{\n" +
                "email' :" + email_encrypt + '\'' + "\n" +
                ", password':" + password_encrypt + '\'' + "\n" +
                ", name' :" + name + '\'' + "\n" +
                ", id :" + id + "\n" +
                "\n}";
    }

}