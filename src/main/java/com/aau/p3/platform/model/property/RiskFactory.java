package com.aau.p3.platform.model.property;

import com.aau.p3.climatetool.risk.CloudburstRisk;
import com.aau.p3.climatetool.risk.CoastalErosionRisk;
import com.aau.p3.climatetool.risk.GroundwaterRisk;
import com.aau.p3.climatetool.risk.StormSurgeRisk;
import com.aau.p3.climatetool.strategy.AverageMeasurementStrategy;
import com.aau.p3.climatetool.strategy.MaxMeasurementStrategy;
import com.aau.p3.climatetool.strategy.MinMeasurementStrategy;
import com.aau.p3.climatetool.utilities.GeoDataReader;
import com.aau.p3.climatetool.utilities.RiskAssessment;
import com.aau.p3.climatetool.utilities.ThresholdRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory class for creating risk assessments, returned as a list
 */
public class RiskFactory {
    private final GeoDataReader geoReader;
    private final ThresholdRepository thresholdRepo;

    /**
     * Constructor for final fields in RiskFactory class
     * @param geoReader reader for geo data
     * @param thresholdRepo reader for database thresholds
     */
    public RiskFactory(GeoDataReader geoReader, ThresholdRepository thresholdRepo) {
        this.geoReader = geoReader;
        this.thresholdRepo = thresholdRepo;
    }

    /**
     * Method that adds the different risks to the list of risk assessments. Each is called with the geodata reader,
     * thresholds and for some, either average/max measurement strategy.
     * @param polygonCoordinates The polygon coordinates of the property
     * @param eastingNorthing easting and northing coordinates of the property
     * @return List of risk assessments.
     */
    public List<RiskAssessment> createRisks(double[][] polygonCoordinates, List<String> eastingNorthing) {
        // ArrayList of RiskAssessments that will contain the risks for the property
        List<RiskAssessment> riskAssessments = new ArrayList<>();

        /* Adds a risk to the list of risks. All risks include the same information and follows the Liskov Substitution Principle */
        riskAssessments.add(new CloudburstRisk(geoReader, thresholdRepo, new AverageMeasurementStrategy()));
        riskAssessments.add(new GroundwaterRisk(thresholdRepo));
        riskAssessments.add(new StormSurgeRisk(geoReader, thresholdRepo, new AverageMeasurementStrategy()));
        riskAssessments.add(new CoastalErosionRisk(thresholdRepo, new MinMeasurementStrategy()));

        // These projected coordinates are used later to be able to calculate the values of coastal erosion and groundwater
        double easting  = Double.parseDouble(eastingNorthing.get(0));
        double northing = Double.parseDouble(eastingNorthing.get(1));
        double[][] eastNorth = new double[1][2];
        eastNorth[0][0] = easting;
        eastNorth[0][1] = northing;

        // Computational method for each risk is called to compute the values of the climate risk
        riskAssessments.get(0).computeRiskMetrics(polygonCoordinates);
        riskAssessments.get(1).computeRiskMetrics(eastNorth);
        riskAssessments.get(2).computeRiskMetrics(polygonCoordinates);
        riskAssessments.get(3).computeRiskMetrics(eastNorth);

        return riskAssessments;
    }
}