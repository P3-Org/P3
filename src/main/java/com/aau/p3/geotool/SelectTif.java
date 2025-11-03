package com.aau.p3.geotool;

import java.util.ArrayList;
import java.util.List;

public class SelectTif {
    /**
     * Coordinates for the polygon:
     * [550900.0, 6320500.0], BOTTOM-left
     * [551100.0, 6320500.0], TOP-right
     * [551100.0, 6320600.0],
     * [550900.0, 6320600.0],
     * [550900.0, 6320500.0]  BOTTOM-left

     */

    public static void main(String[] args) {
        // Redundant, only for testing
        double[][] coordinates = {{550900.0, 6320500.0},
                                  {551100.0, 6320500.0},
                                  {551100.0, 6320600.0},
                                  {550900.0, 6320600.0}};

        List<String> listOfTifFiles = getFileArray(coordinates);
        System.out.println(listOfTifFiles);
    }

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