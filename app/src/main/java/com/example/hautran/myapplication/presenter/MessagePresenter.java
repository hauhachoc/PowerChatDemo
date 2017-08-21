package com.example.hautran.myapplication.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.hautran.myapplication.models.Message;
import com.example.hautran.myapplication.views.MessageView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by hautran on 18/08/17.
 */

public class MessagePresenter {

    MessageView view;
    private String roomID ;
    private Message message ;
    private final String TAG = "MessagePresenter";

    public MessagePresenter(MessageView v){
        this.view = v;
    }

    public void onSendMessageEvent(){
        roomID = view.getIdRoom();
        message = view.getMessageData();
        writeData(roomID, message);
    }

    public void sendImageFile(){
        roomID = view.getIdRoom();
        message = view.getMessageData();
        Log.d(TAG, message.picture+","+message.idSender);
        writeData(roomID, message);
    }

    public void writeData(String id, Message mess){
        FirebaseDatabase.getInstance().getReference().child("message/" + id).push().setValue(mess).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task!=null){
                    view.onSendMessageSuccess();
                }
            }
        });
    }
}
