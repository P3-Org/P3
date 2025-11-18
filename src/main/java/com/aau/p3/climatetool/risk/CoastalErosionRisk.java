package com.aau.p3.climatetool.risk;

import com.aau.p3.climatetool.utilities.GeoDataReader;
import com.aau.p3.climatetool.utilities.RiskAssessment;
import com.aau.p3.climatetool.utilities.ThresholdRepository;

public class CoastalErosionRisk implements RiskAssessment {
    private final GeoDataReader geoDataReader;
    private final ThresholdRepository thresholdRepository;

    public CoastalErosionRisk(GeoDataReader geoDataReader, ThresholdRepository thresholdRepository) {
        this.geoDataReader = geoDataReader;
        this.thresholdRepository = thresholdRepository;
    }

    @Override
    public double[] gatherData(double[][] coordinates) {
        //List<Double> value = geoDataReader.readValues(coordinates, "coastalErosion", "BLAHBLAH");
        //double[] threshold = thresholdRepository.getThreshold("CoastalErosion");
        return new double[] {0, 175, 175};
    }
}