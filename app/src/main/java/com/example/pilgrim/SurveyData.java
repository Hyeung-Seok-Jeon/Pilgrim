package com.example.pilgrim;

import java.io.Serializable;

public class SurveyData implements Serializable {


    private String[] survey;

    public SurveyData() {}
    public SurveyData(String[] lineSplit) {
        this.survey = lineSplit;
    }

    public String[] getSurvey() {
        return survey;
    }
    public String getSurveyMore(int position) {
        return survey[position];

    }

    public void setSurvey(String[] survey) {
        this.survey = survey;
    }
}


