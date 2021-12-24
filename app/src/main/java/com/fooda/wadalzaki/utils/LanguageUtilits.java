package com.fooda.wadalzaki.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.util.DisplayMetrics;


import com.fooda.wadalzaki.ui.home.UserSettingActivity;

import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;
import static com.fooda.wadalzaki.utils.Constants.LANG_KEY;

public class LanguageUtilits {
    private Context context;
    Activity activity;

    public LanguageUtilits(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    @SuppressWarnings("deprecation")
    public void setLocale(String lang) {

        Locale myLocale = new Locale(lang);

        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        Configuration conf = context.getResources().getConfiguration();
        conf.locale = myLocale;
        context.getResources().updateConfiguration(conf, dm);
        saveLang(lang);
        if (context instanceof UserSettingActivity){
            activity.recreate();
        }

    }
    private void saveLang(String lang) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("lang", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LANG_KEY, lang);
        editor.apply();
    }
}
