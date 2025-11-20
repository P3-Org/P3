package com.aau.p3.platform.model.property;
import com.aau.p3.Main;
import com.aau.p3.climatetool.utilities.RiskAssessment;
import java.util.List;
import com.aau.p3.climatetool.ClimateStateScore;
import com.aau.p3.climatetool.utilities.RiskAssessment;
import java.util.List;

/**
 * Class that holds information on a property, which in "PropertyManager" can be saved in a list.
 * Holds everything from address, location and risks
 * @Author Batman
 */
public class Property {
    private final String address;
    private final List<List<Double>> polygonCoordinates;
    private final List<String> latLongCoordinates;
    private final List<RiskAssessment> riskAssessment;
    private int climateScore;
    private int specialistScore = 0;

    // Constructor, initializes final variables.
    public Property(String address, List<List<Double>> polygonCoordinates, List<String> latLongCoordinates, List<RiskAssessment> riskAssessment) {
        this.address = address;
        this.polygonCoordinates = polygonCoordinates;
        this.latLongCoordinates = latLongCoordinates;
        this.riskAssessment = riskAssessment;
    }

    // Calls "computeOverallClimateScore" and assigns to attribute
    public void calculateClimateScore() {
        this.climateScore = ClimateStateScore.computeOverallClimateScore(riskAssessment);
    }

    // Getters and setters
    public List<RiskAssessment> getRisks() {
        return this.riskAssessment;
    }

    public String getAddress() {
        return this.address;
    }

    public List<List<Double>> getPolygonCoordinates() {
        return this.polygonCoordinates;
    }

    public int getClimateScore() {
        return climateScore + specialistScore;
    }

    public List<String> getLatLongCoordinates() {
        return latLongCoordinates;
    }

    public void setSpecialistScore(int scoreEdit) {
        if (scoreEdit > 0) {
            specialistScore = 1;
        } else if (scoreEdit < 0) {
            if (climateScore > 1){
            specialistScore = -1;
            } else {
                specialistScore = 0;
            }
        } else {
            specialistScore = 0;
        }
    }

    public int getSpecialistScore() {
        return this.specialistScore;
    }
  
    public void setComments(String newComment) {
        this.comments.add(newComment);
    }
    public List<String> getComments() {
        return this.comments;
    }
}