package com.fooda.wadalzaki.ui.check;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fooda.wadalzaki.R;
import com.fooda.wadalzaki.helpers.SharedPreferencesHelper;
import com.fooda.wadalzaki.ui.home.HomeActivity;
import com.fooda.wadalzaki.ui.login.LogInActivity;


public class ShowActivity extends AppCompatActivity implements View.OnClickListener {
private TextView editText,txt_wrong_message;
    public static final String PREVENT ="prevent" ;
    public static final String CHANCE ="chance";
    public static final String PREVENT_MESSAGE = "prevent_message";
    public static final String CHANCE_KEY="chance_key";
    private SharedPreferences prevent,chances;

private LinearLayout messageLayout;
private Button btnInter;
private CardView cardViewSory;
    private String title;
    private String message;
    private String chance;
    private SharedPreferencesHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        helper=new SharedPreferencesHelper(this);
        prevent = getSharedPreferences(PREVENT, Context.MODE_PRIVATE);
        chances=getSharedPreferences(CHANCE,MODE_PRIVATE);
        chance=chances.getString(CHANCE_KEY,"تبقى لك محاولتان");
        editText=findViewById(R.id.txt_show_message);
        btnInter=findViewById(R.id.btnInter);
        txt_wrong_message =findViewById(R.id.txt_wrong_message);
        messageLayout =findViewById(R.id.messageLayout);
        cardViewSory =findViewById(R.id.cardViewSory);
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
        message=bundle.getString(CheckActivity.MESSAGE);
        title=bundle.getString(CheckActivity.BUTTON_TITLE);
        editText.setText(message);
        btnInter.setText(title);}
        btnInter.setOnClickListener(this);
        if (bundle!=null&&title.equals("دخول")){
            editText.setTextColor(getResources().getColor(R.color.green));
        }
        else {
            if (chance.equals("أخر محاولة")){
            messageLayout.setVisibility(View.GONE);
          //  editText.setTextColor(getResources().getColor(R.color.red));
                cardViewSory.setVisibility(View.VISIBLE);
            SharedPreferences.Editor editor=prevent.edit();
            editor.putBoolean(PREVENT_MESSAGE,true);
            editor.apply();}
            else {
                btnInter.setText(title);
                editText.setText(message+"\n"+chance);

            }
        }
    }


    @Override
    public void onClick(View v) {
        finish();
        if (title.equals("دخول")){
            if (helper.isUserLogged()){
                startActivity(new Intent(ShowActivity.this, HomeActivity.class));
            }
            else {
                startActivity(new Intent(ShowActivity.this, LogInActivity.class));}

        }

        else {
            startActivity(new Intent(ShowActivity.this,CheckActivity.class));
            if (chance.equals("تبقى لك محاولتان")){
                SharedPreferences.Editor editor=chances.edit();
                editor.putString(CHANCE_KEY,"تبقى لك محاولة واحدة");
                editor.apply();
            }
            else if (chance.equals("تبقى لك محاولة واحدة")){
                SharedPreferences.Editor editor=chances.edit();
                editor.putString(CHANCE_KEY,"أخر محاولة");
                editor.apply();
            }

    }

    }
}