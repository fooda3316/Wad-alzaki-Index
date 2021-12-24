package com.fooda.wadalzaki.ui.login;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.fooda.wadalzaki.R;
import com.fooda.wadalzaki.helpers.DialogPresenter;
import com.fooda.wadalzaki.helpers.login.FirebaseHelper;
import com.fooda.wadalzaki.utils.MyPermissions;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.fooda.wadalzaki.utils.Constants.FIREBASE_TYPE;
import static com.fooda.wadalzaki.utils.Constants.PICK_IMAGE_REQUEST;


public class RegisterActivity extends AppCompatActivity  {

    private String confirmPassString,fullName,emailString,passString,phoneString,addressString;
    MyPermissions permissions;
   // private Uri selectedImage;
    EditText email_signup,pass_signup,confirm_pass_signup, full_name,phone_signup,address_signup;
    AppCompatButton ok_signup;
    ImageView imageView;
    private DialogPresenter dialogPresenter;
    private FirebaseHelper firebaseHelper;
    public static LoadingDialog loadingDialog ;
    private static final String TAG = "RegisterActivity";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        permissions=new MyPermissions(this);
        firebaseHelper=new FirebaseHelper(this);
        full_name =findViewById(R.id.input_full_signup);
        ok_signup=findViewById(R.id.btnFragRegister);
        email_signup=findViewById(R.id.input_email_signup);
        pass_signup=findViewById(R.id.input_pass_signup);
        phone_signup=findViewById(R.id.input_phone);
        address_signup=findViewById(R.id.input_address);
        confirm_pass_signup=findViewById(R.id.input_confirm_pass_signup);
        imageView=findViewById(R.id.selectedImage);
        dialogPresenter=new DialogPresenter(this);
//        if (selectedImage==null){
//            ok_signup.setText(getResources().getString(R.string.choise_image));
//        }
        ok_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailString = email_signup.getText().toString();
                passString = pass_signup.getText().toString();
                confirmPassString = confirm_pass_signup.getText().toString();
                phoneString=phone_signup.getText().toString();
                addressString=address_signup.getText().toString();
                fullName = full_name.getText().toString();
                 //  if (selectedImage!=null) {
                       if (phoneString.isEmpty()){
                           pass_signup.setError(getString(R.string.empty_field_error_message));
                           return;
                       }
                       if (addressString.isEmpty()){
                           address_signup.setError(getString(R.string.empty_field_error_message));
                           return;
                       }
                    //imageOriginalName= System.currentTimeMillis()+"";

                    if (validateEmail(emailString,email_signup)) {
                        if (validatePassword(passString,pass_signup)) {
                            if (passString.equals(confirmPassString)) {
                                //  imageOriginalName=System.currentTimeMillis()+"";
                                Log.d(TAG, "onClick: passString "+passString);
                                showMyLoadingDialog();
                                //   Log.d("loin info",firstname+" "+lastname+" "+emailString+" "+phone+" "+passString);
                                firebaseHelper.createFirebaseUser(  emailString, passString,fullName,FIREBASE_TYPE,phoneString,addressString);
                                //  mLoginPresenter.ok_signUp("firstname", "lastname", "emailString", "passString", "default","phone");


                            } else {
//                            Snackbar snackbar = Snackbar
//                                    .make(view.findViewById(android.R.id.content),
//                                            R.string.passwords_check, Snackbar.LENGTH_LONG);
//                            snackbar.show();
                                dialogPresenter.showAlert(getResources().getString(R.string.passwords_check));
                                pass_signup.setError(getResources().getString(R.string.passwords_check));
                            }
                        }
                    }

              /*  }
                else {
                    ok_signup.setText(getResources().getString(R.string.choise_image));
                    if (permissions.checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)){
                        openFileChooser();
                    }
                    else {
                        permissions.requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
                    }
                }*/
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (permissions.checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)){
                    openFileChooser();
                }
                else {
                    permissions.requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
                }

            }
        });

    }



    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, PICK_IMAGE_REQUEST);
        //pickerHelper.pickImage();
    }

    public boolean validatePassword(String passString, TextView textView) {
        if (passString.length() >= 6) {
            Pattern pattern;
            Matcher matcher;
            final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{4,}$";
            pattern = Pattern.compile(PASSWORD_PATTERN);
            matcher = pattern.matcher(passString);

            if (matcher.matches()) {
                return true;
            } else {
                dialogPresenter.showAlert(getResources().getString(R.string.invalid_password));

                textView.setError(getResources().getString(R.string.invalid_password));
                return false;
            }
        } else {
            textView.setError(getResources().getString(R.string.password_length));

            dialogPresenter.showAlert(getResources().getString(R.string.password_length));

            return false;
        }
    }
    public boolean validateEmail(String email, TextView textView) {
        Matcher matcher = Patterns.EMAIL_ADDRESS.matcher(email);
        if (!email.equals("") && matcher.matches()) {
            return true;
        } else {
            dialogPresenter.showAlert(getResources().getString(R.string.invalid_email));
            textView.setError(getResources().getString(R.string.invalid_email));

            return false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
           // selectedImage = data.getData();
           // imageView.setImageURI(null);
           // imageView.setImageURI(selectedImage);
            ok_signup.setText(getResources().getString(R.string.signup));

        }
    }
    private void showMyLoadingDialog(){
        loadingDialog= new LoadingDialog(this);
        loadingDialog.setLoadingText(getResources().getString(R.string.progress_wait));
        loadingDialog.show();
    }
    private void dismissMyLoadingDialog(){
        loadingDialog.close();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}