package com.example.hautran.myapplication.views;

import com.example.hautran.myapplication.models.User;

/**
 * Created by hautran on 16/08/17.
 */

public interface LoginView {
    public void onLoginSuccess();
    public void onLoginFailed(String message);
    public User getName();
    public void toNextScreen();
    public void onLoading();
    public void onDismissLoading();
}
