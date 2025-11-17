package com.aau.p3.platform.model.property;

import com.aau.p3.climatetool.risk.CloudburstRisk;
import com.aau.p3.climatetool.risk.CoastalErosionRisk;
import com.aau.p3.climatetool.risk.GroundwaterRisk;
import com.aau.p3.climatetool.risk.StormSurgeRisk;
import com.aau.p3.climatetool.strategy.MaxMeasurementStrategy;
import com.aau.p3.climatetool.strategy.MinMeasurementStrategy;
import com.aau.p3.climatetool.utilities.GeoDataReader;
import com.aau.p3.climatetool.utilities.RiskAssessment;
import com.aau.p3.climatetool.utilities.ThresholdRepository;

import java.util.ArrayList;
import java.util.List;

public class RiskFactory {
    private final GeoDataReader geoReader;
    private final ThresholdRepository thresholdRepo;

    public RiskFactory(GeoDataReader geoReader, ThresholdRepository thresholdRepo) {
        this.geoReader = geoReader;
        this.thresholdRepo = thresholdRepo;
    }

    public List<RiskAssessment> createRisks(double[][] coordinates) {
        List<RiskAssessment> riskAssessments = new ArrayList<>();

        /* Adds a risk to the list of risks. All risks include the same information and follows the Liskov Substitution Principle */
        riskAssessments.add(new CloudburstRisk(geoReader, thresholdRepo, new MaxMeasurementStrategy()));
        riskAssessments.add(new GroundwaterRisk(geoReader, thresholdRepo, new MinMeasurementStrategy()));
        riskAssessments.add(new CoastalErosionRisk(geoReader, thresholdRepo, new MaxMeasurementStrategy()));
        riskAssessments.add(new StormSurgeRisk(geoReader, thresholdRepo, new MaxMeasurementStrategy()));



        return riskAssessments;
    }
}
