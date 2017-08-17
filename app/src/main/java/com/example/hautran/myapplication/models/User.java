package com.example.hautran.myapplication.models;

/**
 * Created by hautran on 16/08/17.
 */

public class User {

    public String userName;
    public String group;
    public Message message;


    public User(){
        message = new Message();
        message.idReceiver = "0";
        message.idSender = "0";
        message.text = "";
        message.timestamp = 0;
    }

    public User(String name) {
        this.userName = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
