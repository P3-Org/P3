package com.aau.p3.climatetool.utilities;

import com.aau.p3.database.StaticThresholdRepository;

public class IndicatorScaling {
    private double[] thresholdValues;
    public double scaleToIndicator(String risk, double value){
        ThresholdRepository thresholdRepo = new StaticThresholdRepository();
        thresholdValues = thresholdRepo.getThreshold(risk);

        return value/(thresholdValues[0]+thresholdValues[1]);
    }
}
