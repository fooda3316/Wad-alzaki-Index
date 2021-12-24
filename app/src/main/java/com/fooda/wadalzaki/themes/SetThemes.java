package com.fooda.wadalzaki.themes;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;

import com.fooda.wadalzaki.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import static android.content.Context.MODE_PRIVATE;
import static com.fooda.wadalzaki.utils.Constants.MY_STYLE;
import static com.fooda.wadalzaki.utils.Constants.SETTINGS_PREFERENCES;


public class SetThemes {
    private static SharedPreferences themShard,navigationShard;
    private static int myStyle;


    public static void setThemes(AppCompatActivity activity){
        themShard = activity.getSharedPreferences(SETTINGS_PREFERENCES, MODE_PRIVATE);
        myStyle = themShard.getInt(MY_STYLE, 0);
        switch (myStyle) {
            case 0:
                activity.setTheme(R.style.AppTheme);
                break;
            case 1:
                activity.setTheme(R.style.AppTheme1);
                break;
            case 2:
                activity.setTheme(R.style.AppTheme2);
                break;
            case 3:
                activity.setTheme(R.style.AppTheme3);
                break;
            case 4:
                activity.setTheme(R.style.AppTheme4);
                break;
            case 5:
                activity.setTheme(R.style.AppTheme5);
                break;
            case 6:
                activity.setTheme(R.style.AppTheme6);
                break;
            case 7:
                activity.setTheme(R.style.AppTheme7);
                break;
        }
    }

    public static void ChangeNavigationItemTextColor(NavigationView view, AppCompatActivity activity) {
        navigationShard = activity.getSharedPreferences(SETTINGS_PREFERENCES, MODE_PRIVATE);
        myStyle = navigationShard.getInt(MY_STYLE, 0);
        switch (myStyle) {
            case 0:
                view.setItemTextColor(ColorStateList.valueOf((Color.parseColor("#f2f2f2"))));
                view.setItemIconTintList(ColorStateList.valueOf((Color.parseColor("#f2f2f2"))));
                break;
            case 1:
                view.setItemTextColor(ColorStateList.valueOf((Color.parseColor("#090909"))));
                view.setItemIconTintList(ColorStateList.valueOf((Color.parseColor("#090909"))));

                break;
            case 2:
                view.setItemTextColor(ColorStateList.valueOf((Color.parseColor("#0529F1"))));
                view.setItemIconTintList(ColorStateList.valueOf((Color.parseColor("#0529F1"))));

                break;
            case 3:
                view.setItemTextColor(ColorStateList.valueOf((Color.parseColor("#009311"))));
                view.setItemIconTintList(ColorStateList.valueOf((Color.parseColor("#009311"))));
//
                break;
            case 4:
                view.setItemTextColor(ColorStateList.valueOf((Color.parseColor("#FFBB86FC"))));
                view.setItemIconTintList(ColorStateList.valueOf((Color.parseColor("#FFBB86FC"))));

                break;
            case 5:
                view.setItemTextColor(ColorStateList.valueOf((Color.parseColor("#f8d64e"))));
                view.setItemIconTintList(ColorStateList.valueOf((Color.parseColor("#f8d64e"))));

                break;
            case 6:
                view.setItemTextColor(ColorStateList.valueOf((Color.parseColor("#ff0099cc"))));
                view.setItemIconTintList(ColorStateList.valueOf((Color.parseColor("#ff0099cc"))));

                break;
            case 7:
                view.setItemTextColor(ColorStateList.valueOf((Color.parseColor("#FA0426"))));
                view.setItemIconTintList(ColorStateList.valueOf((Color.parseColor("#FA0426"))));

                break;
        }
    }

    public static void ChangeNavigationHeaderColor(LinearLayout view, Context activity) {
        themShard = activity.getSharedPreferences(SETTINGS_PREFERENCES, MODE_PRIVATE);
        myStyle = themShard.getInt(MY_STYLE, 0);
        switch (myStyle) {
            case 0:
                view.setBackgroundColor((Color.parseColor("#f2f2f2")));

                break;
            case 1:
                view.setBackgroundColor((Color.parseColor("#090909")));

                break;
            case 2:
                view.setBackgroundColor((Color.parseColor("#0529F1")));


                break;
            case 3:
                view.setBackgroundColor((Color.parseColor("#009311")));
                break;
            case 4:
                view.setBackgroundColor((Color.parseColor("#FFBB86FC")));

                break;
            case 5:
                view.setBackgroundColor((Color.parseColor("#f8d64e")));

                break;
            case 6:
                view.setBackgroundColor((Color.parseColor("#ff0099cc")));

                break;
            case 7:
                view.setBackgroundColor((Color.parseColor("#FA0426")));

                break;
        }
    }
    public static void ChangeFloatingActionButtonColor(FloatingActionButton toolbar, Context activity) {
        themShard = activity.getSharedPreferences(SETTINGS_PREFERENCES, MODE_PRIVATE);
        myStyle = themShard.getInt(MY_STYLE, 0);
        switch (myStyle) {
            case 0:
                toolbar.setRippleColor((Color.parseColor("#f2f2f2")));
                break;
            case 1:
                toolbar.setRippleColor((Color.parseColor("#090909")));

                break;
            case 2:
                toolbar.setRippleColor((Color.parseColor("#0529F1")));

                break;
            case 3:
                toolbar.setRippleColor((Color.parseColor("#009311")));

                break;
            case 4:
                toolbar.setRippleColor((Color.parseColor("#FFBB86FC")));

                break;
            case 5:
                toolbar.setRippleColor((Color.parseColor("#f8d64e")));

                break;
            case 6:
                toolbar.setRippleColor((Color.parseColor("#ff0099cc")));
                break;
            case 7:
                toolbar.setRippleColor((Color.parseColor("#FA0426")));
                break;
        }
    }
    public static void ChangeToolbarColor(Toolbar toolbar, AppCompatActivity activity) {
        themShard = activity.getSharedPreferences(SETTINGS_PREFERENCES, MODE_PRIVATE);
        myStyle = themShard.getInt(MY_STYLE, 0);
        switch (myStyle) {
            case 0:
                toolbar.setBackgroundColor((Color.parseColor("#f2f2f2")));
                break;
            case 1:
                toolbar.setBackgroundColor((Color.parseColor("#090909")));

                break;
            case 2:
                toolbar.setBackgroundColor((Color.parseColor("#0529F1")));

                break;
            case 3:
                toolbar.setBackgroundColor((Color.parseColor("#009311")));

                break;
            case 4:
                toolbar.setBackgroundColor((Color.parseColor("#FFBB86FC")));

                break;
            case 5:
                toolbar.setBackgroundColor((Color.parseColor("#f8d64e")));

                break;
            case 6:
                toolbar.setBackgroundColor((Color.parseColor("#ff0099cc")));
                break;
            case 7:
                toolbar.setBackgroundColor((Color.parseColor("#FA0426")));
                break;
        }
    }
    public static void ChangeTabColor(TabLayout toolbar, Context activity) {
        themShard = activity.getSharedPreferences(SETTINGS_PREFERENCES, MODE_PRIVATE);
        myStyle = themShard.getInt(MY_STYLE, 0);
        switch (myStyle) {
            case 0:
                toolbar.setBackgroundColor((Color.parseColor("#f2f2f2")));
                break;
            case 1:
                toolbar.setBackgroundColor((Color.parseColor("#090909")));

                break;
            case 2:
                toolbar.setBackgroundColor((Color.parseColor("#0529F1")));

                break;
            case 3:
                toolbar.setBackgroundColor((Color.parseColor("#009311")));

                break;
            case 4:
                toolbar.setBackgroundColor((Color.parseColor("#FFBB86FC")));

                break;
            case 5:
                toolbar.setBackgroundColor((Color.parseColor("#f8d64e")));

                break;
            case 6:
                toolbar.setBackgroundColor((Color.parseColor("#ff0099cc")));
                break;
            case 7:
                toolbar.setBackgroundColor((Color.parseColor("#FA0426")));
                break;
        }
    }
    public static void setLayoutDirection(View yourParentContainer){
        ViewCompat.setLayoutDirection(yourParentContainer, ViewCompat.LAYOUT_DIRECTION_RTL);
    }

    public static void setImageBackGround(ImageView view, Context activity) {
        themShard = activity.getSharedPreferences(SETTINGS_PREFERENCES, MODE_PRIVATE);
        myStyle = themShard.getInt(MY_STYLE, 0);
        switch (myStyle) {
            case 0:
                view.setBackgroundColor((Color.parseColor("#f2f2f2")));

                break;
            case 1:
                view.setBackgroundColor((Color.parseColor("#090909")));

                break;
            case 2:
                view.setBackgroundColor((Color.parseColor("#0529F1")));


                break;
            case 3:
                view.setBackgroundColor((Color.parseColor("#009311")));
                break;
            case 4:
                view.setBackgroundColor((Color.parseColor("#FFBB86FC")));

                break;
            case 5:
                view.setBackgroundColor((Color.parseColor("#f8d64e")));

                break;
            case 6:
                view.setBackgroundColor((Color.parseColor("#ff0099cc")));

                break;
            case 7:
                view.setBackgroundColor((Color.parseColor("#FA0426")));

                break;
        }
    }

    public static void setLayoutBackGround(ConstraintLayout view, Context activity) {
        themShard = activity.getSharedPreferences(SETTINGS_PREFERENCES, MODE_PRIVATE);
        myStyle = themShard.getInt(MY_STYLE, 0);
        switch (myStyle) {
            case 0:
                view.setBackgroundColor((Color.parseColor("#f2f2f2")));

                break;
            case 1:
                view.setBackgroundColor((Color.parseColor("#090909")));

                break;
            case 2:
                view.setBackgroundColor((Color.parseColor("#0529F1")));


                break;
            case 3:
                view.setBackgroundColor((Color.parseColor("#009311")));
                break;
            case 4:
                view.setBackgroundColor((Color.parseColor("#FFBB86FC")));

                break;
            case 5:
                view.setBackgroundColor((Color.parseColor("#f8d64e")));

                break;
            case 6:
                view.setBackgroundColor((Color.parseColor("#ff0099cc")));

                break;
            case 7:
                view.setBackgroundColor((Color.parseColor("#FA0426")));

                break;
        }
    }
    public static void ChangeButtonColor(Button button, Context activity) {
        themShard = activity.getSharedPreferences(SETTINGS_PREFERENCES, MODE_PRIVATE);
        myStyle = themShard.getInt(MY_STYLE, 0);
        switch (myStyle) {
            case 0:
                button.setBackgroundColor((Color.parseColor("#f2f2f2")));
                break;
            case 1:
                button.setBackgroundColor((Color.parseColor("#090909")));

                break;
            case 2:
                button.setBackgroundColor((Color.parseColor("#0529F1")));

                break;
            case 3:
                button.setBackgroundColor((Color.parseColor("#009311")));

                break;
            case 4:
                button.setBackgroundColor((Color.parseColor("#FFBB86FC")));

                break;
            case 5:
                button.setBackgroundColor((Color.parseColor("#f8d64e")));

                break;
            case 6:
                button.setBackgroundColor((Color.parseColor("#ff0099cc")));
                break;
            case 7:
                button.setBackgroundColor((Color.parseColor("#FA0426")));
                break;
        }
    }
    public static void ChangeCardColor(CardView button, Context activity) {
        themShard = activity.getSharedPreferences(SETTINGS_PREFERENCES, MODE_PRIVATE);
        myStyle = themShard.getInt(MY_STYLE, 0);
        switch (myStyle) {
            case 0:
                button.setCardBackgroundColor((Color.parseColor("#f2f2f2")));
                break;
            case 1:
                button.setCardBackgroundColor((Color.parseColor("#090909")));

                break;
            case 2:
                button.setCardBackgroundColor((Color.parseColor("#0529F1")));

                break;
            case 3:
                button.setCardBackgroundColor((Color.parseColor("#009311")));

                break;
            case 4:
                button.setCardBackgroundColor((Color.parseColor("#FFBB86FC")));

                break;
            case 5:
                button.setCardBackgroundColor((Color.parseColor("#f8d64e")));

                break;
            case 6:
                button.setCardBackgroundColor((Color.parseColor("#ff0099cc")));
                break;
            case 7:
                button.setCardBackgroundColor((Color.parseColor("#FA0426")));
                break;
        }
    }


}
