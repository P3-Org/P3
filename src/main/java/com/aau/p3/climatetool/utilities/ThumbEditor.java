package com.aau.p3.climatetool.utilities;

import javafx.scene.control.Slider;

public class ThumbEditor {
    double[] thresholdValues;

    public void setLimits(RiskAssessment risk, Slider thumb) {
        thresholdValues = risk.getThresholds();

        thumb.setMax(setMaxLimit());
        thumb.setMin(setMinLimit());

    }

    private double setMaxLimit() {
        return (thresholdValues[0]+thresholdValues[1] - thresholdValues[0]) / (thresholdValues[1] - thresholdValues[0]);

    }

    private double setMinLimit(){
        return (0 - thresholdValues[0]) / (thresholdValues[1] - thresholdValues[0]);
    }
}
