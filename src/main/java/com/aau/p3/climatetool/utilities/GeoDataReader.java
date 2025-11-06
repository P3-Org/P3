package com.aau.p3.climatetool.utilities;

public interface GeoDataReader {
    double readValue(double[][] coordinates, String tifDir, String fileTag);
}
