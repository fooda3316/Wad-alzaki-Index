package com.fooda.wadalzaki.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fooda.wadalzaki.R;
import com.fooda.wadalzaki.helpers.SharedPreferencesHelper;
import com.fooda.wadalzaki.themes.SetThemes;
import com.fooda.wadalzaki.ui.login.LogInActivity;
//import com.fooda.wadalzaki.ui.auth.AddInfoActivity;
///import com.fooda.wadalzaki.ui.auth.PhoneLoginActivity;

import static com.fooda.wadalzaki.utils.Constants.MY_STYLE;
import static com.fooda.wadalzaki.utils.Constants.PHONE_TYPE;
import static com.fooda.wadalzaki.utils.Constants.SETTINGS_PREFERENCES;
import static com.fooda.wadalzaki.utils.Constants.USER_ID;
import static com.fooda.wadalzaki.utils.Constants.USER_LOGIN_TYPE;
import static com.fooda.wadalzaki.utils.Constants.USER_PHONE_NUMBER;
import static com.fooda.wadalzaki.utils.Constants.USER_TOKEN;


public class SplashActivity extends AppCompatActivity {
private SharedPreferencesHelper preferencesHelper;
    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferencesHelper=new SharedPreferencesHelper(this);
            SetThemes.setThemes(this);

        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(() -> {
            if (!isFirstLunch()){
                createShortCut();
                setInitialStyle();
                startActivity(new Intent(SplashActivity.this, LanguageActivity.class));
            }
            else {
                if (preferencesHelper.isUserLogged()){
                    //goToAddInfo();
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));

                }
else {
                    startActivity(new Intent(SplashActivity.this, LogInActivity.class));
                 //   goToAddInfo();

                }
            }
            finish();
        }, 1500);
    }



    public void createShortCut(){
        Intent shortCutIntent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        shortCutIntent.putExtra("duplicate", false);
        shortCutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.app_name));
        Parcelable icon = Intent.ShortcutIconResource.fromContext(getApplicationContext(), R.mipmap.ic_launcher);
        shortCutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
        shortCutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(getApplicationContext(), SplashActivity.class));
        sendBroadcast(shortCutIntent);
        Toast.makeText(this, "تم انشاء اختصار للتطبيق", Toast.LENGTH_SHORT).show();
        confirmSigning(true);
    }
    private Boolean isFirstLunch(){
        SharedPreferences sharedPreferences=getSharedPreferences("FirstLunch", Context.MODE_PRIVATE);
        return  sharedPreferences.getBoolean("isFirstLunch",false);
    }
    private void confirmSigning(Boolean confirm){
        SharedPreferences sharedPreferences=getSharedPreferences("FirstLunch",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("isFirstLunch",confirm);
        editor.apply();

    }
    public void setInitialStyle(){
        SharedPreferences    shard = getSharedPreferences(SETTINGS_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shard.edit();
        editor.putInt(MY_STYLE,6);
        editor.apply();

    }
}
