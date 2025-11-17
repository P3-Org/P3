package com.aau.p3.platform.model.property;
import com.aau.p3.climatetool.utilities.RiskAssessment;
import com.aau.p3.platform.model.common.Address;
import java.util.List;

public class Property {
    private Address address;
    private final double[][] coordinates;
    private final List<RiskAssessment> riskAssessment;

    public Property(double[][] coordinates, List<RiskAssessment> riskAssessment) {
        this.coordinates = coordinates;
        this.riskAssessment = riskAssessment;
    }

    public double[][] getCoordinates() {
        return this.coordinates;
    }

    public List<RiskAssessment> getRisks() {
        return this.riskAssessment;
    }
}