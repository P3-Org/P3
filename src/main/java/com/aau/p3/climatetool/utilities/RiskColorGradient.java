package com.aau.p3.climatetool.utilities;

public class RiskColorGradient {
    public static double[] assignColors(double maxValue, double[] thresholds) {
        if (maxValue <= thresholds[0]) {
            double green = maxValue/thresholds[0] * 255;
            return new double[] {255,green,0};

        } else if (maxValue>thresholds[0] && maxValue<= thresholds[1]) {
            double red = (1-(maxValue-thresholds[0])/thresholds[0])*255;
            return new double[] {red,255,0};
        }
        else {
            return new double[] {0,255,0};
        }
    }
}