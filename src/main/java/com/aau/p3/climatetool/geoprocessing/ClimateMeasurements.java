package com.aau.p3.climatetool.geoprocessing;

import org.geotools.api.referencing.operation.MathTransform2D;
import org.geotools.api.referencing.operation.TransformException;
import org.geotools.coverage.grid.GridCoordinates2D;
import org.geotools.coverage.grid.GridCoverage2D;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;

import java.util.ArrayList;
import java.util.List;

public class ClimateMeasurements {
    private final GeometryFactory gf = new GeometryFactory();

    /**
     * Method for constructing the different calls necessary to gather sample values from a property within a grid.
     * @param coverage Raster grid from the tif file. Holds geospatial metadata and transform capabilities from pixels to real world coordinates (i.e. EPSG:25832)
     * @param worldCoords The EPSG:25832 coordinates for the polygons corners from DAWA
     * @return a List of doubles containing the sampled data
     * @throws TransformException if error occurs, throw up the stack
     */
    public List<Double> getMeasurements(GridCoverage2D coverage, double[][] worldCoords) throws TransformException {
        Polygon property = gf.createPolygon(buildCoordinate(worldCoords));
        return samplePixels(coverage, gf, property);
    }

    /**
     * Methods for converting worldCoords of type double[][] to a Coordinate[] array.
     * @param worldCoords The EPSG:25832 coordinates for the polygons corners from DAWA
     * @return an array of type Coordinate
     */
    private Coordinate[] buildCoordinate(double[][] worldCoords){
        Coordinate[] coords = new Coordinate[worldCoords.length + 1]; // +1 since we want to add an extra closing coordinate

        /* Convert the double values for the world coordinates to the datatype Coordinate used by the framework locationtech */
        for (int i = 0; i < worldCoords.length; i++) {
            coords[i] = new Coordinate(worldCoords[i][0], worldCoords[i][1]);
        }

        /* Closes the polygon and creates it based on the coords */
        coords[worldCoords.length] = coords[0];
        return coords;
    }

    /**
     * Method for sampling over a grid of pixels to achieve a list of sample values.
     * @param coverage Raster grid from the tif file. Holds geospatial metadata and transform capabilities from pixels to real world coordinates (i.e. EPSG:25832)
     * @param gf Geometry Factory used to create points that will be checked if inside the property lines. Created in {@code getMeasurrements()}
     * @param property polygon shape from the framework locationtech. Created in {@code getMeasurrements()}
     * @return a list of sample values within the property lines
     * @throws TransformException if error occurs, throw up the stack
     */
    private List<Double> samplePixels(GridCoverage2D coverage, GeometryFactory gf, Polygon property) throws TransformException {
        /* Prepare transforms so we can go from pixels to world coordinates and the other way as well */
        MathTransform2D gridToCRS = coverage.getGridGeometry().getGridToCRS2D();

        /* Gets the width and height of the coverage such that we can iterate over it */
        int width = coverage.getRenderedImage().getWidth();
        int height = coverage.getRenderedImage().getHeight();

        /* Keep track of the max value and the east and north coordinates in these variables.
         * Also keeps track of the sample double array that will hold the samples gathered from the tif files. */
        List<Double> values = new ArrayList<>();
        double[] sample = new double[1];

        /* Since the polygon "property" is defined by coordinates, we want to get the coordinate
         *  corresponding to every pixel. This then allows us to check which coordinates are within the polygon
         *  This search could in theory be optimized by starting the conversion and check closer to the start of the polygon - but we don't do that */
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                /* Converts the pixel center to world coordinates */
                double[] gridPt = {x + 0.5, y + 0.5};
                double[] worldPt = new double[2];

                /* .transform() takes gridPt which corresponds to a pixel, and updates worldPt with the corresponding coordinate */
                gridToCRS.transform(gridPt, 0, worldPt, 0, 1);

                /* .contains() Check if this pixel’s world coordinate lies inside the polygon */
                Point p = gf.createPoint(new Coordinate(worldPt[0], worldPt[1]));
                if (property.contains(p)) {
                    try {
                        /* .evaluate() finds the measurement associated with a coordinate and stores it in sample */
                        coverage.evaluate(new GridCoordinates2D(x, y), sample);
                        /* Since measurement where no data exist is kinda weird, we check if it's NaN
                         *  If the measurement is larger we update "easting" and "northing" that hold the coordinates */
                        if (!Double.isNaN(sample[0]) && sample[0] > 0 && (sample[0] != 300)) {
                            values.add(sample[0]);
                        }
                    } catch (ArrayIndexOutOfBoundsException ignored) { // Catches both checked an unchecked exceptions
                    }
                }
            }
        }
        return values;
    }
}