package com.aau.p3.climatetool.geoprocessing;

import java.util.ArrayList;
import java.util.List;

public class SelectTiff {
    /**
     * Takes coordinates from GeoJSON polygon, and returns all 1x1 km tif files, the polygon covers.
     * @param coordinatesArray 2D double array
     * @return List of filenames covered by the polygon
     */
    public static List<String> getFileArray(double[][] coordinatesArray, String fileTag) {
        List<String> listOfTifFiles = new ArrayList<>();

        for (double[] coordinatePair : coordinatesArray) {
            String name = getFileName(coordinatePair, fileTag);
            if (!(listOfTifFiles.contains(name))) {
                listOfTifFiles.add(name);
            }
        }

        return listOfTifFiles;
    }

    /**
     * Helper method used to find tif files corresponding to the given coordinates given.
     * @param coordinates double array
     * @return tif file name
     */
    private static String getFileName(double[] coordinates, String fileTag) {
        int xCoordinates = (int) (coordinates[0] / 1000);
        int yCoordinates = (int) (coordinates[1] / 1000);
        return fileTag + "_1km_" + yCoordinates + "_" + xCoordinates + ".tif";
    }
}