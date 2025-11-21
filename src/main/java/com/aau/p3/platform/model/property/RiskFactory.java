package com.aau.p3.platform.model.property;

import com.aau.p3.climatetool.risk.CloudburstRisk;
import com.aau.p3.climatetool.risk.CoastalErosionRisk;
import com.aau.p3.climatetool.risk.GroundwaterRisk;
import com.aau.p3.climatetool.risk.StormSurgeRisk;
import com.aau.p3.climatetool.strategy.AverageMeasurementStrategy;
import com.aau.p3.climatetool.strategy.MinMeasurementStrategy;
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

    // Constructor for final fields
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
    public List<RiskAssessment> createRisks(double[][] coordinates) {
        List<RiskAssessment> riskAssessments = new ArrayList<>();

        /* Adds a risk to the list of risks. All risks include the same information and follows the Liskov Substitution Principle */
        riskAssessments.add(new CloudburstRisk(geoReader, thresholdRepo, new AverageMeasurementStrategy()));
        riskAssessments.add(new GroundwaterRisk(thresholdRepo));
        riskAssessments.add(new CoastalErosionRisk(thresholdRepo, new MinMeasurementStrategy()));
        riskAssessments.add(new StormSurgeRisk(geoReader, thresholdRepo, new AverageMeasurementStrategy()));

        // For each risk, use the appropriate function for making the assessment.
        for (RiskAssessment risks : riskAssessments) {
            risks.computeRiskMetrics(coordinates);
        }

        return riskAssessments;
    }
}
