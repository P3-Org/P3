package com.aau.p3.climatetool.risk;

import com.aau.p3.climatetool.geoprocessing.CoastalErosionReader;
import com.aau.p3.climatetool.utilities.MeasurementStrategy;
import com.aau.p3.climatetool.utilities.RiskAssessment;
import com.aau.p3.climatetool.utilities.ThresholdRepositoryInterface;
import com.aau.p3.climatetool.utilities.color.ColorManager;
import com.aau.p3.climatetool.utilities.NormalizeSample;
import java.util.List;

/**
 * Class that implements "RiskAssessment" interface and handles the valuation of coastal erosion risk.
 * Gets information through API call to dataforsyningen.
 */
public class CoastalErosionRisk implements RiskAssessment {
    private final ThresholdRepositoryInterface thresholdRepository;
    private final MeasurementStrategy measurementStrategy;
    private double measurementValue;
    private double[] threshold;
    private double[] RGBValue;
    private double normalizedMeasurement;
    private String description = "Ingen data tilgængelig";
    private String severityString;

    /**
     * Constructor for final attributes
     * @param thresholdRepository the threshold values
     * @param measurementStrategy the strategy for picking measurement
     */
    public CoastalErosionRisk(ThresholdRepositoryInterface thresholdRepository, MeasurementStrategy measurementStrategy) {
        this.thresholdRepository = thresholdRepository;
        this.measurementStrategy = measurementStrategy;
    }

    /**
     * Method for computing the risks of coastal erosion. Uses coordinates to gather information about a properties risk
     * of coastal erosion, which is then handled by RGB and thresholds to decide a severity color.
     * @param coordinates The list of coordinates
     */
    @Override
    public void computeRiskMetrics(double[][] coordinates) {
        CoastalErosionReader reader = new CoastalErosionReader();

        // Get x and y coordinates to perform API call.
        double x = coordinates[0][0];
        double y = coordinates[0][1];

        // Format string for the url string and perform API call
        String wktFormat = String.format(java.util.Locale.US, "%.3f, %.3f", x, y);

        // Get information through coastal erosion classes, with the query of the coordinates.
        reader.riskFetch(wktFormat);

        // Data from coastal erosion inserted into list of doubles
        List<Double> value = reader.getRiskValueArray();

        // Extract the threshold values for coastal erosion.
        this.threshold = thresholdRepository.getThreshold("coastalerosion");

        // Use the measurementStrategy to get the measured value of the risk.
        this.measurementValue = measurementStrategy.processValues(value);

        // Convert the value into string, for representation on page.
        this.severityString = reader.convertValueToString(measurementValue);

        // Get the normalized measurement of the measured value using the thresholds.
        this.normalizedMeasurement = NormalizeSample.minMaxNormalization(this.measurementValue, this.threshold);

        // Assign color according to the normalized measurement value.
        this.RGBValue = ColorManager.getRGBValues(normalizedMeasurement);

        // Write the description for the risk.
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
     * @return NormalizedValue
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
     * Getter method
     * @return Description
     */
    @Override
    public String getDescription() { return this.description; }

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
        return "coastalerosion";
    }

    /**
     * Setter method. Sets description.
     */
    @Override
    public void setDescription() {
        this.description = "Kystændringer i en radius af 250 meter for denne grund vil medføre: " + this.severityString + ".";
    }

}