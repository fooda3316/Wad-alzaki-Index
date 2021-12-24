package com.fooda.wadalzaki.ui.auth;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.afollestad.materialdialogs.MaterialDialog;
import com.fooda.wadalzaki.R;
import com.fooda.wadalzaki.helpers.AddInfoUploadHelper;
import com.fooda.wadalzaki.helpers.SharedPreferencesHelper;
import com.fooda.wadalzaki.utils.MyPermissions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;

import static com.fooda.wadalzaki.utils.Constants.FACEBOOK_TYPE;
import static com.fooda.wadalzaki.utils.Constants.FIREBASE_TYPE;
import static com.fooda.wadalzaki.utils.Constants.GOOGLE_TYPE;
import static com.fooda.wadalzaki.utils.Constants.PHONE_TYPE;
import static com.fooda.wadalzaki.utils.Constants.PICK_IMAGE_REQUEST;
import static com.fooda.wadalzaki.utils.Constants.USER_ID;
import static com.fooda.wadalzaki.utils.Constants.USER_LOGIN_NAME;
import static com.fooda.wadalzaki.utils.Constants.USER_LOGIN_TYPE;
import static com.fooda.wadalzaki.utils.Constants.USER_PHONE_NUMBER;
import static com.fooda.wadalzaki.utils.Constants.USER_TOKEN;

public class AddInfoActivity extends AppCompatActivity  implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private String phoneNum;
    private String uid;
    private String devicetoken;
    private String finalAddress;
    private EditText fullName, mUserEmail, mUserAddress,phoneNumber;
    private Button mSaveInfoBtn;
    private ImageView userSelectedImage;
    private Uri selectedImage;
    final String TAG = "GPS";
    private String loginType;
    private long UPDATE_INTERVAL = 2 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 10000; /* 2 sec */
    static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private SharedPreferencesHelper helper;
    private  Bundle bundle;
    public static MaterialDialog mDialog;
    private   FirebaseAuth mAuth;
    private AddInfoUploadHelper addInfoUploadHelper;
   // GoogleApiClient gac;
   MyPermissions permissions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_info);
        changeStatusBarColor();
        init();


        mSaveInfoBtn.setOnClickListener(view -> {

            if (loginType.equals(PHONE_TYPE)){
              if (selectedImage!=null){
                  showLoadingDialog();
                  uploadAndSaveData();

              }
              else {
                  if (permissions.checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)){
                      openFileChooser();
                  }
                  else {
                      permissions.requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
                  }
              }
          }
          else {
             uploadAndSaveFacebookGoogleData();
          }
        });
        userSelectedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedImage==null){
                    openFileChooser();
                }
            }
        });
    }

    private void init() {
         bundle= getIntent().getExtras();
        mAuth = FirebaseAuth.getInstance();
        loginType=bundle.getString(USER_LOGIN_TYPE,"");
        phoneNum = bundle.getString(USER_PHONE_NUMBER);
        uid = bundle.getString(USER_ID);
        devicetoken = bundle.getString(USER_TOKEN);
        Log.e(TAG, "init: loginType "+loginType );
        permissions=new MyPermissions(this);
        userSelectedImage = findViewById(R.id.user_selected_image);
        fullName = findViewById(R.id.fullName);
        phoneNumber = findViewById(R.id.phone_number);
        mUserEmail = findViewById(R.id.userEmail);
        mSaveInfoBtn = findViewById(R.id.addInfo);
        mUserAddress = findViewById(R.id.userAddress);
        helper =new SharedPreferencesHelper(AddInfoActivity.this);
        isGooglePlayServicesAvailable();
        addInfoUploadHelper=new AddInfoUploadHelper(AddInfoActivity.this);
        setupViews(loginType);
    }

    private void setupViews(String loginType) {
        Log.e(TAG, "setupViews: loginType "+loginType );
        switch (loginType){

            case FIREBASE_TYPE:
                setupFirebaseType();
                break;
                case GOOGLE_TYPE:
                    setupGoogleType();
                    break;
                case FACEBOOK_TYPE:
                    setupFacebookType();
                    break;
                case PHONE_TYPE:
                    setupPhoneType();
                    break;
        }
    }

    private void setupFirebaseType() {
    }
    private void setupGoogleType() {
        mUserEmail.setVisibility(View.VISIBLE);
        mUserAddress.setVisibility(View.VISIBLE);
        phoneNumber.setVisibility(View.VISIBLE);


    }
    private void setupFacebookType() {
        mUserEmail.setVisibility(View.VISIBLE);
        mUserAddress.setVisibility(View.VISIBLE);
        phoneNumber.setVisibility(View.VISIBLE);


    }
    private void setupPhoneType() {
        mUserEmail.setVisibility(View.VISIBLE);
        mUserAddress.setVisibility(View.VISIBLE);
        fullName.setVisibility(View.VISIBLE);
        userSelectedImage.setVisibility(View.VISIBLE);
        if (selectedImage!=null){
            mSaveInfoBtn.setText(getString(R.string.choise_image));
        }


    }

    private void uploadAndSaveData() {
        String _fullName=fullName.getText().toString();
        String _userEmail=mUserEmail.getText().toString();
        String _phoneNumber=loginType=bundle.getString(USER_PHONE_NUMBER,"");
        String _address=mUserAddress.getText().toString();

        if (_fullName.isEmpty()){
            fullName.setError(getString(R.string.empty_field_error_message));
        }
        else  if (_userEmail.isEmpty()){
            mUserEmail.setError(getString(R.string.empty_field_error_message));
        }
        else if (_address.isEmpty()){
            mUserAddress.setError(getString(R.string.empty_field_error_message));
        }
        else {
           // addInfoUploadHelper.uploadImageWithData(selectedImage,_fullName,_userEmail,_phoneNumber,_address);
            addInfoUploadHelper.uploadImageWithData(_fullName,_userEmail,_phoneNumber,_address);
        }

    }
    private void uploadAndSaveFacebookGoogleData() {
        String _userEmail=mUserEmail.getText().toString();
        String _phoneNumber=phoneNumber.getText().toString();
        String _address=mUserAddress.getText().toString();
        String _fullName  = bundle.getString(USER_LOGIN_NAME);

         if (_userEmail.isEmpty()){
            mUserEmail.setError(getString(R.string.empty_field_error_message));
        }
        else if (_address.isEmpty()){
            mUserAddress.setError(getString(R.string.empty_field_error_message));
        }
         else if (_phoneNumber.isEmpty()){
             phoneNumber.setError(getString(R.string.empty_field_error_message));
         }
        else {
            String userId=mAuth.getUid();
             Log.e(TAG, "uploadAndSaveFacebookGoogleData:userId "+userId );
            addInfoUploadHelper.saveAllData(userId,_phoneNumber,_fullName,_userEmail,_address,helper.getUserImage());
        }

    }
    public void changeStatusBarColor() {
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    @Override
    protected void onStart() {
        //gac.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        //gac.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddInfoActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

            return;
        }
        Log.d(TAG, "onConnected");

           }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(AddInfoActivity.this, "onConnectionFailed: \n" + connectionResult.toString(),
                Toast.LENGTH_LONG).show();
        Log.d("DDD", connectionResult.toString());
    }

    private boolean isGooglePlayServicesAvailable() {
        final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.d(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        Log.d(TAG, "This device is supported.");
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedImage = data.getData();
            userSelectedImage.setImageURI(null);
            userSelectedImage.setImageURI(selectedImage);
            mSaveInfoBtn.setText(getResources().getString(R.string.save));

        }

    }
    public void showLoadingDialog() {
        Log.e("showLoadingDialog","showLoadingDialog RV");
        mDialog = new MaterialDialog.Builder(AddInfoActivity.this)
                .title(R.string.app_name)
                .content(R.string.progress_wait)
                .progress(true, 0)
                .show();
    }
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, PICK_IMAGE_REQUEST);
    }

}