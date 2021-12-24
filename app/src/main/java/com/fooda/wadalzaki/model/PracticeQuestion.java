package com.fooda.wadalzaki.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PracticeQuestion implements Serializable {

    @JsonAlias("quetn")
    private String question;

    @JsonAlias("optns")
    private String options;

    @JsonAlias("1")
    private String answer1;

    @JsonAlias("2")
    private String answer2;

    @JsonAlias("3")
    private String answer3;

    @JsonAlias("4")
    private String answer4;

    @JsonAlias("Answr")
    private String correct;
    public String getQuestion() {
        return question;
    }

    public String getOptions() {
        return options;
    }

    public String getAnswer1() {
        return answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public String getAnswer4() {
        return answer4;
    }



    public int getAmountOfAnswers(){
        String options = this.options;
        options = options.replaceAll(",","");
        return options.length();
    }

    public int getCorrect(){
        return Integer.valueOf(correct);
    }


}
