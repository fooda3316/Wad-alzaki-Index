package com.fooda.wadalzaki.helpers;

import android.app.Activity;
import android.content.ContentResolver;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.fooda.wadalzaki.model.UserInfo;
import com.fooda.wadalzaki.ui.home.ProfileActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import android.content.Context;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileHelper {
    private Context context;
    private FirebaseFirestore db;
    private DocumentReference userRef ;
    private UserInfo userInfo;
    private SharedPreferencesHelper preferencesHelper;
    private Activity activity;
    private static final String TAG = "ProfileHelper";
    public ProfileHelper(Context context, Activity activity) {
        this.context = context;
        this.activity=activity;
        db = FirebaseFirestore.getInstance();
        preferencesHelper=new SharedPreferencesHelper(context);
    }

    public void setupUserInfo(String userId, CircleImageView imageView, ProgressBar progressBar,TextView userName, TextView userPhone,TextView userEmail,TextView address){
        Log.e(TAG, "getUser: userId"+userId );
        userInfo =new UserInfo();
        if (userId!=null&&!userId.equals("")){
        userRef= db.collection("UserList").document(userId);
        userRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            Log.e(TAG, "onSuccess: User is exist..." );
                            userInfo =documentSnapshot.toObject(UserInfo.class);
                            Log.e(TAG, "onSuccess: Name "+ userInfo.getName() );
                            Log.e(TAG, "onSuccess: Email "+ userInfo.getEmail() );
                            Log.e(TAG, "onSuccess: PhoneNumber "+ userInfo.getPhoneNumber() );
                            Log.e(TAG, "onSuccess: magePath "+ userInfo.getImagePath() );
                            SetUpImagesHelper setup= new SetUpImagesHelper(context);
                            setup.setupImage(imageView,progressBar, userInfo.getImagePath());
                            userPhone.setText(userInfo.getPhoneNumber());
                            userEmail.setText(userInfo.getEmail());
                            userName.setText(userInfo.getName());
                            address.setText(userInfo.getAddress());

                        } else {
                            Log.e(TAG, "onSuccess: User not present!!!" );
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, e.toString());
                        progressBar.setVisibility(View.GONE);
                    }
                });}

    }
    public void updateInfo(String userId, String field, String newValue){
        if (userId!=null&&!userId.equals("")){
            userRef= db.collection("UserList").document(userId);
            userRef.update(field, newValue).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Log.e(TAG, "onSuccess: " );
                    Toast.makeText(context, "تمت العملية بنجاح...", Toast.LENGTH_SHORT).show();
                    dismissDialog();
                    activity.recreate();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    Log.e(TAG, "onFailure: "+e.toString() );
                    Toast.makeText(context, "عفواً لم تتم العملية بنجاح!!!", Toast.LENGTH_SHORT).show();

                    dismissDialog();
                }
            });

        }
    }

    private void dismissDialog() {
        if (ProfileActivity.mDialog!=null){
            ProfileActivity.mDialog.dismiss();
        }
    }

    public void showNumbers(TextView secondNumber, TextView thirdNumber, ImageView addSecondNumber, ImageView addThirdNumber, ImageView editSecondNumber, ImageView editThirdNumber) {
       String userId=preferencesHelper.getUserId();
        //String userId="8A0l2BYdoCZy6AK5Q3JwstDpivm2";
        Log.e(TAG, "showNumbers: "+userId );
        if (userId!=null&&!userId.equals("")) {
            userRef = db.collection("UserList").document(userId);
            userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    UserInfo user=documentSnapshot.toObject(UserInfo.class);
                    Log.e(TAG, "onSuccess: user equals "+user.getUid() );
                    Log.e(TAG, "onSuccess: user getNumberThree "+user.getNumberThree() );
                    Log.e(TAG, "onSuccess: user getName equals "+user.getName() );
                    Log.e(TAG, "onSuccess: user getPhoneNumber equals "+user.getPhoneNumber() );
                    Log.e(TAG, "onSuccess: user getEmail equals "+user.getEmail() );
                    Log.e(TAG, "onSuccess: user getAddress equals "+user.getAddress() );
                    Log.e(TAG, "onSuccess: user getImagePath equals "+user.getImagePath() );
                   // assert user != null;
                    if (user==null){
                        return;
                    }
                    Log.e(TAG, "onSuccess: getNumberTwo()"+user.getNumberTwo() );
                    if (!user.getNumberTwo().equals("")){
                      secondNumber.setVisibility(View.VISIBLE);
                      addSecondNumber.setVisibility(View.GONE);
                      editSecondNumber.setVisibility(View.VISIBLE);
                      secondNumber.setText(user.getNumberTwo());
                }
                    if (!user.getNumberThree().equals("")){
                        thirdNumber.setVisibility(View.VISIBLE);
                        addThirdNumber.setVisibility(View.GONE);
                        editThirdNumber.setVisibility(View.VISIBLE);
                        thirdNumber.setText(user.getNumberThree());
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    Log.e(TAG, "onFailure: ",e );
                }
            });
        }
    }
    public void updateUserImage(Uri filePath) {
        //Log.e(TAG, "uploadImageWithData: filePath"+filePath.getPath() );
        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference("login");
        //if there is a file to upload
        if (filePath != null) {
            //  presenter.showMyLoadingDialog();
            final String imageName = System.currentTimeMillis()
                    + "." + getFileExtension(filePath);

            StorageReference riversRef = mStorageRef.child(imageName);
            Log.e(TAG, "uploadImageWithData: imageName"+imageName );
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            String userId=mAuth.getUid();
            Log.e(TAG, "uploadImageWithData: userId"+userId );
            riversRef.putFile(filePath)
                    .addOnSuccessListener(taskSnapshot -> {
                        Log.d(TAG, "onSuccess: ");
                        updateInfo(preferencesHelper.getUserId(),"imagePath",imageName);
                        preferencesHelper.saveLoginType("fr");
                        preferencesHelper.saveUserImage(imageName);
                       // dismissDialog();

                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //if the upload is not successfull
                            //hiding the progress dialog
                            Log.e("onFailure", exception.getMessage());
                            //presenter.dismissMyLoadingDialog();
                            dismissDialog();
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



    private String getFileExtension(Uri uri) {
        ContentResolver cR = context.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


}
