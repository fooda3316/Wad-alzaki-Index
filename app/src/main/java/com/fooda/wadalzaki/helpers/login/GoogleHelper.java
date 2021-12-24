package com.fooda.wadalzaki.helpers.login;

/**
 * Created by Santosh on 16-08-2018.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.fooda.wadalzaki.model.UserMeta;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;

import static com.fooda.wadalzaki.utils.Constants.RC_GOOGLE_SIGN_IN;


/**
 * GoogleHelper.java
 * This is helper class to use for google+ sign in.
 * For Error code refer : https://developers.google.com/android/reference/com/google/android/gms/common/ConnectionResult
 */
public class GoogleHelper {
    private final static String TAG = "GoogleHelper";
        private static GoogleSignInOptions gso;

        private static GoogleHelper sInstance;
        private static String webClientID;
        private GoogleApiClient googleApiClient;
        private Context context;
        private OnGoogleSignInListener loginResultCallback;
        private ResultCallback<Status> logoutResultCallback;
      //  private DialogPresenter presenter;
        private FirebaseHelper firebaseHelper;



    /**
     * This method should be called before calling any instance.
     * This is neccessary to get access token and id of user.
     * @param googleClientId
     */
    public static void setClientID(String googleClientId) {
        webClientID = googleClientId;
    }

    /**
     * Interface to listen the Google login
     */
    public interface OnGoogleSignInListener {
        void OnGSignSuccess(GoogleSignInAccount googleSignInAccount, @Nullable UserMeta meta);

        void OnGSignError(GoogleSignInResult errorMessage);
    }

        public static GoogleHelper getInstance(Context context) {
            if (sInstance == null) {
                sInstance = new GoogleHelper( context);
            }
            return sInstance;
        }

        private GoogleHelper(Context context) {
         //   presenter=new DialogPresenter(context);
            firebaseHelper=new FirebaseHelper(context);

            // Configure sign-in to request the user's ID, email address, and basic
            // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
            gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestScopes(new Scope(Scopes.PLUS_LOGIN))
                    .requestProfile() //for profile related info
                    .requestEmail() //for email
                    .requestIdToken(webClientID) //for accessToken and id
                    .build();
        }

        public void initialize(FragmentActivity activity, OnGoogleSignInListener onGoogleSignInListener)
        {
            loginResultCallback = onGoogleSignInListener;
            context = activity;
            googleApiClient = new GoogleApiClient.Builder(activity)
                    .enableAutoManage(activity /* FragmentActivity */, new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult connectionResult) {
                            Log.e(TAG, "onConnectionFailed: " + connectionResult);
                        }
                    } /* OnConnectionFailedListener */)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                   // .addApi(Plus.API)
                    .build();

            googleApiClient.registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                @Override
                public void onConnected(Bundle bundle) {
                    Log.i(TAG, "onConnected");
                }

                @Override
                public void onConnectionSuspended(int i) {
                    Log.i(TAG, "onConnectionSuspended");
                }
            });

            googleApiClient.registerConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                @Override
                public void onConnectionFailed(ConnectionResult connectionResult) {
                    Log.i(TAG, "onConnectionFailed");
                }
            });
        }

        public boolean isConnected() {
            boolean isConnected = googleApiClient.isConnected();

            Log.i(TAG, "isConnected()" + isConnected);
            return isConnected;
        }

        public void signIn(Activity activity) {
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
            activity.startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN);
        }

        public void signOut() {
            Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            if (logoutResultCallback != null) {
                                logoutResultCallback.onResult(status);
                            }
                        }
                    });
        }

        private void handleSignInResult(GoogleSignInResult result) {
            Log.d(TAG, "handleSignInResult:" + result.isSuccess());
            if (result.isSuccess()) {
                Log.i(TAG, "Signed in");
                // Signed in successfully, show authenticated UI.
                final GoogleSignInAccount acct = result.getSignInAccount();
                //This code is just for getting google plus information like gender, birthday, aboutme etc
                if(acct!=null) {
                    if (loginResultCallback != null) {
                        String personName = acct.getDisplayName();
                        String personGivenName = acct.getGivenName();
                        String personFamilyName = acct.getFamilyName();
                        String personEmail = acct.getEmail();
                        String personId = acct.getId();
                        Uri personPhoto = acct.getPhotoUrl();
                        loginResultCallback.OnGSignSuccess(acct, new UserMeta(personName,personEmail,personPhoto.toString()));
                    }
                }

            } else {
                Log.i(TAG, "Signed out");

                if (loginResultCallback != null) {
                    loginResultCallback.OnGSignError(result);
                }
            }
        }

        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            Log.d(TAG, "onActivityResult: requestCode "+requestCode);
            //presenter.showMyLoadingDialog();
            // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
            if (requestCode == RC_GOOGLE_SIGN_IN) {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                handleSignInResult(result);
            }
            //firebaseHelper.onActivityResult(requestCode,data);
        }

        public void setLogoutResultCallback(ResultCallback<Status> callback) {
            logoutResultCallback = callback;
        }

    public GoogleApiClient getGoogleApiClient() {
        return googleApiClient;
    }



}
