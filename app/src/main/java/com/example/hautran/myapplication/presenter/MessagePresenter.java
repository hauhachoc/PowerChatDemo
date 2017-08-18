package com.example.hautran.myapplication.presenter;

import android.support.annotation.NonNull;

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

    public MessagePresenter(MessageView v){
        this.view = v;
    }

    public void onSendMessageEvent(){
        roomID = view.getIdRoom();
        message = view.getMessageData();
        FirebaseDatabase.getInstance().getReference().child("message/" + roomID).push().setValue(message).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task!=null){
                    view.onSendMessageSuccess();
                }
            }
        });
    }
}
