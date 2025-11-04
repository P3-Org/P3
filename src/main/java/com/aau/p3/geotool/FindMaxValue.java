package com.aau.p3.geotool;

import org.geotools.api.referencing.operation.MathTransform2D;
import org.geotools.coverage.grid.GridCoordinates2D;
import org.geotools.coverage.grid.GridCoverage2D;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;

public class FindMaxValue {
    private double easting;
    private double northing;

    public double getMaxValueInPolygon(GridCoverage2D coverage, double[][] worldCoords) throws Exception {
        // Build a JTS Polygon from the world coordinates
        GeometryFactory gf = new GeometryFactory();
        Coordinate[] coords = new Coordinate[worldCoords.length + 1];

        for (int i = 0; i < worldCoords.length; i++) {
            coords[i] = new Coordinate(worldCoords[i][0], worldCoords[i][1]);
        }
        coords[worldCoords.length] = coords[0]; // close the ring of the polygon
        Polygon property = gf.createPolygon(coords);

        // Prepare transforms so we can go from pixels to CRS2D and other way
        MathTransform2D gridToCRS = coverage.getGridGeometry().getGridToCRS2D();
        MathTransform2D crsToGrid = (MathTransform2D) gridToCRS.inverse(); // <-- not used

        // Get grid limits directly from coverage (pixel limits)
        int width = coverage.getRenderedImage().getWidth();
        int height = coverage.getRenderedImage().getHeight();

        // Keep track of the max value and the east and north coordinates in these variables
        double maxVal = Double.NEGATIVE_INFINITY;
        easting = Double.NEGATIVE_INFINITY;
        northing = Double.NEGATIVE_INFINITY;
        double[] sample = new double[1];

        // Loop over every pixel in the grid (full coverage)
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Compute world coordinate for pixel center
                double[] gridPt = {x + 0.5, y + 0.5};
                double[] worldPt = new double[2];
                gridToCRS.transform(gridPt, 0, worldPt, 0, 1);

                // Check if this pixel’s world coordinate lies inside the polygon (kinda crazy shit)
                Point p = gf.createPoint(new Coordinate(worldPt[0], worldPt[1]));
                if (property.contains(p)) {
                    try {
                        coverage.evaluate(new GridCoordinates2D(x, y), sample);
                        if (!Double.isNaN(sample[0]) && sample[0] > maxVal) {
                            maxVal = sample[0];
                            easting = worldPt[0]; // Find the easting coordinate for where the max value lies
                            northing = worldPt[1]; // Find the northing coordinate for where the max value lies
                        }
                    } catch (Exception ignored) {
                        // Skip any pixel outside valid coverage
                    }
                }
            }
        }
        return (maxVal * 1000);
    }

    public double getEasting() {
        return (double) Math.round(this.easting * 10) / 10;
    }

    public double getNorthing() {
        return (double) Math.round(this.northing * 10) / 10;
    }
}