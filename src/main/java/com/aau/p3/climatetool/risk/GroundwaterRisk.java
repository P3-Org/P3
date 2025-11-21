package com.aau.p3.climatetool.risk;

import com.aau.p3.Main;
import com.aau.p3.climatetool.geoprocessing.GroundwaterReader;
import com.aau.p3.climatetool.utilities.RiskAssessment;
import com.aau.p3.climatetool.utilities.ThresholdRepository;
import com.aau.p3.climatetool.utilities.color.ColorManager;
import com.aau.p3.climatetool.utilities.NormalizeSample;

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
        System.out.println(easting);
        System.out.println(northing);

        // Format string for the url string and perform API call
        String wkt = String.format(java.util.Locale.US, "%.3f %.3f", easting, northing);
        reader.groundwaterFetch(wkt);

        // Compute and initialize different fields of class
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
        // I tilfæde af en X-års hændelse, vil grundvandet ligge Y meter fra matriklens overflade.
        this.description = "I tilfælde af en 50-års hændelse, vil grundvandet ligge " + String.format("%.2f", this.measurementValue) + " meter fra matriklens overflade.";
    }


    public String getDescription() { return this.description; }

    public double[] getThresholds() { return this.threshold; }


    public String getRiskType() {
        return "groundwater";
    }
}