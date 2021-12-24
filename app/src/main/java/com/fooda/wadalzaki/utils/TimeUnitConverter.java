package com.fooda.wadalzaki.utils;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeUnitConverter {
    private static final String TAG = "TimeUnitConverter";
    public static String millisecondsToTime(long milliseconds) {
        long hours = (milliseconds / 1000) / 60/60;
        long minutes = (milliseconds / 1000) / 60;
        long seconds = (milliseconds / 1000) % 60;
        String hoursStr = Long.toString(hours);
        String minutesStr = Long.toString(minutes);
        String secondsStr = Long.toString(seconds);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis((int) milliseconds);
        //return formatter.format(calendar.getTime());
        return formatter.format(new Date(milliseconds));
        //return String.format(finalUnitShape(hoursStr) + ":" +finalUnitShape(minutesStr) + ":" + finalUnitShape(secondsStr));
    }
    private static String finalUnitShape(String unit){
        String finalUnit="";
        if (unit.length() >= 2) {
            finalUnit = unit.substring(0, 2);
        } else {
            finalUnit = "0" + unit;
        }
return finalUnit;
    }
    @SuppressLint("DefaultLocale")
    public static String getCurrentDate() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateformate = new SimpleDateFormat("MMM dd, yyyy");
        Calendar currentDate = Calendar.getInstance();
        return dateformate.format(currentDate.getTime());


           }
}
