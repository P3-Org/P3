package com.aau.p3.climatetool.risk;

import com.aau.p3.climatetool.utilities.GeoDataReader;
import com.aau.p3.climatetool.utilities.MeasurementStrategy;
import com.aau.p3.climatetool.utilities.RiskAssessment;
import com.aau.p3.climatetool.utilities.ThresholdRepository;

import java.util.List;

import static com.aau.p3.climatetool.utilities.RiskColorGradient.assignColors;

public class StormSurgeRisk implements RiskAssessment {
    private final GeoDataReader geoDataReader;
    private final ThresholdRepository thresholdRepository;
    private final MeasurementStrategy measurementStrategy;

    public StormSurgeRisk(GeoDataReader geoDataReader, ThresholdRepository thresholdRepository, MeasurementStrategy measurementStrategy) {
        this.geoDataReader = geoDataReader;
        this.thresholdRepository = thresholdRepository;
        this.measurementStrategy = measurementStrategy;
    }

    @Override
    public double[] gatherData(double[][] coordinates) {
        List<Double> value = geoDataReader.readValues(coordinates, "havvand_land", "SIMSEA");
        double[] threshold = thresholdRepository.getThreshold("stormsurge");
        double measurementValue = measurementStrategy.processValues(value);
        return assignColors(measurementValue, threshold);
    }
}