package com.example.hautran.myapplication;

import android.app.Application;
import android.content.res.Configuration;

import com.example.hautran.myapplication.utils.CustomSharedPreferences;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

/**
 * Created by hautran on 17/08/17.
 */

public class ChatApplication extends Application {

    private static ChatApplication sharedInstance;

    public static ChatApplication getSharedInstance() {
        return sharedInstance;
    }

    private static FirebaseAuth mAuth;
    public static FirebaseAuth getFirebaseAuthInstance() {
        return mAuth;
    }

    private static FirebaseUser currentUser;
    public static FirebaseUser getFirebaseUserInstance() {
        return currentUser;
    }

    private static FirebaseDatabase database;
    public static FirebaseDatabase getFirebaseDatabaseInstance() {
        return database;
    }

    private static DatabaseReference myRef;
    public static DatabaseReference getDatabaseReferenceInstance() {
        return myRef;
    }

    private Locale locale = null;

    @Override
    public void onCreate() {
        super.onCreate();
        CustomSharedPreferences.init(this);
        sharedInstance = this;
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}

