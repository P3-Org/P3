package com.aau.p3.climatetool.utilities;

import javafx.scene.control.Slider;

public class ThumbEditor {
    double[] thresholdValues;

    public void setLimits(RiskAssessment risk, Slider thumb) {
        thresholdValues = risk.getThresholds();

        thumb.setMax(NormalizeSample.minMaxNormalization(thresholdValues[0]+thresholdValues[1], thresholdValues));
        thumb.setMin(NormalizeSample.minMaxNormalization(0, thresholdValues));

    }
}
