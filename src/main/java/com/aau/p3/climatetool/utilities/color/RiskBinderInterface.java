package com.aau.p3.climatetool.utilities.color;

import com.aau.p3.climatetool.utilities.RiskAssessment;
import java.util.List;

/**
 * This Interface for risks adds the method for applying colors applyColors()
 */
public interface RiskBinderInterface {
    void applyColors(List<RiskAssessment> riskAssessment, double[][] coordinates);
}
