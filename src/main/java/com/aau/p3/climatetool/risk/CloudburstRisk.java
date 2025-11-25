package com.aau.p3.climatetool.risk;

import com.aau.p3.climatetool.utilities.*;
import com.aau.p3.climatetool.utilities.color.ColorManager;
import com.aau.p3.climatetool.utilities.NormalizeSample;
import java.util.List;

/**
 * Class that implements "RiskAssessment" interface and handles the valuation of cloudburst risk
 * Reads information from TIFF files and sets color from normalized measurements
 */
public class CloudburstRisk implements RiskAssessment {
    private final GeoDataReader geoDataReader;
    private final ThresholdRepository thresholdRepository;
    private final MeasurementStrategy measurementStrategy;
    private double measurementValue;
    private double[] threshold;
    private double[] RGBValue;
    private double normalizedMeasurement;
    private String description = "Ingen data tilgængelig";

    /**
     * Constructor for final attributes in CloudburstRisk class
     * @param geoDataReader
     * @param thresholdRepository
     * @param measurementStrategy
     */
    public CloudburstRisk(GeoDataReader geoDataReader, ThresholdRepository thresholdRepository, MeasurementStrategy measurementStrategy) {
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
        List<Double> value = geoDataReader.readValues(coordinates, "bluespot", "SIMRAIN");

        // Use interface methods to get threshold, measurement value, normalized measurement and RGB value.
        this.threshold = thresholdRepository.getThreshold("cloudburst");

        double tempVal = measurementStrategy.processValues(value) * 1000;
        // If process value is NaN - no risk data is found on the property, and the measure value is set to -1
        this.measurementValue = Double.isNaN(tempVal) ? 999.9 : tempVal;

        this.normalizedMeasurement = NormalizeSample.minMaxNormalization(this.measurementValue, this.threshold);
        this.RGBValue = ColorManager.getRGBValues(normalizedMeasurement);
        this.setDescription();
    }

    /**
     * Getter method
     * @return RGB Value
     */
    @Override
    public double[] getRGB() {
        return this.RGBValue;
    }

    /**
     * Getter method
     * @return NormalizedValue for the given risk
     */
    @Override
    public double getNormalizedValue() {
        return this.normalizedMeasurement;
    }

    /**
     * Getter method
     * @return Measurement value
     */
    @Override
    public double getMeasurementValue() {
        return this.measurementValue;
    }

    /**
     * Setter method. Sets description.
     */
    @Override
    public void setDescription() {
        this.description = this.measurementValue == 999.9 ? "Ingen data tilgænglig" : "Som resultat af kraftig regn, skal der " + String.format("%.2f", this.measurementValue) + "mm til for, at oversvømme denne grund.";
    }

    /**
     * Getter method
     * @return description
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * Getter method
     * @return Thresholds
     */
    @Override
    public double[] getThresholds() {
        return this.threshold;
    }

    /**
     * Getter method
     * @return Risk type
     */
    @Override
    public String getRiskType() {
        return "cloudburst";
    }
}