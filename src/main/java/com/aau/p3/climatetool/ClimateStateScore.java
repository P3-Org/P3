package com.aau.p3.climatetool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClimateStateScore {
    private List<double[]> listOfRGBValues = new ArrayList<>();

//    public int computeScore(List<RiskAssessment> assessmentList, double[][] coordinates) {
//        for (RiskAssessment risk : assessmentList) {
//            listOfRGBValues.add(risk.gatherData(coordinates));
//        }
//        formula(listOfRGBValues);
//        return 1;
//    }

    private void formula(List<double[]> RGBValues) {
        for (double[] arr : RGBValues) {
            System.out.println("You gotta check this out:" + RGBValues.getClass());
            System.out.println(Arrays.toString(arr));
        }
    }

    public void getScore(){}
}