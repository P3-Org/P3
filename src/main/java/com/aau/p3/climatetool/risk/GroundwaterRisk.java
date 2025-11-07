package com.aau.p3.climatetool.risk;

import com.aau.p3.climatetool.utilities.GeoDataReader;
import com.aau.p3.climatetool.utilities.RiskAssessment;
import com.aau.p3.climatetool.utilities.ThresholdRepository;

import java.util.List;

import static com.aau.p3.climatetool.utilities.riskColorGradient.assignColors;

public class GroundwaterRisk implements RiskAssessment {
    private final GeoDataReader geoDataReader;
    private final ThresholdRepository thresholdRepository;

    public GroundwaterRisk(GeoDataReader geoDataReader, ThresholdRepository thresholdRepository) {
        this.geoDataReader = geoDataReader;
        this.thresholdRepository = thresholdRepository;
    }

    @Override
    public double[] gatherData(double[][] coordinates) {
        List<Double> value = geoDataReader.readValue(coordinates, "groundwater", "BLAH");
        double[] threshold = thresholdRepository.getThreshold("Groundwater");
        return new double[] {255, 255, 0};
    }
}