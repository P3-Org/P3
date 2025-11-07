package com.aau.p3.climatetool.utilities;

public class riskColorGradient {


    public static double[] assignColors(double maxValue, double[] thresholds) {

        if (maxValue <= thresholds[0]) {
            double green = maxValue/thresholds[0] * 255;
            return new double[] {255,green,0};

        } else if (maxValue>thresholds[0] && maxValue<= thresholds[1]) {


        }
        return null; //rgb value, needs to adjust to how javaFX reads it */
    }
}


