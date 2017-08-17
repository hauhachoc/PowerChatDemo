package com.example.hautran.myapplication.models;

/**
 * Created by hautran on 16/08/17.
 */

public class User {

    public String userName;
    public String message;

    public User() {

    }

    public User(String name) {
        this.userName = name;
    }

    public User(String userName, String message) {
        this.userName = userName;
        this.message = message;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
