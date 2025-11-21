package com.aau.p3.platform.model.property;

import com.aau.p3.climatetool.risk.CloudburstRisk;
import com.aau.p3.climatetool.risk.CoastalErosionRisk;
import com.aau.p3.climatetool.risk.GroundwaterRisk;
import com.aau.p3.climatetool.risk.StormSurgeRisk;
import com.aau.p3.climatetool.strategy.AverageMeasurementStrategy;
import com.aau.p3.climatetool.strategy.MaxMeasurementStrategy;
import com.aau.p3.climatetool.utilities.GeoDataReader;
import com.aau.p3.climatetool.utilities.RiskAssessment;
import com.aau.p3.climatetool.utilities.ThresholdRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory function for creating risk assessments, returned as a list.
 */
public class RiskFactory {
    private final GeoDataReader geoReader;
    private final ThresholdRepository thresholdRepo;

    /**
     * Constructor for final fields in RiskFactory class
     * @param geoReader reader for geo data
     * @param thresholdRepo thresholds
     */
    public RiskFactory(GeoDataReader geoReader, ThresholdRepository thresholdRepo) {
        this.geoReader = geoReader;
        this.thresholdRepo = thresholdRepo;
    }

    /**
     * Method that adds the different risks to the list of risk assessments. Each is called with the geodata reader,
     * thresholds and for some, either average/max measurement strategy
     * @param coordinates The coordinates of the property
     * @return List of risk assessments.
     */
    public List<RiskAssessment> createRisks(double[][] coordinates, List<String> xy) {
        List<RiskAssessment> riskAssessments = new ArrayList<>();

        /* Adds a risk to the list of risks. All risks include the same information and follows the Liskov Substitution Principle */
        riskAssessments.add(new CloudburstRisk(geoReader, thresholdRepo, new AverageMeasurementStrategy()));
        riskAssessments.add(new GroundwaterRisk(thresholdRepo));
        riskAssessments.add(new StormSurgeRisk(geoReader, thresholdRepo, new AverageMeasurementStrategy()));
        riskAssessments.add(new CoastalErosionRisk(geoReader, thresholdRepo, new MaxMeasurementStrategy()));

        // For each risk, use the appropriate function for making the assessment.
        //for (RiskAssessment risks : riskAssessments) {
        //    risks.computeRiskMetrics(coordinates);
        //}
        // bad workaround because of bad interfaces and uses of bad double[][]........
        double easting  = Double.parseDouble(xy.get(0)); // correct order
        double northing = Double.parseDouble(xy.get(1));
        double[][] eastnorth = new double[1][2];
        eastnorth[0][0] = easting;
        eastnorth[0][1] = northing;
        System.out.println("riskfactory" + easting);
        System.out.println("riskfactory" +  northing);
        System.out.println("coordinates" + coordinates);

        riskAssessments.get(0).computeRiskMetrics(coordinates);
        riskAssessments.get(1).computeRiskMetrics(eastnorth);
        riskAssessments.get(2).computeRiskMetrics(coordinates);
        riskAssessments.get(3).computeRiskMetrics(coordinates);

        return riskAssessments;
    }
}