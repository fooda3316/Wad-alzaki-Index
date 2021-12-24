package com.fooda.wadalzaki.helpers.login;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.fooda.wadalzaki.helpers.DialogPresenter;
import com.fooda.wadalzaki.helpers.SharedPreferencesHelper;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.fooda.wadalzaki.utils.Constants.GOOGLE_CLIENT_ID;


public class LogoutHelper implements GoogleApiClient.OnConnectionFailedListener {
    private DialogPresenter dialogPresenter;
    private Context context;
    private FragmentActivity activity;
    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private SharedPreferencesHelper preferencesHelper;
    private static final String TAG = "LogoutHelper";


    public LogoutHelper(Context context,FragmentActivity activity) {
        this.activity=activity;
        this.context = context;
        dialogPresenter=new DialogPresenter(context);
        initiateGoogleClient(context);
        preferencesHelper=new SharedPreferencesHelper(context);
    }

    private void initiateGoogleClient(Context context) {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(GOOGLE_CLIENT_ID)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .enableAutoManage(activity, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }

    public  void firebaseLogout(){
        Log.d(TAG, "firebaseLogout: ");
        mAuth = FirebaseAuth.getInstance();
        if (mAuth != null) {
            mAuth.signOut();
            preferencesHelper.saveIsUserLogin(false);
            Log.d(TAG, "onAuthStateChanged:signed_in:" + mAuth.getUid());
        } else {
            Log.d(TAG, "onAuthStateChanged:signed_out");
        }
//        mAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if (user != null) {
//                    mAuth.signOut();
//                    preferencesHelper.saveIsUserLogin(false);
//                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
//                } else {
//                    Log.d(TAG, "onAuthStateChanged:signed_out");
//                }
//
//            }
//        };
    }
    private void deleteAccount() {
        Log.d(TAG, "ingreso a deleteAccount");
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        currentUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG,"OK! Works fine!");

                } else {
                    Log.w(TAG,"Something is wrong!");
                }
            }
        });
    }
    public  void googleLogout(){
        dialogPresenter.showMyLoadingDialog();
        //mAuth.signOut();
        // Google sign out
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        dialogPresenter.dismissMyLoadingDialog();
                        preferencesHelper.saveIsUserLogin(false);
                        firebaseLogout();
                    }
                }
        );
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "onConnectionFailed: "+connectionResult.getErrorMessage());
    }
    public  void facebookLogout(FacebookHelper helper){
        helper.logoutFacebook();
        preferencesHelper.saveIsUserLogin(false);
        firebaseLogout();

    }
}
