package com.example.hautran.myapplication.presentation.channels;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by hautran on 16/08/17.
 */

public class ChannelsPresenter {

    private ChannelsView view;

    ChannelsPresenter(ChannelsView v){
        this.view = v;
    }

    public void createANewChannel(){
        view.onLoading();
        String channel = view.getKeyToCreateChannel();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Channel").child(channel);

        myRef.setValue(channel, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                view.onDismissLoading();
                if (databaseError==null){
                    view.onCreateChannelSuccess();
                }else {
                    view.onCreateChannelFailed(databaseError.getMessage());
                }
            }
        });
    }
}
