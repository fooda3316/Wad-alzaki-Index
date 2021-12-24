package com.fooda.wadalzaki.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.fooda.wadalzaki.R;
import com.fooda.wadalzaki.themes.SetThemes;
import com.fooda.wadalzaki.utils.LanguageUtilits;


public class UserSettingActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton btn_blue, btn_black, btn_green, btn_purple, btn_yallow,btn_blue_pressed,btn_red;
    private Button reset, chang_lang,hideShow;
    private ImageView back_to_home;
    private SharedPreferences shard;
    private LanguageUtilits utilits;

    protected PowerManager.WakeLock mWakeLock;

    @SuppressLint("UseSwitchCompatOrMaterialCode")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetThemes.setThemes(this);
        setContentView(R.layout.activity_settings);
        utilits=new LanguageUtilits(this,this);
        //you can set the title for your toolbar here for different fragments different titles
        Toolbar toolbar = findViewById(R.id.settings_toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        this.setTitle(getString(R.string.settings));
        findViews();
        setOnClick();
        reset.setOnClickListener(v -> new AlertDialog.Builder(UserSettingActivity.this)
                .setIcon(R.drawable.app_logo)
                .setTitle(getResources().getString(R.string.reset_dialoge_title))
                .setMessage(getResources().getString(R.string.reset_dialoge_message))
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        resetStyle();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show());
        findViewById(R.id.BTN_ar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utilits.setLocale("ar");
            }
        });
        findViewById(R.id.BTN_en).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utilits.setLocale("en");
            }
        });
        hideShow.setOnClickListener(view -> {

        });
    }


    private void setOnClick() {
        btn_black.setOnClickListener(this);
        btn_blue.setOnClickListener(this);
        btn_green.setOnClickListener(this);
        btn_purple.setOnClickListener(this);
        btn_yallow.setOnClickListener(this);
        back_to_home.setOnClickListener(this);
        chang_lang.setOnClickListener(this);
        btn_blue_pressed.setOnClickListener(this);
        btn_red.setOnClickListener(this);
    }

    private void findViews() {
        btn_black = findViewById(R.id.imag_black);
        btn_blue = findViewById(R.id.imag_blue);
        btn_green = findViewById(R.id.imag_green);
        btn_purple = findViewById(R.id.imag_purple);
        btn_yallow = findViewById(R.id.imag_yallow);
        reset = findViewById(R.id.button_fooda_restore_default);
        back_to_home = findViewById(R.id.back_to_home);
        chang_lang = findViewById(R.id.button_chang_lang);
        btn_blue_pressed = findViewById(R.id.imag_blue_pressed);
        btn_red = findViewById(R.id.imag_red);
        hideShow=findViewById(R.id.btn_hide_show);
    }

    public void changeStyle(int myStyle) {
        shard = getSharedPreferences("Setting", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shard.edit();
        editor.putInt("myStyle", myStyle);
        editor.apply();
        refreshPage();
    }

    public void resetStyle() {
//        shard = getSharedPreferences("Setting", Context.MODE_PRIVATE);
//        shard.edit().clear().commit();
        changeStyle(7);
        refreshPage();
    }

    private void refreshPage() {
        recreate();
    }

    @Override
    public void onDestroy() {
        // this.mWakeLock.release();
        super.onDestroy();
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

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imag_black:
                changeStyle(1);
                break;
            case R.id.imag_blue:
                changeStyle(2);
                break;
            case R.id.imag_green:
                changeStyle(3);
                break;
            case R.id.imag_purple:
                changeStyle(4);
                break;
            case R.id.imag_yallow:
                changeStyle(5);
                break;
            case R.id.back_to_home:
                backToHome();
                break;
            case R.id.imag_blue_pressed:
                changeStyle(6);
                break;
                case R.id.imag_red:
                    changeStyle(7);
                break;
        }
    }


    private void backToHome() {
        finish();
        startActivity(new Intent(UserSettingActivity.this, HomeActivity.class));
    }




}
