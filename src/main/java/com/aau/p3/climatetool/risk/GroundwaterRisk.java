package com.aau.p3.climatetool.risk;

import com.aau.p3.climatetool.geoprocessing.GroundwaterReader;
import com.aau.p3.climatetool.utilities.*;
import com.aau.p3.climatetool.utilities.color.ColorManager;

/**
 * Class that implements "RiskAssessment" interface and handles the valuation of groundwater risk.
 * Gets information through API call to dataforsyningen.
 */
public class GroundwaterRisk implements RiskAssessment{
    private final ThresholdRepositoryInterface thresholdRepository;
    private double measurementValue;
    private double[] threshold;
    private double[] RGBValue;
    private double normalizedMeasurement;
    private String description = "Ingen data tilgængelig";

    // Constructor for final attributes.
    public GroundwaterRisk(ThresholdRepositoryInterface thresholdRepository) {
        this.thresholdRepository = thresholdRepository;
    }

    /**
     * Method for performing the different calls necessary to gather sample values from a property within a grid.
     * Initializes all fields with the computed information.
     * @param eastNorth The list of coordinates
     */
    @Override
    public void computeRiskMetrics(double[][] eastNorth) {
        // Groundwater object creation.
        GroundwaterReader reader = new GroundwaterReader();

        // Get x and y coordinates to perform API call.
        double x = eastNorth[0][0];
        double y = eastNorth[0][1];

        // Format string for the url string and perform API call.
        String wkt = String.format(java.util.Locale.US, "%.3f %.3f", x, y);

        // Get information through coastal erosion classes, with the query of the coordinates.
        reader.riskFetch(wkt);

        // Extract the threshold values for groundwater.
        this.threshold = thresholdRepository.getThreshold("groundwater");

        // Use the measurementStrategy to get the measured value of the risk.
        this.measurementValue = reader.getDistanceFromSurface();

        // Convert the value into string, for representation on page.
        this.normalizedMeasurement = NormalizeSample.minMaxNormalization(measurementValue, threshold);

        // Assign color according to the normalized measurement value.
        this.RGBValue = ColorManager.getRGBValues(normalizedMeasurement);

        // Write the description for the risk.
        this.setDescription();
    }

    /**
     * Getter method.
     * @return An RGB Value.
     */
    @Override
    public double[] getRGB() {
        return this.RGBValue;
    }

    /**
     * Getter method.
     * @return NormalizedValue.
     */
    @Override
    public double getNormalizedValue() {
        return this.normalizedMeasurement;
    }

    /**
     * Getter method.
     * @return Measurement value.
     */
    @Override
    public double getMeasurementValue() { return this.measurementValue; }

    /**
     * Getter method.
     * @return Description.
     */
    @Override
    public String getDescription() { return this.description; }

    /**
     * Getter method.
     * @return Thresholds.
     */
    @Override
    public double[] getThresholds() { return this.threshold; }

    /**
     * Getter method.
     * @return Risk type.
     */
    @Override
    public String getRiskType() {
        return "groundwater";
    }

    /**
     * Setter method that sets the description.
     */
    @Override
    public void setDescription() {
        this.description = "I tilfælde af en 50-års hændelse, vil grundvandet ligge " + String.format("%.2f", this.measurementValue) + " meter fra matriklens overflade.";
    }
}