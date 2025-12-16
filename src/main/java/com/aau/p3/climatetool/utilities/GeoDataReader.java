package com.aau.p3.climatetool.utilities;

import java.util.List;

/**
 * Interface for reading Geo date values - adds the method readValues().
 */
public interface GeoDataReader {
    List<Double> readValues(double[][] coordinates, String tiffTileDir, String fileTag);
}
