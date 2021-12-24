package com.fooda.wadalzaki.helpers;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.fooda.wadalzaki.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

public class UploadDownloadHelper {
    private final Context context;
    private DatabaseReference mDatabaseRef;
    //private DialogPresenter presenter;
    private static final String TAG = "UploadDownloadHelper";
    private User savedUser;
    private final SharedPreferencesHelper helper;
    public UploadDownloadHelper(Context context) {
        this.context = context;
        //presenter = new DialogPresenter(context);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
        helper=new SharedPreferencesHelper(context);
    }

    public void uploadImage(Uri filePath, final String fullName, final String email) {

        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference("login");



        //if there is a file to upload
        if (filePath != null) {
          //  presenter.showMyLoadingDialog();
            final String imageName = System.currentTimeMillis()
                    + "." + getFileExtension(filePath);

            StorageReference riversRef = mStorageRef.child(imageName);

            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Log.d(TAG, "onSuccess: ");
                           // user = new User("", "", "", "", "");
                            User upload = new User(fullName, email,"", taskSnapshot.getUploadSessionUri().toString(),"fr");
                            String uploadId = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(uploadId).setValue(upload);
                           // presenter.dismissMyLoadingDialog();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //if the upload is not successfull
                            //hiding the progress dialog
                            Log.e("onFailure", exception.getMessage());
                            //presenter.dismissMyLoadingDialog();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NotNull UploadTask.TaskSnapshot taskSnapshot) {
                        }
                    });
        }
        //if there is not any file
        else {
            Toast.makeText(context, "لم يتم اختيار اي صورة !!!", Toast.LENGTH_LONG).show();

        }
    }
    public void saveUserInfo( String fullName,String email,String pass,String imageUri,String loginType) {/* needs LoginType*/
        //if there is a file to upload
            //presenter.showMyLoadingDialog();

        User upload = new User(fullName, email,pass,imageUri,loginType);
        String uploadId = mDatabaseRef.push().getKey();
        mDatabaseRef.child(uploadId).setValue(upload).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
               // presenter.dismissMyLoadingDialog();
                //helper.saveIsUserLogin(true);
                helper.saveUserLoginInfo(fullName,imageUri,loginType);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("onFailure", e.getMessage());
               // presenter.dismissMyLoadingDialog();
            }
        });


    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = context.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }




}

