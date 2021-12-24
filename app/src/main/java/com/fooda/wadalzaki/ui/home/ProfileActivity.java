package com.fooda.wadalzaki.ui.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.fooda.wadalzaki.R;
import com.fooda.wadalzaki.helpers.ProfileHelper;
import com.fooda.wadalzaki.helpers.SharedPreferencesHelper;
import com.fooda.wadalzaki.model.UserInfo;
import com.fooda.wadalzaki.utils.MyPermissions;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.fooda.wadalzaki.utils.Constants.PICK_IMAGE_REQUEST;

public class ProfileActivity extends AppCompatActivity {
    private ProfileHelper profileHelper;
    private SharedPreferencesHelper preferencesHelper;
    private TextView mainNumber,secondNumber, userAddress,userName,userEmail,thirdNumber;
    private ImageView editMainNumber,addSecondNumber,addThirdNumber,
            editSecondNumber,editThirdNumber,editEmail,editAddress;
    private CircleImageView userImage;
    private UserInfo userInfo;
    public static MaterialDialog mDialog;
    private ProgressBar progressBar;
    private static final String TAG = "ProfileActivity";
    private MyPermissions permissions;
    private Uri selectedImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        profileHelper=new ProfileHelper(this,this);
        preferencesHelper=new SharedPreferencesHelper(this);
        permissions=new MyPermissions(this);
        editMainNumber = findViewById(R.id.img_edit_main_number);
        secondNumber = findViewById(R.id.second_number);
        thirdNumber = findViewById(R.id.third_number);
        addSecondNumber = findViewById(R.id.add_second_number);
        addThirdNumber = findViewById(R.id.add_third_number);
        userEmail = findViewById(R.id.user_email);
        userImage = findViewById(R.id.user_image);
        progressBar = findViewById(R.id.progress_bar);
        editSecondNumber = findViewById(R.id.edit_second_number);
        editThirdNumber = findViewById(R.id.edit_third_number);
        editEmail = findViewById(R.id.img_edit_email);
        editAddress = findViewById(R.id.img_edit_address);
        userAddress = findViewById(R.id.address);
        userName = findViewById(R.id.user_name);
        mainNumber = findViewById(R.id.main_number);
        profileHelper.showNumbers(secondNumber,thirdNumber,addSecondNumber,addThirdNumber,editSecondNumber,editThirdNumber);
        findViewById(R.id.finish_page).setOnClickListener(v->{
            finish();
        });
        userImage.setOnClickListener(view -> {
            showUpdateImageDialog();
        });
        addSecondNumber.setOnClickListener(view ->
                openAddSecondNumberDialog("numberTwo","إضافة الرقم الثاني "));
        addThirdNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddSecondNumberDialog("numberThree","إضافة الرقم الثالث ");
            }
        });
        editSecondNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddSecondNumberDialog("numberTwo","تعديل الرقم الثاني ");
            }
        });
        editThirdNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddSecondNumberDialog("numberThree","تعديل الرقم الثالث ");
            }
        });
        editEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddSecondNumberDialog("email","تعديل الايميل ");
            }
        });
        editAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddSecondNumberDialog("address","تعديل العنوان ");
            }
        });
        //mainNumber.setText(remoteUser.getPhoneNumber());
        profileHelper.setupUserInfo(preferencesHelper.getUserId(),userImage,progressBar,userName,mainNumber,userEmail,userAddress);
        /*making personal info visible*/
        editMainNumber.setOnClickListener(v->{
            Toast.makeText(this, "نأسف هذة الميزة غير متوفرة الآن", Toast.LENGTH_SHORT).show();
        });


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedImage = data.getData();
            showMyLoadingDialog();
            profileHelper.updateUserImage(selectedImage);

        }

    }
    private void showUpdateImageDialog() {
        new MaterialDialog.Builder(this)
                .content( "هل ترغب فعلاً في تغير الصورة الخاصة بك؟")
                .positiveText(getResources().getString(R.string.yes))
                .negativeText(getResources().getString(R.string.no))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        if (permissions.checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)){
                            openFileChooser();
                        }
                        else {
                            permissions.requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
                        }

                    }
                }).onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
            }
        })
                .build().show();
    }


    private void openAddSecondNumberDialog(String filedName,String filedTitle) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(this);
        View mView = layoutInflaterAndroid.inflate(R.layout.user_input_dialog_box, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(this);
        alertDialogBuilderUserInput.setView(mView);

        final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.userInputDialog);
        final TextView titleView = (TextView) mView.findViewById(R.id.dialogTitle);
        titleView.setText(filedTitle);
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                       String userInput= userInputDialogEditText.getText().toString();
                        Log.e(TAG, "onClick: userInput "+userInput );
                        if (userInput.isEmpty()){
                            Toast.makeText(ProfileActivity.this, "عفواً لم يتم إدخال اي قيمة", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            conformEditing(userInput,filedName,filedTitle);
                        }
                    }
                })

                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();

    }

    private void conformEditing(String userInput,String filedName,String filedTitle) {
        new MaterialDialog.Builder(this)
                .title(filedTitle)
                .content(" القيمة الجديد هي: "+userInput+"\n هل ترغب في المتابعة ")
                .positiveText(getResources().getString(R.string.yes))
                .negativeText(getResources().getString(R.string.no))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        showMyLoadingDialog();
                        profileHelper.updateInfo(preferencesHelper.getUserId(),filedName,userInput);
                    }
                }).onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
            }
        })
                .build().show();

    }
    public void showMyLoadingDialog() {
        mDialog = new MaterialDialog.Builder(this)
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
