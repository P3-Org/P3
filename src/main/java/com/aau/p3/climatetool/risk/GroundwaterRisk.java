package com.aau.p3.climatetool.risk;

import com.aau.p3.climatetool.dawa.DawaAutocomplete;
import com.aau.p3.climatetool.dawa.DawaPropertyNumbers;
import com.aau.p3.climatetool.geoprocessing.GroundwaterReader;
import com.aau.p3.climatetool.utilities.RiskAssessment;
import com.aau.p3.climatetool.utilities.ThresholdRepository;
import com.aau.p3.climatetool.utilities.color.ColorManager;
import com.aau.p3.climatetool.utilities.NormalizeSample;

public class GroundwaterRisk implements RiskAssessment {
    private final ThresholdRepository thresholdRepository;
    private double measurementValue;
    private double[] threshold;
    private double[] RGBValue;
    private double normalizedMeasurement;

    public GroundwaterRisk(ThresholdRepository thresholdRepository) {
        this.thresholdRepository = thresholdRepository;
    }

    @Override
    public void computeRiskMetrics(double[][] coordinates) {
        GroundwaterReader reader = new GroundwaterReader();
        double x = coordinates[0][0];
        double y = coordinates[0][1];
        String wkt = String.format(java.util.Locale.US, "%.3f %.3f", x, y);
        reader.groundwaterFetch(wkt);
        this.measurementValue = reader.getDistanceFromSurface();
        this.threshold = thresholdRepository.getThreshold("groundwater");
        this.normalizedMeasurement = NormalizeSample.minMaxNormalization(measurementValue, threshold);
        this.RGBValue = ColorManager.getRGBValues(measurementValue);
    }

    @Override
    public double[] getRGB() {
        return this.RGBValue;
    }

    @Override
    public double getNormalizedValue() {
        return this.normalizedMeasurement;
    }
}