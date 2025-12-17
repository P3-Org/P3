package com.aau.p3.platform.model.property;

import com.aau.p3.climatetool.utilities.RiskAssessment;
import java.util.ArrayList;
import java.util.List;
import com.aau.p3.climatetool.ClimateStateScore;

/**
 * Class that holds information on a property
 */
public class Property {
    private final String address;
    private final List<List<Double>> polygonCoordinates;
    private final List<String> latLongCoordinates;
    private final List<String> eastingNorthing;
    private final List<String> comments = new ArrayList<>();
    private final List<RiskAssessment> riskAssessment;
    private int climateScore;
    private int specialistAdjustment = 0;

    /**
     * Constructor for property
     * @param address of the property
     * @param polygonCoordinates of the property
     * @param eastingNorthing projected coordinate system coordinates for the property
     * @param latLongCoordinates geographic coordinate system coordinates for the property
     * @param riskAssessment list containing the risks
     */
    public Property(String address, List<List<Double>> polygonCoordinates, List<String> eastingNorthing, List<String> latLongCoordinates, List<RiskAssessment> riskAssessment) {
        this.address = address;
        this.polygonCoordinates = polygonCoordinates;
        this.eastingNorthing = eastingNorthing;
        this.latLongCoordinates = latLongCoordinates;
        this.riskAssessment = riskAssessment;
    }

    /**
     * Method for assigning the climate score value calculated in "computeOverallClimateScore" to the associated property
     */
    public void calculateClimateScore() {
        this.climateScore = ClimateStateScore.computeOverallClimateScore(riskAssessment);
    }

    /**
     * Getter method for the risk on the property
     * @return A list of climate risks for the property
     */
    public List<RiskAssessment> getRisks() {
        return this.riskAssessment;
    }

    /**
     * Getter method for the address
     * @return the address for the property
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * Getter method for polygon coordinates
     * @return the polygon coordinates
     */
    public List<List<Double>> getPolygonCoordinates() {
        return this.polygonCoordinates;
    }

    /**
     * Getter method for the climate score of the property
     * @return the latest climateScore for the property (might have been affected by the specialists increasing or decreasing the climate score)
     */
    public int getClimateScore() {
        return climateScore + specialistAdjustment;
    }

    /**
     * Getter method for the geographic coordinates
     * @return Latitude Longitude Coordinates
     */
    public List<String> getLatLongCoordinates() {
        return latLongCoordinates;
    }

    /**
     * Getter method for the specialists score, the score that lies in-between the interval of [-1..1]
     * @return specialistAdjustment for the property
     */
    public int getSpecialistAdjustment() {
        return this.specialistAdjustment;
    }

    /**
     * Apply's the specialist adjustment for the property, adjusting the climate score.
     * The score is constrained to be -1, 0, or +1, and modifies the overall climate score:
     * - +1 increases the score,
     * - -1 decreases it (unless the climate score is +1),
     * - 0 resets the adjustment.
     * @param scoreEdit the adjustment value (-1, 0, or +1)
     */
    public void applySpecialistAdjustment(int scoreEdit) {
        if (scoreEdit > 0) {
            specialistAdjustment = 1; // Increases the climate score by 1
        } else if (scoreEdit < 0) {
            // Decreases the score if climateScore > 1, otherwise resets to 0
            if (climateScore > 1) {
                specialistAdjustment = -1;
            } else {
                specialistAdjustment = 0;
            }
        } else {
            specialistAdjustment = 0;
        }
    }

    /**
     * Sets comment in the property object
     * @param newComment the new comment to be added to property
     */
    public void setComment(String newComment) {
        this.comments.add(newComment);
    }

    /**
     * Gets comment in property object
     * @return comments from property
     */
    public List<String> getComments() {
        return this.comments;
    }
}