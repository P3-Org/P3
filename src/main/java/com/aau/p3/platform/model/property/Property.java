package com.aau.p3.platform.model.property;
import com.aau.p3.Main;
import com.aau.p3.climatetool.utilities.RiskAssessment;

import java.util.List;

public class Property {
    private final String address;
    private final List<List<Double>> polygonCoordinates;
    private final List<String> latLongCoordinates;
    private final List<RiskAssessment> riskAssessment;
    //private List<RiskAssessment> riskAssessment;

    public Property(String address, List<List<Double>> polygonCoordinates, List<String> latLongCoordinates, List<RiskAssessment> riskAssessment) {
        this.address = address;
        this.polygonCoordinates = polygonCoordinates;
        this.latLongCoordinates = latLongCoordinates;
        this.riskAssessment = riskAssessment;
    }

    public List<RiskAssessment> getRisks() {
        return this.riskAssessment;
    }

    public String getAddress() {
        return this.address;
    }

    public List<List<Double>> getPolygonCoordinates() {
        return this.polygonCoordinates;
    }

    public List<String> getLatLongCoordinates() {
        return latLongCoordinates;
    }
}