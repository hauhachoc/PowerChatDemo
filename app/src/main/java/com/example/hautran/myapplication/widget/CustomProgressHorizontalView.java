package com.example.hautran.myapplication.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.example.hautran.myapplication.R;

/**
 * Created by hautran on 16/08/17.
 */

public class CustomProgressHorizontalView extends Dialog {

    public CustomProgressHorizontalView(Context mContext) {

        super(mContext, R.style.DiaLogStyle);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_custom_progress_horizontal);
        setCancelable(false);
    }

}
