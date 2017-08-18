package com.example.hautran.myapplication.views;

import com.example.hautran.myapplication.models.Message;

/**
 * Created by hautran on 18/08/17.
 */

public interface MessageView {

    public String getIdRoom();
    public Message getMessageData();
    public void onSendMessageSuccess();

}
