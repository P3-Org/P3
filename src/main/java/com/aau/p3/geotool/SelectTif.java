package com.aau.p3.geotool;

import java.util.ArrayList;
import java.util.List;

public class SelectTif {
    /**
     * Takes coordinates from GeoJSON polygon, and returns all 1x1 km tif files, the polygon covers.
     * @param coordinatesArray 2D double array
     * @return List of filenames covered by the polygon
     */
    public static List<String> getFileArray(double[][] coordinatesArray) {
        List<String> listOfTifFiles = new ArrayList<>();

        for (double[] coordinatePair : coordinatesArray) {
            String name = getFileName(coordinatePair);
            if (!(listOfTifFiles.contains(name))) {
                listOfTifFiles.add(name);
            }
        }

        return listOfTifFiles;
    }

    /**
     * Method used to find tif files corresponding to the given coordinates given.
     * @param coordinates double array
     * @return tif file name
     */
    public static String getFileName(double[] coordinates) {
        int xCoordinates = (int) (coordinates[0] / 1000);
        int yCoordinates = (int) (coordinates[1] / 1000);
        return "SIMRAIN_1km_" + yCoordinates + "_" + xCoordinates + ".tif";
    }
}