package com.aau.p3;

import org.geotools.api.referencing.operation.MathTransform2D;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.referencing.operation.transform.AffineTransform2D;

import java.awt.geom.Point2D;

/**
 * Converts pixel coordinates (x, y) to world (map) coordinates using
 * the GeoTIFF’s affine transform (works with EPSG:25832 and GeoTools 30.x)
 * No DirectPosition or geometry dependency needed.
 */
public class GeoUtils {

    public static double[] pixelToWorld(GridCoverage2D coverage, int pixelX, int pixelY) {
        try {
            // ✅ Get the affine transform from the coverage
            AffineTransform2D gridToCRS =
                    (AffineTransform2D) coverage.getGridGeometry().getGridToCRS2D();

            // ✅ Transform the pixel coordinates to real-world coordinates
            Point2D src = new Point2D.Double(pixelX, pixelY);
            Point2D dst = new Point2D.Double();
            gridToCRS.transform(src, dst);

            // ✅ Return [Easting, Northing]
            return new double[]{dst.getX(), dst.getY()};

        } catch (Exception e) {
            e.printStackTrace();
            return new double[]{Double.NaN, Double.NaN};
        }
    }


    /**
     * Converts real-world (map) coordinates to pixel (x, y) coordinates.
     *
     * @param coverage the GeoTools coverage
     * @param easting  real-world X coordinate (e.g., UTM ETRS89)
     * @param northing real-world Y coordinate
     * @return double[]{pixelX, pixelY}
     */
    public static double[] worldToPixel(GridCoverage2D coverage, double easting, double northing) {
        try {
            MathTransform2D gridToCRS = coverage.getGridGeometry().getGridToCRS2D();

            // ✅ Invert the transform safely
            MathTransform2D crsToGrid = (MathTransform2D) gridToCRS.inverse();

            double[] src = new double[]{easting, northing};
            double[] dst = new double[2];
            crsToGrid.transform(src, 0, dst, 0, 1);

            return dst;

        } catch (Exception e) {
            e.printStackTrace();
            return new double[]{Double.NaN, Double.NaN};
        }
    }
}