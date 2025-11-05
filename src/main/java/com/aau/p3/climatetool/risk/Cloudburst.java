package com.aau.p3.climatetool.risk;

import com.aau.p3.climatetool.geoprocessing.TifFileReader;

import static com.aau.p3.platform.utilities.riskColorGradient.assignColors;

public class Cloudburst {
    public static double[] gatherData(double[][] coordinates) {
        double maxValue = getMaxValue(coordinates);
        double[] threshold = getThresholdValues();
        return assignColors(maxValue, threshold);
    }

    private static double getMaxValue(double[][] coordinates) {
        return TifFileReader.readGeoData(coordinates, "bluespot", "SIMRAIN");
    }

    private static double[] getThresholdValues() {
        // Get data from DB
        return new double[] {15.0, 30.0}; // test values
    }

}