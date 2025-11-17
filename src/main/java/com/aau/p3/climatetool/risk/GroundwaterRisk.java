package com.aau.p3.climatetool.risk;

import com.aau.p3.climatetool.utilities.GeoDataReader;
import com.aau.p3.climatetool.utilities.MeasurementStrategy;
import com.aau.p3.climatetool.utilities.RiskAssessment;
import com.aau.p3.climatetool.utilities.ThresholdRepository;
import com.aau.p3.climatetool.utilities.color.ColorManager;
import com.aau.p3.climatetool.utilities.color.NormalizeSample;

import java.util.List;

public class GroundwaterRisk implements RiskAssessment {
    private final GeoDataReader geoDataReader;
    private final ThresholdRepository thresholdRepository;
    private final MeasurementStrategy measurementStrategy;
    private double measurementValue;
    private double[] threshold;
    private double[] RGBValue;
    private double normalizedMeasurement;

    public GroundwaterRisk(GeoDataReader geoDataReader, ThresholdRepository thresholdRepository, MeasurementStrategy measurementStrategy) {
        this.geoDataReader = geoDataReader;
        this.thresholdRepository = thresholdRepository;
        this.measurementStrategy = measurementStrategy;
    }

    @Override
    public void computeRiskMetrics(double[][] coordinates) {
        // W.I.P!!!!!!
        // Mangler mads' værdier
        List<Double> value = geoDataReader.readValues(coordinates, "bluespot", "SIMRAIN");
        this.threshold = this.thresholdRepository.getThreshold("cloudburst");
        this.measurementValue = measurementStrategy.processValues(value);
        this.normalizedMeasurement = NormalizeSample.minMaxNormalization(this.measurementValue, threshold);
        System.out.println(normalizedMeasurement);
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