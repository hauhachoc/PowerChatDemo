package com.example.hautran.myapplication.presentation.channels;

import com.example.hautran.myapplication.models.Room;

/**
 * Created by hautran on 16/08/17.
 */

public interface ChannelsView {

    public String getKeyToCreateChannel();
    public void onCreateChannelSuccess();
    public void onCreateChannelFailed(String mess);
    public void onLoading();
    public void onDismissLoading();
}
