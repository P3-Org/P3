package com.aau.p3.climatetool.utilities;

// Interface with abstract methods, allows for each risk to get handled appropriately, as they are formated different
public interface RiskAssessment {
    void computeRiskMetrics(double[][] coordinates);
    double[] getRGB();
    double getNormalizedValue();
    double getMeasurementValue();
}