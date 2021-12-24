package com.fooda.wadalzaki.helpers;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.fooda.wadalzaki.model.User;
import com.fooda.wadalzaki.ui.auth.AddInfoActivity;
import com.fooda.wadalzaki.ui.home.HomeActivity;
import com.fooda.wadalzaki.ui.home.NewPersonActivity;
import com.fooda.wadalzaki.ui.login.LogInActivity;
import com.fooda.wadalzaki.ui.login.RegisterActivity;
import com.fooda.wadalzaki.utils.TimeUnitConverter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class NewPersonUploadHelper {
    private final Context context;
    private FirebaseFirestore db;

    private static final String TAG = "UploadDownloadHelper";
    private User savedUser;
    private SharedPreferencesHelper helper;
    public NewPersonUploadHelper(Context context) {
        this.context = context;
        db = FirebaseFirestore.getInstance();
        helper=new SharedPreferencesHelper(context);
    }

    public void uploadNewUser(Uri filePath, final String fullName, final String email, String phoneNumber, String address) {
        Log.e(TAG, "uploadImageWithData: filePath"+filePath.getPath() );
        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference("login");
        //if there is a file to upload
        if (filePath != null) {
          //  presenter.showMyLoadingDialog();
            final String imageName = System.currentTimeMillis()
                    + "." + getFileExtension(filePath);

            StorageReference riversRef = mStorageRef.child(imageName);
            Log.e(TAG, "uploadImageWithData: imageName"+imageName );

            Log.e(TAG, "uploadImageWithData: userId"+"userId" );
            riversRef.putFile(filePath)
                    .addOnSuccessListener(taskSnapshot -> {
                        Log.d(TAG, "onSuccess: ");
                       // user = new User("", "", "", "", "");

                        String imagePath =taskSnapshot.getUploadSessionUri().toString();
                        saveUserAllData(String.valueOf(System.currentTimeMillis()),phoneNumber,fullName,email,address,imageName,helper.getUserName());
                       // presenter.dismissMyLoadingDialog();
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //if the upload is not successfull
                            //hiding the progress dialog
                            Log.e("onFailure", exception.getMessage());
                            if (LogInActivity.mDialog!=null){
                                LogInActivity.mDialog.dismiss();
                            }
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

    public void saveUserAllData(String uid, String phoneNum, String name, String email, String finalAddress, String imagePath,String addedName) {
        Log.e(TAG, "saveAllData: uid:"+uid+"phoneNum +"+phoneNum+"\n "+"email: "+email+" \n finalAddress: "+finalAddress+" imagePath: "+imagePath );
        HashMap<String,Object> userData = new HashMap<>();
        userData.put("uid", uid);
        userData.put("phoneNumber", phoneNum);
        userData.put("name", name);
        userData.put("numberThree", "");
        userData.put("numberTwo", "");
        userData.put("email", email);
        userData.put("address", finalAddress);
        userData.put("imagePath", imagePath);

        db.collection("UserList").document(uid).set(userData).addOnSuccessListener(o -> {

            Intent intent = new Intent(context, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

            context.startActivity(intent);
            saveAddedPersonData(phoneNum,helper.getUserName(), TimeUnitConverter.getCurrentDate(),addedName);
            dismissDialog();
        }).addOnFailureListener(e -> {
            dismissDialog();
            Log.e(TAG, "OnFailureListener: "+e.toString());
            Toast.makeText(context, "Something went Wrong!", Toast.LENGTH_SHORT).show();
        });
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = context.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }



private void dismissDialog(){
        if (AddInfoActivity.mDialog!=null){
            AddInfoActivity.mDialog.dismiss();
        }
    if (RegisterActivity.loadingDialog!=null){
        RegisterActivity.loadingDialog.close();
    }
    if (NewPersonActivity.mDialog!=null){
        NewPersonActivity.mDialog.dismiss();
    }
}
    public void saveAddedPersonData(String phoneNum,String adderName,String date,String addedName) {
        HashMap<String,Object> userData = new HashMap<>();
        userData.put("phoneNumber", phoneNum);
        userData.put("adderName", adderName);
        userData.put("date", date);
        userData.put("addedName", addedName);
        //db.collection("Notebook");
        db.collection("AddedUserList").add(userData).addOnSuccessListener(o -> {

            Log.e(TAG, "addOnSuccessListener: ");

            dismissDialog();
        }).addOnFailureListener(e -> {
            dismissDialog();
            Log.e(TAG, "OnFailureListener: "+e.toString());
            Toast.makeText(context, "Something went Wrong!", Toast.LENGTH_SHORT).show();
        });
    }

}

