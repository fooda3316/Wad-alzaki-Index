package com.fooda.wadalzaki.ui.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.afollestad.materialdialogs.MaterialDialog;
import com.fooda.wadalzaki.R;
import com.fooda.wadalzaki.helpers.SharedPreferencesHelper;
import com.fooda.wadalzaki.helpers.UploadDownloadHelper;
import com.fooda.wadalzaki.helpers.login.FacebookHelper;
import com.fooda.wadalzaki.helpers.login.FirebaseHelper;
import com.fooda.wadalzaki.helpers.login.GoogleHelper;
import com.fooda.wadalzaki.model.UserMeta;
import com.fooda.wadalzaki.themes.SetThemes;
//import com.fooda.wadalzaki.ui.auth.AddInfoActivity;
import com.fooda.wadalzaki.ui.home.HomeActivity;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphResponse;
import com.facebook.LoggingBehavior;
import com.facebook.login.LoginFragment;
import com.facebook.login.widget.LoginButton;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.firebase.auth.FirebaseAuth;
import com.shobhitpuri.custombuttons.GoogleSignInButton;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import org.json.JSONException;
import org.json.JSONObject;

import static com.fooda.wadalzaki.utils.Constants.FACEBOOK_TYPE;
import static com.fooda.wadalzaki.utils.Constants.FIREBASE_TYPE;
import static com.fooda.wadalzaki.utils.Constants.GOOGLE_CLIENT_ID;
import static com.fooda.wadalzaki.utils.Constants.GOOGLE_TYPE;
import static com.fooda.wadalzaki.utils.Constants.RC_GOOGLE_SIGN_IN;



public class LogInActivity extends AppCompatActivity implements   FacebookHelper.OnFbSignInListener, GoogleHelper.OnGoogleSignInListener{
    private static final int RC_SIGN_IN = 100;
    EditText email_login;
    EditText pass_login;
    AppCompatButton ok_login, skip;
    public static MaterialDialog mDialog;
   // public static LoadingDialog loadingDialog ;
    private FacebookHelper fbConnectHelper;
    private GoogleHelper gSignInHelper;
    private static final String TAG = LoginFragment.class.getSimpleName();
    private LoginButton facebookSignIn;
    private GoogleSignInButton googleSignInButton;
    private FirebaseHelper firebaseHelper;
    private SharedPreferencesHelper preferencesHelper;
    private  FirebaseAuth mAuth;
    private UploadDownloadHelper downloadHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setup();
        SetThemes.setThemes(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
//        FacebookSdk.setAutoInitEnabled(true);
//        FacebookSdk.fullyInitialize();
        setContentView(R.layout.activity_login);
        mAuth=FirebaseAuth.getInstance();
        firebaseHelper=new FirebaseHelper(this);
        downloadHelper=new UploadDownloadHelper(this);
        ok_login=findViewById(R.id.btn_login);
        skip =findViewById(R.id.btnSkip);
        email_login =findViewById(R.id.input_email_login);
        pass_login =findViewById(R.id.input_pass_login);
        facebookSignIn = findViewById(R.id.signInWithFacebookBtn);
        googleSignInButton = findViewById(R.id.signInWithGoogleBtn);
        preferencesHelper=new SharedPreferencesHelper(this);
        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // signIn();
              if(isGooglePlayServicesAvailable(LogInActivity.this)) {
                  showMyLoadingDialog();
                  gSignInHelper.signIn(LogInActivity.this); }
              else {
                  Toast.makeText(LogInActivity.this, "Sorry google play service is not present!", Toast.LENGTH_SHORT).show();              }
            }
        });

        //facebookSignIn.setLoginBehavior(LoginBehavior.WEB_VIEW_ONLY);
        fbConnectHelper.looIn(facebookSignIn);


        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(LogInActivity.this, HomeActivity.class));
            }
        });
        findViewById(R.id.btn_new_account).setOnClickListener(v -> startActivity(new Intent(LogInActivity.this,RegisterActivity.class)));
        ok_login.setOnClickListener(v -> {
          if (isAllTextsNotEmpty(email_login,pass_login)){
              showMyLoadingDialog();
              firebaseHelper.loginUser(email_login.getText().toString().trim(),pass_login.getText().toString().trim());
          }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
   private Boolean isAllTextsNotEmpty(EditText mail, EditText pass){
        if (mail.getText().toString().trim().isEmpty()){
            mail.setError("لا يمكن ترك هذا الحقل فارغا");
            vibrate();
            return false;
        }
       if (pass.getText().toString().trim().isEmpty()){
           mail.setError("لا يمكن ترك هذا الحقل فارغا");
           vibrate();
           return false;
       }
        return true;
   }

    private void setup() {
        GoogleHelper.setClientID(GOOGLE_CLIENT_ID);
        gSignInHelper = GoogleHelper.getInstance(this);
        gSignInHelper.initialize(this, this);
        fbConnectHelper = new FacebookHelper(this,this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null ) {
            showMyLoadingDialog();
            if (requestCode==RC_GOOGLE_SIGN_IN){
                Log.d(TAG, "onActivityResult: RC_GOOGLE_SIGN_IN");
                gSignInHelper.onActivityResult(requestCode, resultCode, data);

            }
            else {
                Log.d(TAG, "onActivityResult: RC_FACEBOOK_SIGN_IN");
                fbConnectHelper.onActivityResult(requestCode, resultCode, data);

            }
        }
        else {
            Log.d(TAG, "onActivityResult:there are no data ");
            //dismissMyLoadingDialog();
        }

    }


    private void displayLog(String string) {
        Log.d(TAG, "displayLog: message "+string);
    }


    private void startHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
       finishAffinity();
    }
    @SuppressLint("MissingPermission")
    public void vibrate() {
        Vibrator vibs = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibs.vibrate(200);
    }
    //Code to check if Facebook app is installed:
    public static boolean doesUserHaveFacebookAppInstalled(Context context){
        try{
            context.getPackageManager().getApplicationInfo("com.facebook.katana", 0 );
            return true;
        } catch( PackageManager.NameNotFoundException e ){
            return false;
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
      }


    public boolean isGooglePlayServicesAvailable(Activity activity) {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(activity);
        if(status != ConnectionResult.SUCCESS) {
            if(googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(activity, status, 2404).show();
            }
            return false;
        }
        return true;
    }


    @Override
    public void OnFbSuccess(GraphResponse graphResponse) {

        Log.d(TAG, "OnFbSuccess: graphResponse "+graphResponse);
        UserMeta meta = getUserModelFromGraphResponse(graphResponse);
        if(meta !=null) {
            preferencesHelper.saveUserLoginInfo(meta.userName,meta.profilePic,"f");
            preferencesHelper.saveIsUserLogin(true);
            Log.d(TAG, "OnFbSuccess: data ");
            firebaseHelper.createUser(meta.userName,meta.userEmail,"1a2b3c4d5e6f7g8h9i",meta.profilePic,FACEBOOK_TYPE );
            ///goToAdDInfo("f");
        }
    }



    private UserMeta getUserModelFromGraphResponse(GraphResponse graphResponse) {
        UserMeta userMeta = new UserMeta();
        try {
            JSONObject jsonObject = graphResponse.getJSONObject();
            userMeta.userName = jsonObject.getString("name");
           // userMeta.userEmail = jsonObject.getString("email");
           String firstName=jsonObject.getString("first_name");
//            Random r = new Random();
//            String randomNumber = String.format("%04d", r.nextInt(1001));

            //userMeta.userEmail = "a.fooda@hotmail.com";
            String id = jsonObject.getString("id");
            userMeta.userEmail = firstName+id+"@gmail.com";

            String profileImg = "http://graph.facebook.com/"+ id+ "/picture?type=large";
            userMeta.profilePic = profileImg;
            Log.i(TAG,profileImg);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return userMeta;
    }

    @Override
    public void OnFbError(String errorMessage) {
        dismissMyLoadingDialog();
        displayLog(errorMessage);
    }


    @Override
    public void OnGSignSuccess(GoogleSignInAccount acct, UserMeta person) {
        dismissMyLoadingDialog();
        UserMeta meta = new UserMeta();
        meta.userName = (acct.getDisplayName()==null)?"":acct.getDisplayName();
        meta.userEmail = acct.getEmail();

        Log.i(TAG, "OnGSignSuccess: AccessToken "+ acct.getIdToken());

        Uri photoUrl = acct.getPhotoUrl();
        if(photoUrl!=null)
            meta.profilePic = photoUrl.toString();
        else
            meta.profilePic = "";
        Log.i(TAG, acct.getIdToken());
        //preferencesHelper.saveUserLoginInfo(userMeta.userName,userMeta.profilePic,"g");
        firebaseHelper.createUser(meta.userName,meta.userEmail,"1a2b3c4d5e6f7g8h9i",meta.profilePic,GOOGLE_TYPE );

        //   SharedPreferenceManager.getSharedInstance().saveUserModel(userMeta);
        //startHomeActivity();
    }


    @Override
    public void OnGSignError(GoogleSignInResult errorMessage) {
        displayLog("Google Sign in error@");
        dismissMyLoadingDialog();
    }
    private Boolean isGoogleLogged(){

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(gSignInHelper.getGoogleApiClient());
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the
            // OptionalPendingResult will be "done" and the
            // GoogleSignInResult will be available instantly.
            Log.d("TAG", "Got cached sign-in");
            return true;


        }
        return false;
    }
    public boolean isFacebookLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }
//    private void showMyLoadingDialog(){
//        loadingDialog= new LoadingDialog(this);
//        loadingDialog.setLoadingText(getResources().getString(R.string.progress_wait));
//        loadingDialog.show();
//    }
    private void dismissMyLoadingDialog(){
        mDialog.dismiss();

    }
    public void showMyLoadingDialog() {
        mDialog = new MaterialDialog.Builder(this)
                .title(R.string.app_name)
                .content(R.string.progress_wait)
                .progress(true, 0)
                .show();
    }
}