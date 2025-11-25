package com.aau.p3.climatetool.risk;

import com.aau.p3.Main;
import com.aau.p3.climatetool.geoprocessing.GroundwaterReader;
import com.aau.p3.climatetool.utilities.*;
import com.aau.p3.climatetool.utilities.color.ColorManager;

/**
 * Class that implements "RiskAssessment" interface and handles the valuation of groundwater risk
 * Gets information through API call to dataforsyningen
 */
public class GroundwaterRisk implements RiskAssessment{
    private final ThresholdRepository thresholdRepository;
    private double measurementValue;
    private double[] threshold;
    private double[] RGBValue;
    private double normalizedMeasurement;
    private String description = "Ingen data tilgængelig";
    public double easting;
    public double northing;
    /**
     *  Constructor that initializes the thresholds
     * @param thresholdRepository
     */
    public GroundwaterRisk(ThresholdRepository thresholdRepository) {
        this.thresholdRepository = thresholdRepository;
    }

    /**
     * Method for constructing the different calls necessary to gather sample values from a property within a grid.
     * @param eastnorth The list of coordinates
     * Initializes all fields with the computed information
     */
    @Override
    public void computeRiskMetrics(double[][] eastnorth) {
        // Groundwater object creation
        GroundwaterReader reader = new GroundwaterReader();

        // Get x and y coordinates to perform API call
        this.easting = eastnorth[0][0];
        this.northing = eastnorth[0][1];

        // Format string for the url string and perform API call
        String wkt = String.format(java.util.Locale.US, "%.3f %.3f", easting, northing);
        reader.riskFetch(wkt);

        // Use interface methods to get threshold, measurement value, normalized measurement and RGB value.
        this.measurementValue = reader.getDistanceFromSurface();
        this.threshold = thresholdRepository.getThreshold("groundwater");
        this.normalizedMeasurement = NormalizeSample.minMaxNormalization(measurementValue, threshold);
        this.RGBValue = ColorManager.getRGBValues(normalizedMeasurement);
        this.setDescription();
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
    public void setDescription() {
        this.description = "I tilfælde af en 20-års hændelse, vil grundvandet ligge " + String.format("%.2f", this.measurementValue) + "m fra matriklens overflade.";
    }

    @Override
    public String getDescription() { return this.description; }

    @Override
    public double[] getThresholds() { return this.threshold; }

    @Override
    public String getRiskType() {
        return "groundwater";
    }
}