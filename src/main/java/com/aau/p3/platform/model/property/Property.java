package com.aau.p3.platform.model.property;
import com.aau.p3.climatetool.utilities.RiskAssessment;
import java.util.ArrayList;
import java.util.List;
import com.aau.p3.climatetool.ClimateStateScore;

/**
 * Class that holds information on a property, which in "PropertyManager" can be saved in a list.
 * Holds everything from address, location and risks
 */
public class Property {
    private final String address;
    private final List<List<Double>> polygonCoordinates;
    private final List<String> latLongCoordinates;
    private final List<RiskAssessment> riskAssessment;
    private int climateScore;
    private int specialistScore = 0;
    private List<String> comments = new ArrayList<>();

    /**
     * Constructor for property - initializes final variables
     * @param address
     * @param polygonCoordinates
     * @param latLongCoordinates
     * @param riskAssessment
     */
    public Property(String address, List<List<Double>> polygonCoordinates, List<String> latLongCoordinates, List<RiskAssessment> riskAssessment) {
        this.address = address;
        this.polygonCoordinates = polygonCoordinates;
        this.latLongCoordinates = latLongCoordinates;
        this.riskAssessment = riskAssessment;
    }

    /**
     * Calls "computeOverallClimateScore" and assigns to attribute climateScore in property
     */
    public void calculateClimateScore() {
        this.climateScore = ClimateStateScore.computeOverallClimateScore(riskAssessment);
    }

    /**
     * Getter method
     * @return Risk assessment for the property
     */
    public List<RiskAssessment> getRisks() {
        return this.riskAssessment;
    }

    /**
     * Getter method
     * @return Address for the property
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * Getter method
     * @return Polygon coordinates
     */
    public List<List<Double>> getPolygonCoordinates() {
        return this.polygonCoordinates;
    }

    /**
     * Getter method
     * @return climateScore + changes by the specialist in specialistScore
     */
    public int getClimateScore() {
        return climateScore + specialistScore;
    }

    /**
     * Getter method
     * @return Latitude Longitude Coordinates
     */
    public List<String> getLatLongCoordinates() {
        return latLongCoordinates;
    }

    /**
     * Getter method
     * @return specialistScore from property class
     */
    public int getSpecialistScore() {
        return this.specialistScore;
    }

    /**
     * Setter method
     * @param scoreEdit the score that is to be edited or set
     */
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

    public void setComment(String newComment) {
        this.comments.add(newComment);
        System.out.println("Added a comment!");
    }
    public List<String> getComments() {
        return this.comments;
    }
}