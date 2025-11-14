package com.aau.p3.climatetool.utilities.color;

import com.aau.p3.climatetool.utilities.RiskAssessment;

import java.util.List;

public interface RiskBinderInterface {
    void applyColors(List<RiskAssessment> riskAssessment, double[][] coordinates);
}
