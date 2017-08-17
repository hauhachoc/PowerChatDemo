package com.example.hautran.myapplication.widget;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.example.hautran.myapplication.R;

import butterknife.ButterKnife;

/**
 * Created by hautran on 16/08/17.
 */

public class DialogHelper {

    private Activity activity;
    private ProgressDialog progressDialog;

    public DialogHelper(Activity activity) {
        this.activity = activity;
    }

    public void alert(String title, String message) {
        alert(title, message,null);
    }

    public void alert(String title, String message, DialogInterface.OnDismissListener dismissListener) {
        Dialog dialog = new MyDialogBuilder(activity, title, message)
                .positiveText("OK")
                .onDismiss(dismissListener)
                .single(true)
                .build();
        dialog.show();
    }

    public void action(String title, String message, String positiveText, String negativeText,
                       DialogInterface.OnClickListener takeAction) {
        Dialog dialog = new MyDialogBuilder(activity, title, message)
                .positiveText(positiveText)
                .negativeText(negativeText)
                .onPositive(takeAction)
                .build();
        dialog.show();
    }
    public void action(String title, String message, String positiveText, String negativeText,
                       DialogInterface.OnClickListener takeAction, DialogInterface.OnClickListener cancelAction) {
        Dialog dialog = new MyDialogBuilder(activity, title, message)
                .positiveText(positiveText)
                .negativeText(negativeText)
                .onPositive(takeAction)
                .onNegative(cancelAction)
                .build();
        dialog.show();
    }

    public void showProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        } else {
            progressDialog = createProgressDialog();
        }
        progressDialog.show();
        progressDialog.setContentView(R.layout.layout_progress_loading);
    }

    public void dismissProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public MyDialogBuilder createBuilder(int title, int content, int positiveText, int negativeText) {
        return new MyDialogBuilder(activity, title, content)
                .positiveText(positiveText)
                .negativeText(negativeText);
    }

    public void showLeavingAlert(String url) {
        Dialog dialog = new MyDialogBuilder(activity, "Alert", "Message")
                .onPositive((dialog1, which) -> {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(url));
                    activity.startActivity(browserIntent);
                }).build();
        dialog.show();
    }

    public void showLeavingAlert(DialogInterface.OnClickListener onClickListener){
        Dialog dialog = new MyDialogBuilder(activity, "Alert", "Message")
                .onPositive(onClickListener).build();
        dialog.show();
    }

    public void profileDialog(String message, DialogInterface.OnDismissListener dismissListener) {
        final Dialog customDialog = new Dialog(activity, R.style.AppDialogTheme);
        customDialog.setContentView(R.layout.custom_alert_dialog);
        TextView titleView = ButterKnife.findById(customDialog, R.id.title);
        TextView messageView = ButterKnife.findById(customDialog, R.id.message);
        Button positiveBtn = ButterKnife.findById(customDialog, R.id.positive_btn);
        Button negativeBtn = ButterKnife.findById(customDialog, R.id.negative_btn);

        positiveBtn.setText("OK");
        titleView.setText("Field");
        messageView.setText(message);
        messageView.setGravity(Gravity.START);
        negativeBtn.setVisibility(View.GONE);
        positiveBtn.setBackground(null);
        positiveBtn.setOnClickListener(v -> customDialog.dismiss());
        customDialog.setCancelable(true);
        customDialog.setCanceledOnTouchOutside(true);
        customDialog.setOnDismissListener(dismissListener);
        customDialog.show();
    }

    private ProgressDialog createProgressDialog() {
        ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.getWindow().setDimAmount(0.2f);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(true);
        return progressDialog;
    }
}
