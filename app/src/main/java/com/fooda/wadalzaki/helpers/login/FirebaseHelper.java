package com.fooda.wadalzaki.helpers.login;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.fooda.wadalzaki.R;
import com.fooda.wadalzaki.helpers.AddInfoUploadHelper;
import com.fooda.wadalzaki.helpers.DialogPresenter;
import com.fooda.wadalzaki.helpers.FirebaseDatabaseHelper;
import com.fooda.wadalzaki.helpers.SharedPreferencesHelper;
import com.fooda.wadalzaki.helpers.UploadDownloadHelper;
import com.fooda.wadalzaki.ui.home.HomeActivity;
import com.fooda.wadalzaki.ui.login.LogInActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class FirebaseHelper {
    private  Context context;
    private DialogPresenter presenter;
    private final FirebaseAuth mAuth;
    private final SharedPreferencesHelper helper;
    private UploadDownloadHelper downloadHelper;
    private static final String TAG = "FirebaseHelper";
    private AddInfoUploadHelper addInfoUploadHelper;
    private String deviceToken;
    private final FirebaseDatabaseHelper firebaseDatabaseHelper;

    public FirebaseHelper(Context context) {
        this.context = context;
        presenter=new DialogPresenter(context);
        mAuth = FirebaseAuth.getInstance();
        helper=new SharedPreferencesHelper(context);
        addInfoUploadHelper=new AddInfoUploadHelper(context);
        downloadHelper=new UploadDownloadHelper(context);
        firebaseDatabaseHelper=new FirebaseDatabaseHelper(context);

    }



    private void showLog(String message) {
        Log.d(TAG, "showLog: message: "+message);
    }

    public void createFirebaseUser(String email,String pass,String fullName,String loginType,String phone,String address) {
     //   Log.d(TAG, "createUser: info :"+info.getPassword());
       // showMyLoadingDialog();
        mAuth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(task -> {
                   // dismissMyLoadingDialog();
                    if(!task.isSuccessful()) {


                        showLog(Objects.requireNonNull(task.getException()).getMessage());
                    }
                    else{
                        Log.e(TAG, "createFirebaseUser: success" );
                        task.getResult().getUser().getEmail();
                        //helper.saveIsUserLogin(true);
                        addInfoUploadHelper.uploadImageWithData(fullName,email,phone,address);
                        //addInfoUploadHelper.uploadImageWithData(uri,fullName,email,phone,address);
                        //downloadHelper.uploadImage(uri,fullName,email);
                        //helper.saveUserLoginInfo(fullName,email,loginType);
                       // downloadHelper.saveValues(email);
                        //context.startActivity(new Intent(context, HomeActivity.class));

                    }
                });
    }
    public void createUser(String fullName,String email,String pass,String imageUri,String loginType) {
        Log.d(TAG, "createUser: email "+email+"\n loginType "+loginType);
        //showMyLoadingDialog();
        mAuth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(task -> {
                  //  dismissMyLoadingDialog();
                    if(!task.isSuccessful()) {
                        String message="The email address is already in use by another account.";
                        if ((task.getException().getMessage()).equals(message)){
                            Log.e(TAG, "createUser: The email address is already in use by another account" );
                            firebaseDatabaseHelper.finishLoginUser(fullName,imageUri,loginType);
                            //loginUser(email,pass,loginType);
                        }
                        showLog(Objects.requireNonNull(task.getException()).getMessage());
                        LogInActivity.mDialog.dismiss();

                    }
                    else{

                       // helper.saveIsUserLogin(true);
                     //   downloadHelper.saveUserInfo("","","","","");
                       // downloadHelper.saveUserInfo(fullName,email,pass,imageUri,loginType);
                       // helper.saveUserLoginInfo(fullName,imageUri,loginType);
                        firebaseDatabaseHelper.finishLoginUser(fullName,imageUri,loginType);

                       // goToAdDInfo(loginType);

                    }
                });

    }

    public void loginUser(String email,String pass) {
        Log.d(TAG, "loginUser: ");
       //showMyLoadingDialog();
        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(task -> {
                    if(!task.isSuccessful()) {
                        showLog(Objects.requireNonNull(task.getException()).getMessage());
                        presenter.showAlert(context.getResources().getString(R.string.invalid_username_or_password));
                        LogInActivity.mDialog.dismiss();
                    }
                    else{
                        String ID=task.getResult().getUser().getUid();
                        firebaseDatabaseHelper.getAndSaveValues(ID);
                        String userId=mAuth.getCurrentUser().getUid();
                        helper.saveUserId(userId);
                        //startHomeActivity();

                    }
                });


    }


    private void sendUserToMain() {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

}
