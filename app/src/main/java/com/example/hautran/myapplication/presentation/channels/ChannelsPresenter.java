package com.example.hautran.myapplication.presentation.channels;

import com.example.hautran.myapplication.models.Room;
import com.example.hautran.myapplication.utils.Constants;
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
        String idGroup = (Constants.UID + System.currentTimeMillis()).hashCode() + "";
        Room room = new Room();
        room.groupInfo.put("name", channel);
        room.groupInfo.put("admin", Constants.UID);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Channel/"+ idGroup);

        myRef.setValue(room, new DatabaseReference.CompletionListener() {
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
