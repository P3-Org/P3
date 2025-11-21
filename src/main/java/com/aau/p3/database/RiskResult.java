package com.aau.p3.database;

import com.aau.p3.climatetool.utilities.RiskAssessment;

public class RiskResult implements RiskAssessment {
    private final String riskType;
    private final double measurementValue;
    private final double normalizedValue;
    private final double[] thresholds;
    private final double[] rgb;

    /**
     * Constructor that takes in the dto values and matches it with the object of type RiskAssessments.
     * @param dto contains the data values originally converted from RiskAssessment objects.
     */
    public RiskResult(RiskToDTO dto) {
        this.riskType = dto.riskType;
        this.measurementValue = dto.measurementValue;
        this.normalizedValue = dto.normalizedValue;
        this.thresholds = dto.thresholds;
        this.rgb = dto.RGB;
    }

    // Implement RiskAssessment interface:
    @Override public double[] getRGB() { return rgb; }
    @Override public double getNormalizedValue() { return normalizedValue; }
    @Override public double getMeasurementValue() { return measurementValue; }
    @Override public double[] getThresholds() { return thresholds; }
    @Override public String getRiskType() {
        return this.riskType;
    }

    @Override
    public void computeRiskMetrics(double[][] coordinates) {}

    @Override
    public void setDescription() {}

    @Override
    public String getDescription() { return ""; }
}