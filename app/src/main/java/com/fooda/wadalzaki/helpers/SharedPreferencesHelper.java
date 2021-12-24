package com.fooda.wadalzaki.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.fooda.wadalzaki.utils.Constants.IS_AUDIOS_SAVED_KEY;
import static com.fooda.wadalzaki.utils.Constants.IS_DOWNLOAD_START;
import static com.fooda.wadalzaki.utils.Constants.IS_USER_LOGIN;
import static com.fooda.wadalzaki.utils.Constants.LIST_SAVED_KEY;
import static com.fooda.wadalzaki.utils.Constants.LOGIN_KEY;
import static com.fooda.wadalzaki.utils.Constants.USER_INFO_ADDRESS;
import static com.fooda.wadalzaki.utils.Constants.USER_INFO_EMAIL;
import static com.fooda.wadalzaki.utils.Constants.USER_INFO_MAIN_PHONE_NUMBER;
import static com.fooda.wadalzaki.utils.Constants.USER_INFO_SECOND_PHONE_NUMBER;
import static com.fooda.wadalzaki.utils.Constants.USER_INFO_THIRD_PHONE_NUMBER;
import static com.fooda.wadalzaki.utils.Constants.USER_INFO_USER_ID;
import static com.fooda.wadalzaki.utils.Constants.USER_LOGIN_IMAGE;
import static com.fooda.wadalzaki.utils.Constants.USER_LOGIN_NAME;
import static com.fooda.wadalzaki.utils.Constants.USER_LOGIN_TYPE;
import static com.fooda.wadalzaki.utils.Constants.USER_PHONE_NUMBER;


public class SharedPreferencesHelper {
    private Context context;
    private HashMap<String,String> fileData=new HashMap<>();
    private static final String TAG = "SharedPreferencesHelper";
    private SharedPreferences isDownloadBeganSH, isSavedPreferences,savedListPreferences,loginPreferences;

    public SharedPreferencesHelper(Context context) {
        this.context = context;
        isDownloadBeganSH=context.getSharedPreferences(IS_DOWNLOAD_START, Context.MODE_PRIVATE);
        isSavedPreferences=context.getSharedPreferences(IS_AUDIOS_SAVED_KEY, Context.MODE_PRIVATE);
        savedListPreferences=context.getSharedPreferences(LIST_SAVED_KEY, Context.MODE_PRIVATE);
        loginPreferences=context.getSharedPreferences(LOGIN_KEY, Context.MODE_PRIVATE);



    }

    public void downloadBegan(String downloadFileName,Boolean isBegan) {
        Log.d(TAG, "downloadBegan: "+downloadFileName+" "+isBegan);
        SharedPreferences.Editor editor= isDownloadBeganSH.edit();
        editor.putBoolean(downloadFileName,isBegan);
        editor.apply();
    }
    public void saveIsUserLogin(Boolean isUserLogin) {
        SharedPreferences.Editor editor= loginPreferences.edit();
        editor.putBoolean(IS_USER_LOGIN,isUserLogin);
        editor.apply();
    }
    public void saveUserLoginInfo(String name,String imageUri,String loginType) {
        SharedPreferences.Editor editor= loginPreferences.edit();
        editor.putString(USER_LOGIN_NAME,name);
        editor.putString(USER_LOGIN_IMAGE,imageUri);
        editor.putString(USER_LOGIN_TYPE,loginType);

        editor.apply();
    }
    public void saveMoreUserInfo(String email,String imageUri,String address,String mainPhoneNumber) {
        SharedPreferences.Editor editor= loginPreferences.edit();
        editor.putString(USER_INFO_EMAIL,email);
        editor.putString(USER_INFO_ADDRESS,address);
        editor.putString(USER_INFO_MAIN_PHONE_NUMBER,mainPhoneNumber);
        editor.putString(USER_LOGIN_IMAGE,imageUri);


        editor.apply();
    }
    public void saveLoginType(String loginType) {
        SharedPreferences.Editor editor= loginPreferences.edit();
        editor.putString(USER_LOGIN_TYPE,loginType);
        editor.apply();
    }
    public void saveUserId(String userId) {
        Log.e(TAG, "saveUserId in pre: userId "+userId );
        SharedPreferences.Editor editor= loginPreferences.edit();
        editor.putString(USER_INFO_USER_ID,userId);
        editor.apply();
    }
    public void saveUserImage(String image) {
        SharedPreferences.Editor editor= loginPreferences.edit();
        editor.putString(USER_LOGIN_IMAGE,image);
        editor.apply();
    }
    public String getUserId() {
        return loginPreferences.getString(USER_INFO_USER_ID,"");

    }
    public boolean isUserLogged() {
        return loginPreferences.getBoolean(IS_USER_LOGIN,false);

    }
    public String getUserName() {
        return loginPreferences.getString(USER_LOGIN_NAME,"");

    }
    public String getUserImage() {
        return loginPreferences.getString(USER_LOGIN_IMAGE,"");

    }
    public String getUserLoginType() {
        return loginPreferences.getString(USER_LOGIN_TYPE,"");

    }
    public boolean isDownloadBegan(String fileName) {
        Log.d(TAG, "isDownloadBegan: "+fileName);
        return isDownloadBeganSH.getBoolean(fileName,false);

    }
    public boolean isFileSaved(String fileName) {
        Log.d(TAG, "isFileSaved: "+fileName);
        return isSavedPreferences.getBoolean(fileName,false);
    }

    public String getUserEmail() {
        return loginPreferences.getString(USER_INFO_EMAIL,"");
    }
}
