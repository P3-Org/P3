package com.aau.p3.climatetool.risk;

import com.aau.p3.climatetool.utilities.*;
import com.aau.p3.climatetool.utilities.color.ColorManager;

import java.util.List;

/**
 * Class that implements "RiskAssessment" interface and handles the valuation of groundwater risk
 * Reads information from TIFF files and sets color from normalized measurements
 */
public class StormSurgeRisk implements RiskAssessment {
    private final GeoDataReader geoDataReader;
    private final ThresholdRepository thresholdRepository;
    private final MeasurementStrategy measurementStrategy;
    private double measurementValue;
    private double[] threshold;
    private double[] RGBValue;
    private double normalizedMeasurement;
    private String description = "Ingen data tilgængelig";

    /**
     * Constructor for final attributes of StormSurgeRisk
     * @param geoDataReader
     * @param thresholdRepository
     * @param measurementStrategy
     */
    public StormSurgeRisk(GeoDataReader geoDataReader, ThresholdRepository thresholdRepository, MeasurementStrategy measurementStrategy) {
        this.geoDataReader = geoDataReader;
        this.thresholdRepository = thresholdRepository;
        this.measurementStrategy = measurementStrategy;
    }

    /**
     * Method for constructing the different calls necessary to gather sample values from a property within a grid.
     * @param coordinates The list of coordinates
     * Reads values from TIFF files and initializes all fields with the computed information
     */
    @Override
    public void computeRiskMetrics(double[][] coordinates) {
        List<Double> value = geoDataReader.readValues(coordinates, "havvand_land", "SIMSEA");

        // Use interface methods to get threshold, measurement value, normalized measurement and RGB value.
        this.threshold = thresholdRepository.getThreshold("stormsurge");
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

    @Override
    public void setDescription() {}

    @Override
    public String getDescription() {
        return this.description;
    }

    public double[] getThresholds() { return this.threshold; }

    @Override
    public String getRiskType() {
        return "stormsurge";
    }
}