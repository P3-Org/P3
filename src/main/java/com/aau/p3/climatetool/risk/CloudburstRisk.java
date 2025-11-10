package com.aau.p3.climatetool.risk;

import com.aau.p3.climatetool.utilities.GeoDataReader;
import com.aau.p3.climatetool.utilities.MeasurementStrategy;
import com.aau.p3.climatetool.utilities.RiskAssessment;
import com.aau.p3.climatetool.utilities.ThresholdRepository;

import java.util.List;

import static com.aau.p3.climatetool.utilities.RiskColorGradient.assignColors;

public class CloudburstRisk implements RiskAssessment {
    private final GeoDataReader geoDataReader;
    private final ThresholdRepository thresholdRepository;
    private final MeasurementStrategy measurementStrategy;

    public CloudburstRisk(GeoDataReader geoDataReader, ThresholdRepository thresholdRepository, MeasurementStrategy measurementStrategy) {
        this.geoDataReader = geoDataReader;
        this.thresholdRepository = thresholdRepository;
        this.measurementStrategy = measurementStrategy;
    }

    @Override
    public double[] gatherData(double[][] coordinates) {
        List<Double> value = geoDataReader.readValues(coordinates, "bluespot", "SIMRAIN");
        double[] threshold = thresholdRepository.getThreshold("Cloudburst");
        double measurementValue = measurementStrategy.processValues(value);
        return assignColors(measurementValue, threshold);
    }
}