package com.aau.p3.climatetool.geoprocessing;

import com.aau.p3.climatetool.utilities.GeoDataReader;

public class TifGeoDataReader implements GeoDataReader {
    @Override
    public double readValue(double[][] coordinates, String tifDir, String fileTag) {
        return TifFileReader.readGeoData(coordinates, tifDir, fileTag);
    }
}