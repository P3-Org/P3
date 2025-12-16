package com.aau.p3.climatetool.risk;

import com.aau.p3.climatetool.utilities.*;
import com.aau.p3.climatetool.utilities.color.ColorManager;
import java.util.List;

/**
 * Class that implements "RiskAssessment" interface and handles the valuation of stormsurge risk.
 * Reads information from TIFF files and sets color from normalized measurements.
 */
public class StormSurgeRisk implements RiskAssessment {
    private final GeoDataReader geoDataReader;
    private final ThresholdRepositoryInterface thresholdRepository;
    private final MeasurementStrategy measurementStrategy;
    private double measurementValue;
    private double[] threshold;
    private double[] RGBValue;
    private double normalizedMeasurement;
    private String description = "Ingen data tilgængelig";

    /**
     * Constructor for final attributes in CloudburstRisk class
     * @param geoDataReader method of reading values
     * @param thresholdRepository threshold values
     * @param measurementStrategy method of choosing measurements
     */
    public StormSurgeRisk(GeoDataReader geoDataReader, ThresholdRepositoryInterface thresholdRepository, MeasurementStrategy measurementStrategy) {
        this.geoDataReader = geoDataReader;
        this.thresholdRepository = thresholdRepository;
        this.measurementStrategy = measurementStrategy;
    }

    /**
     * Method for constructing the different calls necessary to gather sample values from a property within a grid.
     * Reads values from TIFF files and initializes all fields with the computed information.
     * @param coordinates The list of coordinates.
     */
    @Override
    public void computeRiskMetrics(double[][] coordinates) {
        // Read and assign values from the file SIMSEA, with the given coordinates.
        List<Double> value = geoDataReader.readValues(coordinates, "havvand_land", "SIMSEA");

        // Extract the threshold values for stormsurge.
        this.threshold = thresholdRepository.getThreshold("stormsurge");

        // Temporary measured value.
        double tempVal = measurementStrategy.processValues(value);

        /* If process value is NaN - no risk data is found on the property the measure value is set to 999.9.
         *  This is done if a property doesn't contain any data of storm surge risk the label container will be green */
        this.measurementValue = Double.isNaN(tempVal) ? 999.9 : tempVal;

        // Get the normalized measurement of the measured value using the thresholds.
        this.normalizedMeasurement = NormalizeSample.minMaxNormalization(this.measurementValue, this.threshold);

        // Assign color according to the normalized measurement value.
        this.RGBValue = ColorManager.getRGBValues(normalizedMeasurement);

        // Write the description for the risk.
        this.setDescription();
    }

    /**
     * Getter method.
     * @return RGB Value.
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
    public String getDescription() {
        return this.description;
    }

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
        return "stormsurge";
    }

    /**
     * Setter method that sets the description.
     */
    @Override
    public void setDescription() {
        this.description = this.measurementValue == 999.9 ? "Ingen data tilgænglig" : "Som resultat af stormflod, skal havniveauet stige med " + String.format("%.2f", this.measurementValue) + " meter for, at oversvømme denne grund.";
    }
}