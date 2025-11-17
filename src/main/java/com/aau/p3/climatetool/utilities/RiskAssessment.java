package com.aau.p3.climatetool.utilities;

public interface RiskAssessment {
    void computeRiskMetrics(double[][] coordinates);
    double[] getRGB();
    double getNormalizedValue();
}