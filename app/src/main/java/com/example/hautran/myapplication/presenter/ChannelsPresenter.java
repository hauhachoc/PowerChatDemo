package com.example.hautran.myapplication.presenter;

import android.support.annotation.NonNull;

import com.example.hautran.myapplication.models.Room;
import com.example.hautran.myapplication.utils.Constants;
import com.example.hautran.myapplication.views.ChannelsView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by hautran on 16/08/17.
 */

public class ChannelsPresenter {

    private ChannelsView view;

    public ChannelsPresenter(ChannelsView v) {
        this.view = v;
    }

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    public void createANewChannel() {
        view.onLoading();
        String channel = view.getKeyToCreateChannel();
        String idGroup = (Constants.UID + System.currentTimeMillis()).hashCode() + "";
        Room room = new Room();
        room.groupInfo.put("name", channel);
        room.groupInfo.put("admin", Constants.UID);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Channel/" + idGroup);

        myRef.setValue(room, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                view.onDismissLoading();
                if (databaseError == null) {
                    view.onCreateChannelSuccess();
                } else {
                    view.onCreateChannelFailed(databaseError.getMessage());
                }
            }
        });
    }

    public void onDeleteChannel(int pos, ArrayList<String> arrRoom) {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Channel");
        myRef.child(arrRoom.get(pos)).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                view.onDeleteChannelSuccess();
            }
        });
    }
}
