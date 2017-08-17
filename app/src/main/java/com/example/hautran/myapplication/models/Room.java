package com.example.hautran.myapplication.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Room implements Parcelable{
    public ArrayList<String> member;
    public Map<String, String> groupInfo;

    public Room(){
        member = new ArrayList<>();
        groupInfo = new HashMap<String, String>();
    }

    protected Room(Parcel in) {
        member = in.createStringArrayList();
    }

    public static final Creator<Room> CREATOR = new Creator<Room>() {
        @Override
        public Room createFromParcel(Parcel in) {
            return new Room(in);
        }

        @Override
        public Room[] newArray(int size) {
            return new Room[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringList(member);
    }
}
