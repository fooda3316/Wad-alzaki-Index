package com.fooda.wadalzaki.ui.check;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fooda.wadalzaki.R;
import com.fooda.wadalzaki.model.PracticeQuestion;
import com.fooda.wadalzaki.model.PracticeTest;

import java.io.IOException;

import static com.fooda.wadalzaki.ui.check.ShowActivity.CHANCE;
import static com.fooda.wadalzaki.ui.check.ShowActivity.PREVENT;
import static com.fooda.wadalzaki.utils.Constants.SAVE_CHECKED;

public class CheckActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String FINAL_ANSWER_KEY = "final_answer_key";
    public static final  String MESSAGE = "message";
    public static final String BUTTON_TITLE ="title" ;
    private PracticeTest practiceTest;
    private RadioButton check1, check2, check3, check4,check;
    private TextView loadQuestions ,txtResult,headerTV,header2TV,topicTV,questionNumberTV;
    private Button buttonNext;
    CardView checkCardView;
    private int questionIndex = 0;
    private boolean isAnswerSelected;
    private Handler mHandler = new Handler();
    private Runnable  mUpdateTimeTask;
    public final static String PRACTICE_TESTS_PATH = "test/";
    private SharedPreferences saveAnswer,prevent,chances;
    public  Boolean ANSWER_ONE =false ;
    public  Boolean ANSWER_Two =false;
    public  Boolean ANSWER_THREE =false;
    public  Boolean ANSWER_FOUR =false;
    public  Boolean ANSWER_FIVE =false;
    public  Boolean ANSWER_SIX =false;
    Boolean answer=false;
    private Animation animation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_main);
        saveAnswer =getSharedPreferences(SAVE_CHECKED,MODE_PRIVATE);
        prevent=getSharedPreferences(PREVENT,MODE_PRIVATE);
        chances=getSharedPreferences(CHANCE,MODE_PRIVATE);
        if (isUserPrevented()){
            finish();
            startActivity(new Intent( CheckActivity.this, ShowActivity.class ));
        }
        setViews();
        loadTest();
        setCardEnm();
        loadQuestion( practiceTest.getQuestions().get( questionIndex ) );
        buttonNext.setOnClickListener(this);
    }

    private void setCardEnm() {
        animation = AnimationUtils.loadAnimation(this,R.anim.uptodowndiagonal);
        checkCardView.setAnimation(animation);
    }


    private void setViews() {
        check1 = findViewById(R.id.choice_1);
        check2 = findViewById(R.id.choice_2);
        check3 = findViewById(R.id.choice_3);
        check4 = findViewById(R.id.choice_4);
        check = findViewById(R.id.choice);
        check.setVisibility(View.GONE);
        headerTV = findViewById(R.id.practiceHeaderTVtest );
        loadQuestions = findViewById(R.id.txt_show_question);
        txtResult = findViewById(R.id.txt_show_result);
        buttonNext = findViewById(R.id.btn_next);
        checkCardView = findViewById(R.id.checkCardView);
    }

    @Override
    public void onClick(View v) {
        if (isRBChecked()){
            if (showResult() )
            {
             //   showStatus("Correct answer",getResources().getColor( R.color.colorAccent ));

                switch ( questionIndex){
                    case 0: ANSWER_ONE =true; break;
                    case 1: ANSWER_Two =true; break;
                    case 2: ANSWER_THREE =true; break;
                    case 3: ANSWER_FOUR =true; break;
                    case 4: ANSWER_FIVE =true; break;
                    case 5: ANSWER_SIX =true; break;

                }
                moveToNextQuestion();
                switch (questionIndex) {
                    case 0:
                     answer=ANSWER_ONE;
                    case 1:
                        answer=ANSWER_Two;
                    case 2:
                        answer=ANSWER_THREE;
                    case 3:
                        answer=ANSWER_FOUR;
                    case 4:
                        answer=ANSWER_FIVE;
                    case 5:
                        answer=ANSWER_SIX;
                }
                unCheckRB();
            }
            else {
              //  showStatus("wrong answer",getResources().getColor( R.color.colorAccent ));

                switch ( questionIndex){
                    case 0: ANSWER_ONE =false; break;
                    case 1: ANSWER_Two =false; break;
                    case 2: ANSWER_THREE =false; break;
                    case 3: ANSWER_FOUR =false; break;
                    case 4: ANSWER_FIVE =false; break;
                    case 5: ANSWER_SIX =false; break;

                }
                moveToNextQuestion();
                unCheckRB();
            }
        }
        else {showStatus("اختر إجابة من فضلك",getResources().getColor( R.color.colorPrimaryDark ));}

    }

    private void answerIs(Boolean selectedAnswer) {
        SharedPreferences.Editor editor=saveAnswer.edit();
        editor.putBoolean(FINAL_ANSWER_KEY,selectedAnswer);
        editor.apply();
    }

    private boolean isRBChecked() {
        if (check1.getVisibility()==View.VISIBLE){
            if (check1.isChecked()) {
                return true;
            }
            else if (check2.getVisibility()==View.VISIBLE){
                if (check2.isChecked()) {
                    return true;
                }
                else if (check3.getVisibility()==View.VISIBLE){
                    if (check3.isChecked()) {
                        return true;
                    } else if (check4.getVisibility()==View.VISIBLE){
                        if (check4.isChecked()) {
                            return true;
                        }

                    } }}}


        return false;
    }
    private boolean showResult() {
        RadioGroup radioGroup = findViewById(R.id.answersGroup);
        int i = radioGroup.getCheckedRadioButtonId();
        switch (i){
            case R.id.choice_1: if( practiceTest.getQuestions().get(questionIndex).getCorrect()==1){
                return true;}
            else return false; //break;
            case R.id.choice_2:if(  practiceTest.getQuestions().get(questionIndex).getCorrect()==2){
                return true;}
            else return false;//break;
            case R.id.choice_3:if(  practiceTest.getQuestions().get(questionIndex).getCorrect()==3){
                return true;}
            else return false;//break;
            case R.id.choice_4:if(  practiceTest.getQuestions().get(questionIndex).getCorrect()==4){
                return true;}
            else return false;//break;
        }
        return false;

    }
    private void moveToNextQuestion(){
     if (questionIndex < practiceTest.getQuestions().size() - 1 ) {
            isAnswerSelected = false;
   questionIndex++;
            loadQuestion( practiceTest.getQuestions().get( questionIndex ) );
        } else {
         Boolean finalAnswer=(ANSWER_ONE&&ANSWER_Two&&ANSWER_THREE&&ANSWER_FOUR&&ANSWER_FIVE&&ANSWER_SIX);
         answerIs(finalAnswer);
        if (finalAnswer){
            goToShow("تهانينا ... \nلقد اجتزت اختبار الامان بنجاح","دخول");
        }else {
            goToShow("نأسف ... \nلم تجتز اختبار الامان بنجاح \n هل ترغب في المحاولة من جديد ؟","حاول من جديد");
        }
        }
    }

    private void goToShow(String message,String title) {
        finish();
        startActivity(new Intent(CheckActivity.this,ShowActivity.class).putExtra(BUTTON_TITLE,title).putExtra(MESSAGE,message));
    }

    private void loadQuestion(PracticeQuestion question){
     //   System.out.println(question);
        int number = questionIndex+1;
        check1.setVisibility( View.INVISIBLE);
        check2.setVisibility(View.INVISIBLE);
        check3.setVisibility(View.INVISIBLE);
        check4.setVisibility(View.INVISIBLE);
        loadQuestions.setText(question.getQuestion());
        headerTV.setText(practiceTest.getHeader());
        if (question.getAmountOfAnswers()==2){
            check1.setVisibility(View.VISIBLE);
            check1.setText(question.getAnswer1());
            check2.setVisibility(View.VISIBLE);
            check2.setText(question.getAnswer2());
            check3.setVisibility(View.GONE);
            check4.setVisibility(View.GONE);
        }
        else if (question.getAmountOfAnswers()==3){
            check1.setVisibility(View.VISIBLE);
            check1.setText(question.getAnswer1());
            check2.setVisibility(View.VISIBLE);
            check2.setText(question.getAnswer2());
            check3.setVisibility(View.VISIBLE);
            check3.setText(question.getAnswer3());
            check4.setVisibility(View.GONE);
        }
        else if (question.getAmountOfAnswers()==4){
            check1.setVisibility(View.VISIBLE);
            check1.setText(question.getAnswer1());
            check2.setVisibility(View.VISIBLE);
            check2.setText(question.getAnswer2());
            check3.setVisibility(View.VISIBLE);
            check3.setText(question.getAnswer3());
            check4.setVisibility(View.VISIBLE);
            check4.setText(question.getAnswer4());
        }
        else {
            check1.setVisibility(View.GONE);
            check2.setVisibility(View.GONE);
            check3.setVisibility(View.GONE);
            check4.setVisibility(View.GONE);
        }

    }
    private void finishTest(){

        finish();
    }

    private void unCheckRB() {
        check1.setChecked( false );
        check1.setChecked( true );
        //check1.setClickable( true );

        check2.setChecked( false );
        check2.setChecked( true );
        // check2.setClickable( true );

        check3.setChecked( false );
        check3.setChecked( true );
        //check3.setClickable( true );

        check4.setChecked( false );
        check4.setChecked( true );
        // check4.setClickable( true );
        check.setChecked( false );
        check.setChecked( true );
    }
    void showStatus(String paramString ,int color) {
        animateView(this.txtResult);
        this.txtResult.setText(paramString);
        this.txtResult.setTextColor(color);
        this.txtResult.setVisibility(View.VISIBLE);
        mUpdateTimeTask= new Runnable() {

            public void run() {
                txtResult.setVisibility(View.INVISIBLE);
            }
        };
        mHandler.postDelayed(mUpdateTimeTask, 2000L);

    }
    void animateView(View paramView) {
        try {
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(paramView, "alpha", new float[] { 0.0F, 1.0F });
            objectAnimator.setDuration(500L);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.play(objectAnimator);
            animatorSet.start();
            return;
        } catch (Exception exception) {
            return;
        } catch (Error error) {
            return;
        }
    }
    private void loadTest() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            //practiceQuestion = mapper.readValue(getAssets()
            // .open( Utils.PRACTICE_TESTS_PATH + testName+".json"), PracticeQuestion.class);
            practiceTest = mapper.readValue(getAssets()
                    .open( PRACTICE_TESTS_PATH + "wad_alzaki_test.json"), PracticeTest.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private Boolean isUserPrevented(){
        return prevent.getBoolean(ShowActivity.PREVENT_MESSAGE,false);
    }




}