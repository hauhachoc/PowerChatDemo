package com.example.hautran.myapplication.models;

import java.util.ArrayList;


public class ListFriend {
    private ArrayList<Friend> listFriend;

    public ArrayList<Friend> getListFriend() {
        return listFriend;
    }

    public ListFriend(){
        listFriend = new ArrayList<>();
    }

    public void setListFriend(ArrayList<Friend> listFriend) {
        this.listFriend = listFriend;
    }
}
