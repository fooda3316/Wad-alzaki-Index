package com.fooda.wadalzaki.helpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;

import androidx.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.fooda.wadalzaki.R;
import com.google.android.material.snackbar.Snackbar;
import com.kishandonga.csbx.CustomSnackbar;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class DialogPresenter {
    private Context context;
    private LoadingDialog loadingDialog;
    private MaterialDialog mDialog,alertDialog;
    private CustomSnackbar sb;

    public DialogPresenter(Context context) {
        this.context = context;

    }

    public void showMyLoadingDialog(){
        loadingDialog= new LoadingDialog(context);
        loadingDialog.setLoadingText(context.getResources().getString(R.string.progress_wait));
        loadingDialog.show();
    }
    public void dismissMyLoadingDialog(){
        loadingDialog.close();

    }
    public void showAlertDialog(){
mDialog=new MaterialDialog.Builder(context)
        .title("خدمات قوقل بلي")
        .contentColor(Color.RED)
        .content("عفواً خدمات قوقل بلي غير متوفرة لديك")
        .positiveColor(Color.GREEN)
        .negativeColor(Color.BLUE)
        .positiveText("نعم")
        .cancelable(false)
        .onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
            }
        })
        .show();
    }
    @SuppressLint("ResourceAsColor")
    public void showAlert(String message){
        sb = new CustomSnackbar(context);
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
