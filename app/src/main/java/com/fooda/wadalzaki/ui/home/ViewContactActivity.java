package com.fooda.wadalzaki.ui.home;

import android.Manifest;
import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.fooda.wadalzaki.R;
import com.fooda.wadalzaki.SQLdatabase.SQLEditor;
import com.fooda.wadalzaki.helpers.SetUpImagesHelper;
import com.fooda.wadalzaki.model.UserInfo;

import com.fooda.wadalzaki.utils.MyPermissions;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.fooda.wadalzaki.utils.Constants.INFO_KEY;

public class ViewContactActivity extends AppCompatActivity {
    private UserInfo info;
    private ImageView imageView,addToFavoriteImg,sendEmail,startCall,sendSms,contactBack,contactWats;
    private TextView contactName,contactPhoneNumber,contactEmail,contactAddress,contactNumberTwo,contactNumberThree;
    private SQLEditor sqlEditor;
    private int []isShoucang = new int[]{0,1};
    private MyPermissions permissions;
    private ProgressBar progressBar;
    private LinearLayout numberTwoLayout,numberThreeLayout;
    private SetUpImagesHelper imagesHelper;



    @SuppressLint("CutPasteId")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact);
        sqlEditor = new SQLEditor(this);
        permissions=new MyPermissions(this);
        imagesHelper=new SetUpImagesHelper(this);
        info= (UserInfo) getIntent().getSerializableExtra(INFO_KEY);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        imageView =findViewById(R.id.show_person_image);
        progressBar =findViewById(R.id.progress_bar);
        addToFavoriteImg=findViewById(R.id.contact_toggle_favorite);
        sendEmail=findViewById(R.id.contact_send_email);
        startCall=findViewById(R.id.contact_start_call);
        sendSms=findViewById(R.id.contact_send_sms);
        contactBack=findViewById(R.id.contact_back);
        contactName=findViewById(R.id.contact_name);
        contactPhoneNumber=findViewById(R.id.contact_phone_number);
        numberTwoLayout=findViewById(R.id.contact_number_two_layout);
        numberThreeLayout=findViewById(R.id.contact_number_three_layout);
        contactEmail=findViewById(R.id.contact_email);
        contactAddress=findViewById(R.id.contact_address);
        contactNumberTwo=findViewById(R.id.contact_number_two);
        contactNumberThree=findViewById(R.id.contact_number_three);
        contactWats=findViewById(R.id.contact_wats);
        checkIsFavorite(info.getName());
        contactName.setText(info.getName());
        if (!info.getNumberTwo().equals("")){
            numberTwoLayout.setVisibility(View.VISIBLE);
//            numberTwoLayout.setOnClickListener(view -> {
//                if (permissions.checkPermission(Manifest.permission.CALL_PHONE)){
//                    startingCall(info.getNumberTwo());
//                }
//                else {
//                    permissions.requestPermission(Manifest.permission.CALL_PHONE);
//                }
//            });
        }
        if (!info.getNumberThree().equals("")){
            numberThreeLayout.setVisibility(View.VISIBLE);
            numberThreeLayout.setOnClickListener(view -> {
                if (permissions.checkPermission(Manifest.permission.CALL_PHONE)){
                    startingCall(info.getNumberThree());
                }
                else {
                    permissions.requestPermission(Manifest.permission.CALL_PHONE);
                }
            });
        }
        contactEmail.setText(info.getEmail());
        contactAddress.setText(info.getAddress());
        contactNumberTwo.setText(info.getNumberTwo());
        contactNumberThree.setText(info.getNumberThree());

        contactPhoneNumber.setText(info.getPhoneNumber());
        setUpImage();
        new SetUpImagesHelper(this).setupImage(imageView,progressBar,info.getImagePath());
        addToFavoriteImg.setOnClickListener(v->{
            addToFavorite();
        });
        contactBack.setOnClickListener(v->{
            finish();
        });

//        startCall.setOnClickListener(v->{
//            if (permissions.checkPermission(Manifest.permission.CALL_PHONE)){
//                startingCall(info.getPhoneNumber());
//            }
//            else {
//                permissions.requestPermission(Manifest.permission.CALL_PHONE);
//            }
//
//        });

        sendSms.setOnClickListener(v->{
//            if (permissions.checkPermission(Manifest.permission.SEND_SMS)){
//                sendingSms();
//            }
//            else {
//                permissions.requestPermission(Manifest.permission.SEND_SMS);
//            }

        });
        sendEmail.setOnClickListener(v->{
            sendMyEmail(info.getEmail());
        });
        contactWats.setOnClickListener(v->{
            sendToWts(info.getPhoneNumber());
        });
    }

    private void setUpImage() {
        Picasso.get().load(info.getImagePath()).error(R.drawable.ic_person).placeholder(R.drawable.app_logo).into(imageView, new Callback() {

            @Override
            public void onSuccess() {

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {
                Log.d("Error : ", e.getMessage());
                progressBar.setVisibility(View.GONE);
                imageView.setImageResource(R.drawable.ic_person1);

            }
        });
    }

    private void sendingSms() {
        Intent intent_message = new Intent(Intent.ACTION_SENDTO,Uri.parse("smsto:"+info.getPhoneNumber()));
        startActivity(intent_message);
    }

    private void startingCall(String phoneNumber) {
        Intent intent_call = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phoneNumber));
        startActivity(intent_call);
    }

    private void addToFavorite() {
        if(isShoucang[0]==0){
            sqlEditor.add(info);
            //sqlEditor.add(id,name,number);
            addToFavoriteImg.setImageResource(R.drawable.icon_star1);
            exChange();
            Toast.makeText(this,"تمت الاضافة الى المفضلة",Toast.LENGTH_SHORT).show();
        }else {
            sqlEditor.delete(info.getName());
            // sqlEditor.delete(name);
            addToFavoriteImg.setImageResource(R.drawable.icon_star2);
            exChange();
        }
    }

    public void checkIsFavorite(String name){
        List<UserInfo> list = sqlEditor.query("name='"+name+"'");
        if(list.size()!=0){
            addToFavoriteImg.setImageResource(R.drawable.icon_star1);
            exChange();
        }else {
            addToFavoriteImg.setImageResource(R.drawable.icon_star2);
        }
    }
    public void exChange(){
        int temp = isShoucang[0];
        isShoucang[0] = isShoucang[1];
        isShoucang[1] = temp;
    }
    public void sendMyEmail(String toPerson) {
        try {
            String[] to = {toPerson};
            Intent SendEmail = new Intent(Intent.ACTION_SEND);
            SendEmail.putExtra(Intent.EXTRA_EMAIL, to);
            SendEmail.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            SendEmail.putExtra(Intent.EXTRA_TEXT, "السلام عليكم ورحمة الله وبركاته  :");
            SendEmail.setType("message/rfc822");
            Intent.createChooser(SendEmail, "Send Email");
            startActivity(SendEmail);

        }catch (Exception e){
            Toast.makeText(this, "عفوا لا يوجد تطبيق مراسلة في جهازك", Toast.LENGTH_SHORT).show();

        }
    }
    public static void actionStart(Context context, UserInfo info) {
        Intent intent = new Intent(context, ViewContactActivity.class);
        intent.putExtra(INFO_KEY,info);
        context.startActivity(intent);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            startActivity(new Intent(this, HomeActivity.class));
            //onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
    private boolean isAccessibilityOn (Context context, Class<? extends AccessibilityService> clazz) {
        int accessibilityEnabled = 0;
        final String service = context.getPackageName () + "/" + clazz.getCanonicalName ();
        try {
            accessibilityEnabled = Settings.Secure.getInt (context.getApplicationContext ().getContentResolver (), Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException ignored) {  }

        TextUtils.SimpleStringSplitter colonSplitter = new TextUtils.SimpleStringSplitter (':');

        if (accessibilityEnabled == 1) {
            String settingValue = Settings.Secure.getString (context.getApplicationContext ().getContentResolver (), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                colonSplitter.setString (settingValue);
                while (colonSplitter.hasNext ()) {
                    String accessibilityService = colonSplitter.next ();

                    if (accessibilityService.equalsIgnoreCase (service)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
    protected void sendToWts(String smsNumber){
        //String smsNumber = "2126123456789"; // E164 format without '+' sign
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        //  Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_TEXT, "test \n");
        sendIntent.putExtra("jid", smsNumber + "@s.whatsapp.net"); //phone number without "+" prefix
        sendIntent.setPackage("com.whatsapp");
//        if (!isAccessibilityOn (this, WhatsappAccessibilityService.class)) {
//            Intent intent = new Intent (Settings.ACTION_ACCESSIBILITY_SETTINGS);
//           startActivity (intent);
//        }
        startActivity(sendIntent);
    }
   private void sendWats(String phone,String message){

       String total = "https://api.whatsapp.com/send?phone=" +  phone + "&text=" + message;
       Intent intent = new Intent(Intent.ACTION_VIEW);
       intent.setData(Uri.parse(total));

       intent.setPackage("com.whatsapp");
       if (intent.resolveActivity(getPackageManager()) != null) startActivity(intent);
       else Toast.makeText(this, getString(R.string.not_installed), Toast.LENGTH_SHORT).show();
   }



}
