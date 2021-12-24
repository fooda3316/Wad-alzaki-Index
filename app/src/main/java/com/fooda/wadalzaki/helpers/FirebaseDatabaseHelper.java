package com.fooda.wadalzaki.helpers;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.fooda.wadalzaki.model.UserInfo;
import com.fooda.wadalzaki.ui.auth.AddInfoActivity;
import com.fooda.wadalzaki.ui.home.HomeActivity;
import com.fooda.wadalzaki.ui.login.LogInActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.fooda.wadalzaki.utils.Constants.USER_ID;
import static com.fooda.wadalzaki.utils.Constants.USER_LOGIN_NAME;
import static com.fooda.wadalzaki.utils.Constants.USER_LOGIN_TYPE;
import static com.fooda.wadalzaki.utils.Constants.USER_TOKEN;

public class FirebaseDatabaseHelper {
    private final Context context;
    private FirebaseFirestore db;
    //private DialogPresenter presenter;
    private static final String TAG = "UploadDownloadHelper";
    private String deviceToken;
    private final SharedPreferencesHelper helper;
    private DocumentReference docRef;
    private CollectionReference notebookRef;
    private FirebaseInstallations firebaseInstallations;
    public FirebaseDatabaseHelper(Context context) {
        this.context = context;
        //presenter = new DialogPresenter(context);
        db = FirebaseFirestore.getInstance();
         notebookRef = db.collection("UserList");
        helper=new SharedPreferencesHelper(context);
    }

    public void finishLoginUser(String fullName,String imageUri, String loginType) {
        Log.e(TAG, "finishLoginUser: loginType "+loginType );
        FirebaseAuth auth=FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String uid = Objects.requireNonNull(user).getUid();
        docRef = db.collection("UserList").document(uid);
        FirebaseInstallations.getInstance().getToken(true).addOnCompleteListener(task1 -> {
            Log.e(TAG, "signInWithPhoneAuthCredential: " );
            if (task1.isSuccessful()){
                deviceToken = task1.getResult().getToken();
                Log.e(TAG, "signInWithPhoneAuthCredential: deviceToken "+deviceToken );
            }
        });


        docRef.get().addOnSuccessListener(documentSnapshot -> {
            ///helper.saveIsUserLogin(true);
            helper.saveUserLoginInfo(fullName,imageUri,loginType);
            if (documentSnapshot.exists()) {
              /*  notebookRef.whereGreaterThanOrEqualTo("uid", uid)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                    UserInfo mUserInfo = documentSnapshot.toObject(UserInfo.class);
                                    Log.e(TAG, "onSuccess documentSnapshot: "+documentSnapshot );
                                    Log.e(TAG, "onSuccess mUserInfo: "+mUserInfo );

                                }
                            }
                        });*/
                helper.saveIsUserLogin(true);
                sendUserToMain();
            }else {
                Intent intent = new Intent(context, AddInfoActivity.class);
                intent.putExtra(USER_ID, uid);
                intent.putExtra(USER_LOGIN_TYPE, loginType);
                Log.e(TAG, "finish send dataLoginUser: loginType "+loginType );
                intent.putExtra(USER_TOKEN, deviceToken);
                intent.putExtra(USER_LOGIN_NAME, fullName);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
            }

        });


    }
    private void sendUserToMain() {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }
    public void getAndSaveValues(String idQuery) {
        Log.e(TAG, "getAndSaveValues: emailQuery "+idQuery );
        notebookRef.whereEqualTo("uid", idQuery).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<UserInfo> list=new ArrayList<>();
                        if (!queryDocumentSnapshots.isEmpty()){
                            Log.e(TAG, "queryDocumentSnapshots is not empty: " );
                            for (DocumentSnapshot snapshot:queryDocumentSnapshots)
                                list.add(snapshot.toObject(UserInfo.class));

                        }
                        else {
                            Log.e(TAG, "queryDocumentSnapshots is  empty!!!: " );

                        }
                        for (UserInfo info:list) {
                            Log.e(TAG, "UserInfo email: "+info.getEmail() );

                        }
                        Log.e(TAG, "addOnSuccessListener: " );
                        //queryDocumentSnapshots.getDocuments()
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Log.e(TAG, "QueryDocumentSnapshot: " );
                            UserInfo mUserInfo = documentSnapshot.toObject(UserInfo.class);
                            Log.e(TAG, "onSuccess documentSnapshot: "+documentSnapshot );
                            Log.e(TAG, "onSuccess mUserInfo: "+mUserInfo );
                            Log.e(TAG, "onSuccess Name: "+mUserInfo.getName() );
                            Log.e(TAG, "onSuccess Email: "+mUserInfo.getEmail() );
                            Log.e(TAG, "onSuccess Address: "+mUserInfo.getAddress() );
                            helper.saveIsUserLogin(true);
                            helper.saveUserLoginInfo(mUserInfo.getName(),mUserInfo.getImagePath(),"fr");
                            dismissLogInActivityDialog();
                            sendUserToMain();

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Log.e(TAG, "getAndSaveValues onFailure: "+e.getMessage(), e);
            }
        });


    }

    private void dismissLogInActivityDialog() {
        if (LogInActivity.mDialog!=null){
            LogInActivity.mDialog.dismiss();
        }
    }

}

