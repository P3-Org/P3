package com.aau.p3.climatetool.risk;

import com.aau.p3.climatetool.utilities.GeoDataReader;
import com.aau.p3.climatetool.utilities.RiskAssessment;
import com.aau.p3.climatetool.utilities.ThresholdRepository;

import static com.aau.p3.climatetool.utilities.riskColorGradient.assignColors;

public class StormSurgeRisk implements RiskAssessment {
    private final GeoDataReader geoDataReader;
    private final ThresholdRepository thresholdRepository;

    public StormSurgeRisk(GeoDataReader geoDataReader, ThresholdRepository thresholdRepository) {
        this.geoDataReader = geoDataReader;
        this.thresholdRepository = thresholdRepository;
    }

    @Override
    public double[] gatherData(double[][] coordinates) {
        double value = geoDataReader.readValue(coordinates, "stormsurge", "BLAHBLAH");
        double[] threshold = thresholdRepository.getThreshold("StormSurge");
        return assignColors(value, threshold);
    }
}

