package com.example.hautran.myapplication.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.example.hautran.myapplication.R;

import butterknife.ButterKnife;

/**
 * Created by hautran on 16/08/17.
 */

public class MyDialogBuilder {
    private Context context;
    private String title, message, positiveText, negativeText;
    private DialogInterface.OnClickListener positiveListener, negativeListener;
    private DialogInterface.OnDismissListener dismissListener;
    private boolean autoDismiss = true, cancelable = true;
    private boolean single = false;

    public MyDialogBuilder(Context context, String title, String message) {
        this.context = context;
        this.title = title;
        this.message = message;
    }

    public MyDialogBuilder(Context context, int title, int message) {
        this.context = context;
        if(title != 0)
        this.title = context.getString(title);
        this.message = context.getString(message);
    }

    public MyDialogBuilder positiveText(String val) {
        this.positiveText = val;
        return this;
    }

    public MyDialogBuilder negativeText(String val) {
        this.negativeText = val;
        return this;
    }

    public MyDialogBuilder positiveText(int val) {
        this.positiveText = context.getString(val);
        return this;
    }

    public MyDialogBuilder negativeText(int val) {
        this.negativeText = context.getString(val);
        return this;
    }

    public MyDialogBuilder onPositive(DialogInterface.OnClickListener listener) {
        this.positiveListener = listener;
        return this;
    }

    public MyDialogBuilder onNegative(DialogInterface.OnClickListener listener) {
        this.negativeListener = listener;
        return this;
    }

    public MyDialogBuilder autoDismiss(boolean val) {
        this.autoDismiss = val;
        return this;
    }

    public MyDialogBuilder cancelable(boolean val) {
        this.cancelable = val;
        return this;
    }

    public MyDialogBuilder single(boolean val) {
        this.single = val;
        return this;
    }

    public MyDialogBuilder onDismiss(DialogInterface.OnDismissListener listener) {
        this.dismissListener = listener;
        return this;
    }

    public Dialog build() {
        final Dialog customDialog = new Dialog(context, R.style.AppDialogTheme);
        customDialog.setContentView(R.layout.custom_alert_dialog);
        TextView titleView = ButterKnife.findById(customDialog, R.id.title);
        TextView messageView = ButterKnife.findById(customDialog, R.id.message);
        Button positiveBtn = ButterKnife.findById(customDialog, R.id.positive_btn);
        Button negativeBtn = ButterKnife.findById(customDialog, R.id.negative_btn);
        if(TextUtils.isEmpty(title)) {
            titleView.setVisibility(View.GONE);
        } else {
            titleView.setVisibility(View.VISIBLE);
            titleView.setText(title);
        }
        if(TextUtils.isEmpty(message)){
            messageView.setVisibility(View.GONE);
        }else {
            messageView.setVisibility(View.VISIBLE);
            messageView.setText(message);
        }
        if(single) {
            negativeBtn.setVisibility(View.GONE);
            positiveBtn.setBackground(null);
        }
        if(!TextUtils.isEmpty(positiveText)) positiveBtn.setText(positiveText);
        if(!TextUtils.isEmpty(negativeText)) negativeBtn.setText(negativeText);
        positiveBtn.setOnClickListener(v -> {
            if(positiveListener != null) positiveListener.onClick(customDialog, DialogInterface.BUTTON_POSITIVE);
            if(autoDismiss) customDialog.dismiss();
        });
        negativeBtn.setOnClickListener(v -> {
            if(negativeListener != null) negativeListener.onClick(customDialog, DialogInterface.BUTTON_NEGATIVE);
            if(autoDismiss) customDialog.dismiss();
        });
        customDialog.setCancelable(cancelable);
        customDialog.setCanceledOnTouchOutside(cancelable);
        if(cancelable && dismissListener != null)
            customDialog.setOnDismissListener(dismissListener);
        return customDialog;
    }

}
