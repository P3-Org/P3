package com.aau.p3.climatetool.utilities;

import javafx.scene.control.Slider;

/**
 * Class that handles the Thumb on the threshold sliders
 */
public class ThumbEditor {
    double[] thresholdValues;

    /**
     * Method for setting the limits(min,max) on the threshold sliders.
     * @param risk the risk assessment we are getting thresholds from.
     * @param thumb the thumb we are assigning min and max to.
     */
    public void setLimits(RiskAssessment risk, Slider thumb) {
        thresholdValues = risk.getThresholds();

        thumb.setMax(NormalizeSample.minMaxNormalization(thresholdValues[0]+thresholdValues[1], thresholdValues));
        thumb.setMin(NormalizeSample.minMaxNormalization(0, thresholdValues));
    }
}
