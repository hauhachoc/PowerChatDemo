package com.example.hautran.myapplication.presentation.login;

import com.example.hautran.myapplication.models.User;
import com.example.hautran.myapplication.utils.Constants;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by hautran on 16/08/17.
 */

public class LoginPresenter {

    private LoginView view;

    LoginPresenter(LoginView v) {
        this.view = v;
    }

    public void onLoginClickEvent() {
        view.onLoading();
        User name = view.getName();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("User").child(Constants.UID);

        myRef.setValue(name, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                view.onDismissLoading();
                if (databaseError==null){
                    view.onLoginSuccess();
                }else {
                    view.onLoginFailed(databaseError.getMessage());
                }
            }
        });
    }
}
