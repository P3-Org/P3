package com.aau.p3.climatetool.geoprocessing;

import java.util.ArrayList;
import java.util.List;

/**
 * Class responsible for selecting the desired tiff files that is available at the server containing geotiff files from Dataforsyningen
 */
public class SelectTiff {

    /**
     * Takes coordinates from GeoJSON polygon, and returns all 1x1 km tiff files, the polygon covers.
     * @param coordinatesArray 2D double array
     * @return List of filenames covered by the polygon
     */
    public static List<String> getFileArray(double[][] coordinatesArray, String fileTag) {
        List<String> listOfTiffFiles = new ArrayList<>();

        // Iterates the coordinates and call the method getFileName() to locate the relevant tiff files. Add them to the list
        for (double[] coordinatePair : coordinatesArray) {
            String name = getFileName(coordinatePair, fileTag);
            if (!(listOfTiffFiles.contains(name))) {
                listOfTiffFiles.add(name);
            }
        }
        return listOfTiffFiles;
    }

    /**
     * Helper method used to complete the entire file name of the tiff files.
     * @param coordinates double array
     * @param fileTag string that specifies the prefix of geotiff file naming e.g. "SIMRAIN"
     * @return full tiff file name matching the naming from Dataforsyningen
     */
    private static String getFileName(double[] coordinates, String fileTag) {
        int xCoordinates = (int) (coordinates[0] / 1000);
        int yCoordinates = (int) (coordinates[1] / 1000);
        return fileTag + "_1km_" + yCoordinates + "_" + xCoordinates + ".tif";
    }
}