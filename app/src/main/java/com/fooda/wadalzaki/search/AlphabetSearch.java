package com.fooda.wadalzaki.search;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fooda.wadalzaki.model.UserInfo;

import java.util.ArrayList;
import java.util.List;


public class AlphabetSearch implements View.OnTouchListener{

    LinearLayout alphabetLayout;//Linear layout of letters
    ListView listView;
    TextView current; //This TextView is responsible of showing the letter that user pressed on it
    List<String> firstKey; //Contact data list<Persen>The first letter set
    String arabicAlphaBet[]={"ا","ب","ت","ث","ج","ح","خ","د","ذ","ر","س","ش","ص","ض","ط","ظ","ع","غ","ف","ق","ك","ل","م","ن","ه","و","ي","#"};
    String alpabet[]={"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R",
                       "S","T","U","V","W","X","Y","Z","#"};
    private static final String TAG = "AlphabetSearch";
    float height;//alphabetLayout Height

    public AlphabetSearch(final LinearLayout alphabetLayout, ListView listView, TextView current){
        this.alphabetLayout = alphabetLayout;
        this.listView = listView;
        this.current = current;

        ViewTreeObserver vto = this.alphabetLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                height = alphabetLayout.getHeight();
                alphabetLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        alphabetLayout.setOnTouchListener(this);
    }

    public void whenListChange(List<UserInfo>list){
        this.firstKey = getFirstKey(list);
    }


    /**
     * click events
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        current.setVisibility(View.VISIBLE);
        float Y = (event.getY()/height)*29;
        int index = (int)Y;
        if(index > -1 && index < 28) {//منع تجاوز ال 28 حرف
            String key = arabicAlphaBet[index];
            current.setText(key);
            if(firstKey.contains(key)){
                int position = firstKey.indexOf(key);
                listView.setSelectionFromTop(position,0);
            }
        }

        //when  the user lifts his finger from  the item عندما يرفع المستخدم اصبعة من الاسماء
        if (event.getAction() == MotionEvent.ACTION_UP||event.getAction()==MotionEvent.ACTION_OUTSIDE) {
            current.setVisibility(View.GONE);
        }
        return true;
    }

    /**
     * Get an array of contact phonetic initials
     */
    public List<String> getFirstKey(List<UserInfo>list){
        List<String> firstKey = new ArrayList<>();
        for (int i=0;i<list.size();i++){
            String key = list.get(i).getName().substring(0,1);
            Log.e(TAG, "getFirstKey: key "+key );
            if(key.matches("[ا-ي]")){
                firstKey.add(key);
            }else {
                firstKey.add("#");
            }
        }
        return firstKey;
    }


}
