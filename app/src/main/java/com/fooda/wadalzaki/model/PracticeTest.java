package com.fooda.wadalzaki.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PracticeTest implements Serializable {
    @JsonAlias("Mainhd")
    private String header;
    @JsonAlias("minutes")
    private int minutes;
    @JsonAlias("Sub")
    private List<PracticeQuestion> questions;



    private int bestScore = 0;
    public void setBestScore(int bestScore) {
        this.bestScore = bestScore;
    }

    public String getHeader() {
        return header;
    }

    public List<PracticeQuestion> getQuestions() {
        return questions;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getBestScore() {
        return bestScore;
    }


}
