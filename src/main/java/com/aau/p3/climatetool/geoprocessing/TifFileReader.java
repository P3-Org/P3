package com.aau.p3.climatetool.geoprocessing;

import org.geotools.api.referencing.operation.TransformException;
import org.geotools.coverage.grid.GridCoverage2D;
import java.io.IOException;
import java.util.List;
import static com.aau.p3.climatetool.geoprocessing.SelectTif.getFileArray;

public class TifFileReader {

    /**
     * Method for reading geo data based on any type of tif file.
     * @param coordinates The EPSG:25832 coordinates for the polygons corners
     * @param tifTileDir The directory containing the relevant tif files
     * @param fileTag The file tag based on the specific climate risk i.e. "SIMRAIN", "DTM"...
     * @return
     */
    public static double readGeoData(double[][] coordinates, String tifTileDir, String fileTag) {
        try {
            /* Locate which tif files that the coordinates fall in to */
            List<String> listOfTifFiles = getFileArray(coordinates, fileTag);

            /* Instantiates a tif tile object that will create the coverage grid based on the tif files */
            TifTile tile = new TifTile(tifTileDir, listOfTifFiles);
            tile.load();
            GridCoverage2D gc = tile.getCoverage();

            /* Gathers the max value from the tif file that is within the bounds of the polygon (property).
            *  Then return it */
            FindMaxValue maxValueOfProperty = new FindMaxValue();
            double maxVal = maxValueOfProperty.getMaxValueInPolygon(gc, coordinates);

            // delete the sout later on (currently just for testing)
            System.out.println("EASTING: " + maxValueOfProperty.getEasting() + " NORTHING: " + maxValueOfProperty.getNorthing());
            System.out.println("Max value of rain on property: " + maxVal + " mm/day");
            return maxVal;
        } catch (IOException | TransformException e) {
            throw new RuntimeException(e);
        }
    }
}