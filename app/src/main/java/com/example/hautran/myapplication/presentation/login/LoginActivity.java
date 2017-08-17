package com.example.hautran.myapplication.presentation.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hautran.myapplication.presentation.BaseActivity;
import com.example.hautran.myapplication.presentation.channels.AllChannelsActivity;
import com.example.hautran.myapplication.R;
import com.example.hautran.myapplication.models.User;
import com.example.hautran.myapplication.utils.Constants;
import com.example.hautran.myapplication.widget.DialogHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hautran on 16/08/17.
 */

public class LoginActivity extends BaseActivity implements LoginView {

    @BindView(R.id.edtInputName)
    EditText edtName;

    @BindView(R.id.btnLogin)
    AppCompatButton btnLogin;

    @BindView(R.id.layoutLogin)
    ConstraintLayout layoutLogin;

    private LoginPresenter presenter;
    private final String TAG = "LoginActivity";
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
        dialogHelper = new DialogHelper(this);
        presenter = new LoginPresenter(this);
        setupUI(layoutLogin);
        loginByFirebaseAnonymously();

    }

    @Override
    public void onStart() {
        super.onStart();
        currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            loginByFirebaseAnonymously();
        }
    }


    private void loginByFirebaseAnonymously() {
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInAnonymously:success");
                            currentUser = mAuth.getCurrentUser();
                            Constants.UID = currentUser.getUid();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInAnonymously:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @OnClick(R.id.btnLogin)
    public void onLogin() {
        if (currentUser != null) {
            if (TextUtils.isEmpty(edtName.getText().toString().trim())) {
                dialogHelper.alert(null, "Please your name must filled");
            } else {
                presenter.onLoginClickEvent();
            }
        } else {
            loginByFirebaseAnonymously();
        }
    }


    @Override
    public void onLoginSuccess() {
        toNextScreen();
    }

    @Override
    public void onLoginFailed(String message) {
        Log.d(TAG, message);
        dialogHelper.alert(null, message);
    }

    @Override
    public User getName() {
        User user = new User(edtName.getText().toString());
        return user;
    }

    @Override
    public void toNextScreen() {
        Intent mainView = new Intent(this, AllChannelsActivity.class);
        startActivity(mainView);
        finish();
    }

    @Override
    public void onLoading() {
        dialogHelper.showProgress();
    }

    @Override
    public void onDismissLoading() {
        dialogHelper.dismissProgress();
    }

}
