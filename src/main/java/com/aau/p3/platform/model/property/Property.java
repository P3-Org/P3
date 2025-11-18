package com.aau.p3.platform.model.property;
import com.aau.p3.climatetool.utilities.RiskAssessment;
import com.aau.p3.platform.model.common.Address;
import java.util.List;

public class Property {
    private Address address;
    private final double[][] polygonCoordinates;
    private final List<RiskAssessment> riskAssessment;
    private int BFEValue;

    public Property(double[][] coordinates, List<RiskAssessment> riskAssessment) {
        this.polygonCoordinates = coordinates;
        this.riskAssessment = riskAssessment;
    }

    public double[][] getCoordinates() {
        return this.polygonCoordinates;
    }

    public List<RiskAssessment> getRisks() {
        return this.riskAssessment;
    }

    public int getBFEValue() {
        return this.BFEValue;
    }

    public void setBFE(int BFE) {
        this.BFEValue = BFE;
    }
}
