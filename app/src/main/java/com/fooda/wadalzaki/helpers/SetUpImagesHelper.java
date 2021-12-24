package com.fooda.wadalzaki.helpers;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.fooda.wadalzaki.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;

public class SetUpImagesHelper {
    private SharedPreferencesHelper preferencesHelper;
    private Context context;

    public SetUpImagesHelper(Context context) {
        this.context = context;
        preferencesHelper = new SharedPreferencesHelper(context);
    }

    private static final String TAG = "SetUpImagesHelper";

    public void setupImage(ImageView imageView, ProgressBar progressBar, String path) {
        Log.e(TAG, "setupImage: path " + path);
       // Log.e(TAG, "setupImage: LoginType " + preferencesHelper.getUserLoginType());

        StorageReference storageReference =
                FirebaseStorage.getInstance().getReference("login");

        if (path != null) {
           // if (preferencesHelper.getUserLoginType().equals("fr")) {


                storageReference.child(path).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        ///Log.e(TAG, "onSuccess:uri " + uri.toString());
                        setupImageWithProgressBar(imageView, progressBar, uri.toString());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Log.e(TAG, "onFailure: ", e);
                    }
                });
          //  }
            //else {
//                setupImageWithProgressBar(imageView, progressBar, path);
//            }
        }
    }

    private void setupImageWithProgressBar(ImageView imageView, ProgressBar progressBar, String path) {

        Picasso.get().load(path).error(R.drawable.no_image).placeholder(R.drawable.app_logo).into(imageView, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onError(Exception e) {
                Log.e("Error : ", e.toString());
                progressBar.setVisibility(View.GONE);
                imageView.setImageResource(R.drawable.ic_person1);
            }
        });
    }

    public void initiateUserData(CircleImageView imageView, TextView textView) {
        ///  new FirebaseDatabaseHelper(this).getAndSaveValues("ak1169194@gmail.com");
        String path = preferencesHelper.getUserImage();
        String fullName = preferencesHelper.getUserName();

        Log.e(TAG, "initiateUserData: fullName: " + fullName + " \n uri: " + path);
        if (!path.equals("") && !fullName.equals("")) {
            textView.setText(fullName);
           // if (preferencesHelper.getUserLoginType().equals("fr")) {
                StorageReference storageReference =
                    FirebaseStorage.getInstance().getReference("login");


            storageReference.child(path).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Log.e(TAG, "onSuccess:uri " + uri.toString());
                    setupImageWithOutProgressBar(imageView,uri.toString());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    Log.e(TAG, "onFailure: ", e);
                }
            });
//            }else {
//                setupImageWithOutProgressBar(imageView,path);
//            }

        }


    }

    private void setupImageWithOutProgressBar(CircleImageView imageView,String uri) {
        Picasso.get().load(uri.toString()).error(R.drawable.app_logo).into(imageView, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
                Log.e(TAG, "onSuccess:... ");

            }

            @Override
            public void onError(Exception e) {
                Log.e("Error : ", e.toString());
                imageView.setImageResource(R.drawable.ic_person1);
            }
        });
    }

}
