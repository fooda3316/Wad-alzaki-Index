package com.fooda.wadalzaki.helpers.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

/**
 * FacebookHelper.java
 */
public class FacebookHelper {
    private CallbackManager callbackManager;
    private Activity activity;
    private AccessToken accessToken;
    private OnFbSignInListener fbSignInListener;
    private static final String TAG = "FacebookHelper";
  //  private DialogPresenter presenter;
    private FirebaseHelper firebaseHelper;
    public FacebookHelper(String F) {
        logoutFacebook();
    }

    /**
     * Interface to listen the Facebook connect
     */
    public interface OnFbSignInListener {
        void OnFbSuccess(GraphResponse graphResponse);

        void OnFbError(String errorMessage);
    }

    public FacebookHelper(Activity activity, OnFbSignInListener fbSignInListener) {
        this.activity = activity;
        this.fbSignInListener = fbSignInListener;
        ///logoutFacebook();
        firebaseHelper=new FirebaseHelper(activity);
    }
public void looIn(LoginButton facebookSignIn){
      callbackManager = CallbackManager.Factory.create();

    LoginManager.getInstance().setLoginBehavior(LoginBehavior.WEB_VIEW_ONLY).logOut();
   LoginManager.getInstance().logOut();
    facebookSignIn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            Log.e("onSuccess","login Success");
            callGraphAPI(loginResult.getAccessToken());
        }

        @Override
        public void onCancel() {
           // LoginManager.getInstance().logOut();
            fbSignInListener.OnFbError("User cancelled.");
            accessToken = AccessToken.getCurrentAccessToken();
            if (accessToken == null) {
                Log.e("AccessToken","Login unSuccessful..Please contact developer... ");
            } else {

                Log.e("AccessToken","Login Successful... ");
            }        }

        @Override
        public void onError(FacebookException error) {
            if (error instanceof FacebookAuthorizationException) {
                if (AccessToken.getCurrentAccessToken() != null) {
                    LoginManager.getInstance().logOut();
                }
            }
            fbSignInListener.OnFbError("onError exception "+error.toString());
                }
    });
}

    private void callGraphAPI(AccessToken accessToken) {
        Log.d(TAG, "callGraphAPI: accessToken "+accessToken);
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d(TAG, "onCompleted:response "+response);
                        fbSignInListener.OnFbSuccess(response);
                    }
                });
        Bundle parameters = new Bundle();
        //Explicitly we need to specify the fields to get values else some values will be null.
       parameters.putString("fields", "id,birthday,first_name,gender,last_name,link,location,name");
        request.setParameters(parameters);
        request.executeAsync();
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
       // presenter.showMyLoadingDialog();
        if (callbackManager != null)
            callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    void logoutFacebook(){
       // LoginManager.getInstance().logOut();
        if(AccessToken.getCurrentAccessToken()!=null){
            AccessToken accessToken=AccessToken.getCurrentAccessToken();
            new GraphRequest(accessToken
                    ,"/"+accessToken.getUserId()+"/permissions/"
                    ,null
                    , HttpMethod.DELETE
                    ,new GraphRequest.Callback() {
                @Override
                public void onCompleted(GraphResponse graphResponse) {
                    Log.e("GraphResponse","GraphResponse");
                    LoginManager.getInstance().logOut();
                }
            }).executeAsync();
            AccessToken.setCurrentAccessToken(null);
        }
          }

}
