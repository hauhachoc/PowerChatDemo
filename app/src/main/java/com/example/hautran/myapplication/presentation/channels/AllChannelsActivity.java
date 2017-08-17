package com.example.hautran.myapplication.presentation.channels;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.hautran.myapplication.R;
import com.example.hautran.myapplication.presentation.AbstractActivity;

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
