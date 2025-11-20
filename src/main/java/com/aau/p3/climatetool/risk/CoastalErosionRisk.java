package com.aau.p3.climatetool.risk;

import com.aau.p3.climatetool.utilities.GeoDataReader;
import com.aau.p3.climatetool.utilities.MeasurementStrategy;
import com.aau.p3.climatetool.utilities.RiskAssessment;
import com.aau.p3.climatetool.utilities.ThresholdRepository;
import com.aau.p3.climatetool.utilities.color.ColorManager;
import com.aau.p3.climatetool.utilities.NormalizeSample;

import java.util.List;
/**
 * Class that implements "RiskAssessment" interface and handles the valuation of groundwater risk
 *
 * @Author Batman
 */
public class CoastalErosionRisk implements RiskAssessment {
    private final GeoDataReader geoDataReader;
    private final ThresholdRepository thresholdRepository;
    private final MeasurementStrategy measurementStrategy;
    private double measurementValue;
    private double[] threshold;
    private double[] RGBValue;
    private double normalizedMeasurement;

    // Constructor for final attributes
    public CoastalErosionRisk(GeoDataReader geoDataReader, ThresholdRepository thresholdRepository, MeasurementStrategy measurementStrategy) {
        this.geoDataReader = geoDataReader;
        this.thresholdRepository = thresholdRepository;
        this.measurementStrategy = measurementStrategy;
    }

    /**
     * Method for constructing the different calls necessary to gather sample values from a property within a grid.
     * @param coordinates The list of coordinates
     * Initializes all fields with the computed information
     */
    @Override
    public void computeRiskMetrics(double[][] coordinates) {
        List<Double> value = geoDataReader.readValues(coordinates, "bluespot", "SIMRAIN");

        // Compute and initialize different fields of class
        this.threshold = thresholdRepository.getThreshold("coastalerosion");
        this.measurementValue = measurementStrategy.processValues(value);
        this.normalizedMeasurement = NormalizeSample.minMaxNormalization(this.measurementValue, this.threshold);
        this.RGBValue = ColorManager.getRGBValues(normalizedMeasurement);
    }

    // Getters
    @Override
    public double[] getRGB() {
        return this.RGBValue;
    }

    @Override
    public double getNormalizedValue() {
        return this.normalizedMeasurement;
    }

    @Override
    public double getMeasurementValue() { return this.measurementValue; }

}