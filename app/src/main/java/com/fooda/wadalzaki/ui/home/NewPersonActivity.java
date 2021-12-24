package com.fooda.wadalzaki.ui.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;
import com.fooda.wadalzaki.R;
import com.fooda.wadalzaki.helpers.NewPersonUploadHelper;
import com.fooda.wadalzaki.utils.MyPermissions;

import static com.fooda.wadalzaki.utils.Constants.PICK_IMAGE_REQUEST;

public class NewPersonActivity extends AppCompatActivity {
    private ImageButton saveInfo, deleteAllFields, cancelInfo;
    private ImageView selectedImage;
    private EditText inputFullName, inputEmail, inputPhone, inputAddress;
    private Uri selectedImageUri;
    private MyPermissions permissions;
    public static MaterialDialog mDialog;
    private NewPersonUploadHelper helper;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_person_layout);
        permissions = new MyPermissions(this);
        helper=new NewPersonUploadHelper(this);
        saveInfo = findViewById(R.id.save_info);
        deleteAllFields = findViewById(R.id.delete_all_fields);
        cancelInfo = findViewById(R.id.cancel_info);
        selectedImage = findViewById(R.id.selected_image);
        inputFullName = findViewById(R.id.input_full_name);
        inputEmail = findViewById(R.id.input_email);
        inputPhone = findViewById(R.id.input_phone);
        inputAddress = findViewById(R.id.input_address);
        cancelInfo.setOnClickListener(v -> {
            finish();
        });
        selectedImage.setOnClickListener(view -> {
            if (permissions.checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                openFileChooser();
            } else {
                permissions.requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        });
        deleteAllFields.setOnClickListener(v -> {
            deleteUserAllFields();
        });
        saveInfo.setOnClickListener(v -> {
            if (selectedImageUri == null) {

                if (permissions.checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    openFileChooser();
                } else {
                    permissions.requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
                }
            }
            if (!isSomeFieldEmpty(inputFullName, inputEmail, inputPhone, inputAddress)) {
                if (selectedImageUri != null) {
                    String fullName=inputFullName.getText().toString().trim();
                    String email=inputEmail.getText().toString().trim();
                    String phoneNumber=inputPhone.getText().toString().trim();
                    String address=inputAddress.getText().toString().trim();
                    showMyLoadingDialog();
                helper.uploadNewUser(selectedImageUri,fullName,email,phoneNumber,address);
                }
                else {
                    if (permissions.checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        openFileChooser();
                    } else {
                        permissions.requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
                    }
                }
            }
        });

    }

    private boolean isSomeFieldEmpty(EditText inputFullName, EditText inputEmail, EditText inputPhone, EditText inputAddress) {
        if (inputFullName.getText().toString().isEmpty()) {
            inputFullName.setError(getString(R.string.empty_field_error_message));
            return true;
        }
        if (inputEmail.getText().toString().isEmpty()) {
            inputEmail.setError(getString(R.string.empty_field_error_message));
            return true;
        }
        if (inputPhone.getText().toString().isEmpty()) {
            inputPhone.setError(getString(R.string.empty_field_error_message));
            return true;
        }
        if (inputAddress.getText().toString().isEmpty()) {
            inputAddress.setError(getString(R.string.empty_field_error_message));
            return true;
        }
        return false;
    }

    private void deleteUserAllFields() {
        if (selectedImage != null) {
            selectedImage.setImageURI(null);
        }
        inputPhone.setText("");
        inputFullName.setText("");
        inputEmail.setText("");
        inputAddress.setText("");
    }

    public static void actionStart(Context context, Boolean flag) {
        Intent intent = new Intent(context, NewPersonActivity.class);
        intent.putExtra("flag", flag);
        context.startActivity(intent);
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            selectedImage.setImageURI(selectedImageUri);

        }

    }

    public void showMyLoadingDialog() {
        mDialog = new MaterialDialog.Builder(this)
                .title(R.string.app_name)
                .content(R.string.progress_wait)
                .progress(true, 0)
                .show();
    }

    public void dismissMyLoadingDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

}
