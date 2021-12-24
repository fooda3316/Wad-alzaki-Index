package com.fooda.wadalzaki.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;


import com.fooda.wadalzaki.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public  class SharingConnection extends Intent {
    public Context context;
    private static final String WHATS_APP="https://chat.whatsapp.com/DI9Nm2byU6p8CubSdqihO2?fbclid=IwAR3Tic-UG3q7vRQEXUaY52t1SoyyGWYxVX7V5CPDrx7rVH4oiQGIO2-wYSw";

    private static final String FACEBOOK="https://web.facebook.com/groups/nourdamas";
    private static final String WEBSITE = "https://github.com/fooda3316/";
    public SharingConnection(Context context) {
        this.context = context;
    }


    public void MoreApp() {
        String developerName="Ahmed khalifa Gmah Muhmmed Edrees";
        //String developerName = "Ahmed_khalifa";
//        try {
//            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q="+developerName)));
//        } catch (android.content.ActivityNotFoundException anfe) {
//            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/search?q="+developerName)));
//        }
        Intent morapp = new Intent(Intent.ACTION_VIEW);
        morapp.setData(Uri.parse("https://play.google.com/store/apps/dev?id=6942095385143878874"));
       context.startActivity(morapp);

    }

    public void RateApp() {
        Intent Rate_App = new Intent(Intent.ACTION_VIEW);
        Rate_App.setData(Uri.parse("market://details?id="+context.getPackageName()));
        context.startActivity(Rate_App);
    }

    public  void ShareApp() {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, context.getString( R.string.app_name) + "\n" + "https://play.google.com/store/apps/details?id="+context.getPackageName());
        Intent.createChooser(share, "Share the app");
        context.startActivity(share);

    }

    public void SendEmail() {
        try {
            String[] to = {"ak1169194@gmail.com"};
            Intent SendEmail = new Intent(Intent.ACTION_SEND);
            SendEmail.putExtra(Intent.EXTRA_EMAIL, to);
            SendEmail.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name));
            SendEmail.putExtra(Intent.EXTRA_TEXT, "السلام عليكم ورحمة الله وبركاته معي اقتراح للتطبيق  Grammar وهو :");
            SendEmail.setType("message/rfc822");
            Intent.createChooser(SendEmail, "Send Email");
            context.startActivity(SendEmail);

        }catch (Exception e){
            Toast.makeText(context, "عفوا لا يوجد تطبيق مراسلة في جهازك", Toast.LENGTH_SHORT).show();

        }
    }

    public  void Share_TXT(String textTOshare) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT,textTOshare );
        Intent.createChooser(share, "مشاركة نص");
        context.startActivity(share);
      }



    public void showAppInfo() {

        try {
            InputStream inputStream = context.getAssets().open("help.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader BR = new BufferedReader(inputStreamReader);
            String line;
            StringBuilder msg = new StringBuilder();
            while ((line = BR.readLine()) != null) {
                msg.append(line + "\n");
            }
            AlertDialog.Builder build = new AlertDialog.Builder(context);
            build.setTitle("Help");
            build.setIcon(R.mipmap.ic_launcher);
            build.setMessage(Html.fromHtml(msg + ""));
            build.setNegativeButton("close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    //Negative
                }
            }).show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void update() {
    }


    public void facebook() {
        Intent morapp = new Intent(Intent.ACTION_VIEW);
        morapp.setData(Uri.parse(FACEBOOK));
        context.startActivity(morapp);

    }

    public void github() {
        Intent viewIntent =
                new Intent(Intent.ACTION_VIEW, Uri.parse(WEBSITE));
        context.startActivity(viewIntent);
    }
    public void whatsApp() {
        Intent viewIntent =
                new Intent(Intent.ACTION_VIEW, Uri.parse(WHATS_APP));
        context.startActivity(viewIntent);
    }
}
