package com.example.hautran.myapplication.presentation.channels;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.hautran.myapplication.presentation.AbstractActivity;
import com.example.hautran.myapplication.presentation.BaseActivity;
import com.example.hautran.myapplication.R;
import com.example.hautran.myapplication.adapter.ChannelAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hautran on 16/08/17.
 */

public class AllChannelsActivity extends AbstractActivity {


    @Override
    protected int getViewLayoutId() {
        return R.layout.activity_top;
    }

    @Override
    public int getViewContainerId() {
        return R.id.fragmentContainer;
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentContainer, fragment)
                .commitAllowingStateLoss();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        replaceFragment(new ChannelsFragment());
    }
}
