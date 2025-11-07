package com.aau.p3.climatetool.geoprocessing;

import com.aau.p3.climatetool.utilities.GeoDataReader;

import java.util.List;

public class TifGeoDataReader implements GeoDataReader {
    @Override
    public List<Double> readValue(double[][] coordinates, String tifDir, String fileTag) {
        return TifFileReader.readGeoData(coordinates, tifDir, fileTag);
    }
}