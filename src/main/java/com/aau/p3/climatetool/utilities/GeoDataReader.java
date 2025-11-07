package com.aau.p3.climatetool.utilities;

import java.util.List;

public interface GeoDataReader {
    List<Double> readValue(double[][] coordinates, String tifDir, String fileTag);
}
