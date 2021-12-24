package com.fooda.wadalzaki.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.fooda.wadalzaki.R;
import com.google.android.material.snackbar.Snackbar;
import com.kishandonga.csbx.CustomSnackbar;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;


public class MyPermissions {
    private Activity activity;
    private CustomSnackbar sb;

    public static final int PERMISSION_REQUEST_CODE=1234;
    public MyPermissions(Activity activity) {
        this.activity=activity;
    }
    public boolean checkPermission(String permission) {
        if (ContextCompat.checkSelfPermission(activity,permission )
                != PackageManager.PERMISSION_GRANTED) {

            return false;
        }
        return true;
    }
    public void requestPermission(String permission) {
        ActivityCompat.requestPermissions(activity,
                new String[]{permission},
                PERMISSION_REQUEST_CODE);
    }
    @SuppressLint("ResourceAsColor")
    public void showAlert(String message){
        sb = new CustomSnackbar(activity);
        sb.message(message);
        sb.padding(15);
        sb.cornerRadius(15);
        sb.textColor(R.color.red);
        sb.backgroundColor(R.color.black);
        sb.duration(Snackbar.LENGTH_LONG);
        sb.withAction(android.R.string.ok, new Function1<Snackbar, Unit>() {
            @Override
            public Unit invoke(Snackbar snackbar) {
                snackbar.dismiss();
                return null;
            }
        });
        sb.show();

    }
}
