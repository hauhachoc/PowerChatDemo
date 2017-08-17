package com.example.hautran.myapplication.models;

import java.util.ArrayList;


public class Consersation {
    private ArrayList<Message> listMessageData;
    public Consersation(){
        listMessageData = new ArrayList<>();
    }

    public ArrayList<Message> getListMessageData() {
        if (listMessageData==null)
        {
            return null;
        }
        return listMessageData;
    }
}
