package com.fooda.wadalzaki.utils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * Created by leosunzh on 2015/12/24.
 * The animation of the slide list
 */
public class FabAnimatorSet implements View.OnTouchListener{
    float currentY;
    float lastY;
    //滑动方向,下滑为真 Sliding direction, sliding to true
    Boolean lastDirection;
    Boolean currentDirection;

    int touchSlop = 10;

    AnimatorSet backAnimatorSet;
    AnimatorSet hideAnimatorSet;

    FloatingActionButton b;
    ListView listView;

    public void FabAnimatorSet(ListView listView, FloatingActionButton b){
        listView.setOnTouchListener(this);
        this.b = b;
        this.listView = listView;
        //Create a display animation
        backAnimatorSet = new AnimatorSet();
        backAnimatorSet.setDuration(300);

        //Create a hidden animation
        hideAnimatorSet = new AnimatorSet();
        hideAnimatorSet.setDuration(300);

    }

    //Show
    private void back(){
        ObjectAnimator fabBack = ObjectAnimator.ofFloat(b,"translationY",b.getTranslationY(),0f);
        ArrayList<Animator> back = new ArrayList<>();
        back.add(fabBack);
        backAnimatorSet.playTogether(back);
        backAnimatorSet.start();
    }
    //Hide
    private void hide(){
        ObjectAnimator fabHide = ObjectAnimator.ofFloat(b,"translationY",b.getTranslationY(),b.getHeight()+100);
        ArrayList<Animator> hide = new ArrayList<>();
        hide.add(fabHide);
        hideAnimatorSet.playTogether(hide);
        hideAnimatorSet.start();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastY = event.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                float tempCurrentY = event.getY();
                if(Math.abs(tempCurrentY - lastY)>touchSlop){//
                    currentY = tempCurrentY;
                    currentDirection = currentY > lastY;//Determining slip
                    if(currentDirection!=lastDirection){
                        if(currentDirection){//Decline
                            back();
                        }else {//Slip on
                            hide();
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                lastDirection = currentDirection;
                break;
        }
        return false;
    }
}
