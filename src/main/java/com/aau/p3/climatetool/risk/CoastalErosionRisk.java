package com.aau.p3.climatetool.risk;

import com.aau.p3.climatetool.geoprocessing.CoastalErosionReader;
import com.aau.p3.climatetool.utilities.MeasurementStrategy;
import com.aau.p3.climatetool.utilities.RiskAssessment;
import com.aau.p3.climatetool.utilities.ThresholdRepository;
import com.aau.p3.climatetool.utilities.color.ColorManager;
import com.aau.p3.climatetool.utilities.NormalizeSample;
import java.util.List;

/**
 * Class that implements "RiskAssessment" interface and handles the valuation of coastal erosion risk
 */
public class CoastalErosionRisk implements RiskAssessment {
    private final ThresholdRepository thresholdRepository;
    private final MeasurementStrategy measurementStrategy;
    private double measurementValue;
    private double[] threshold;
    private double[] RGBValue;
    private double normalizedMeasurement;

    // Constructor for final attributes
    public CoastalErosionRisk(ThresholdRepository thresholdRepository, MeasurementStrategy measurementStrategy) {
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
        reader.coastalErosionFetch(wktFormat);

        // Data from coastal erosion inserted into list of doubles
        List<Double> value = reader.getRiskValueArray();

        // Use interface methods to get threshold, measurement value, normalized measurement and RGB value.
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
    public double getMeasurementValue() {
        return this.measurementValue;
    }

    @Override
    public double[] getThresholds() {
        return this.threshold;
    }

}