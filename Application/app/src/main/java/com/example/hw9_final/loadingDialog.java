package com.example.hw9_final;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class loadingDialog {
private Activity activity;
private AlertDialog dialog;

    public loadingDialog(Activity myActivity){
        activity = myActivity;
    }

    public void startLoadingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.DialogTheme);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.custom_dialog,null));
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();

    }

    public void dismissDialog(){

        dialog.dismiss();
    }
}
