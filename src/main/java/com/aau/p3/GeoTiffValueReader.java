package com.aau.p3;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.grid.GridGeometry2D;
import org.geotools.api.referencing.crs.CoordinateReferenceSystem;
import org.geotools.api.referencing.operation.MathTransform;
import org.geotools.gce.geotiff.GeoTiffReader;
import org.geotools.referencing.CRS;

import java.awt.image.Raster;
import java.io.File;

public class GeoTiffValueReader {

    public static void main(String[] args) throws Exception {
        File geotiff = new File("src/main/resources/TIF_files/SIMRAIN_1km_6310_551.tif");
        if (!geotiff.exists()) {
            System.err.println("File not found: " + geotiff.getAbsolutePath());
            return;
        }

        GeoTiffReader reader = new GeoTiffReader(geotiff);
        GridCoverage2D coverage = reader.read(null);

        CoordinateReferenceSystem rasterCRS = coverage.getCoordinateReferenceSystem2D();
        CoordinateReferenceSystem wgs84CRS = CRS.decode("EPSG:4326", true);

        MathTransform toRasterCRS = CRS.findMathTransform(wgs84CRS, rasterCRS, true);
        MathTransform toWGS84 = CRS.findMathTransform(rasterCRS, wgs84CRS, true);

        Raster raster = coverage.getRenderedImage().getData();
        int width = raster.getWidth();
        int height = raster.getHeight();

        // GridGeometry2D -> get the affine transform
        GridGeometry2D gridGeom = coverage.getGridGeometry();
        double[] ul = new double[]{0.0, 0.0}; // upper-left pixel
        double[] lr = new double[]{width - 1, height - 1}; // lower-right pixel
        double[] ulWorld = new double[2];
        double[] lrWorld = new double[2];

        gridGeom.getGridToCRS2D().transform(ul, 0, ulWorld, 0, 1);
        gridGeom.getGridToCRS2D().transform(lr, 0, lrWorld, 0, 1);

        // Convert to WGS84
        double[] ulWGS = new double[2];
        double[] lrWGS = new double[2];
        toWGS84.transform(ulWorld, 0, ulWGS, 0, 1);
        toWGS84.transform(lrWorld, 0, lrWGS, 0, 1);

        System.out.printf("Raster bounds in raster CRS: UL(%.2f, %.2f) - LR(%.2f, %.2f)%n",
                ulWorld[0], ulWorld[1], lrWorld[0], lrWorld[1]);
        System.out.printf("Raster bounds in WGS84: UL(%.5f, %.5f) - LR(%.5f, %.5f)%n",
                ulWGS[0], ulWGS[1], lrWGS[0], lrWGS[1]);

        // Example query
        double[][] coords = {{12.5683, 55.6761},{9.8482231, 56.93582533},};

        for (double[] lonlat : coords) {
            double[] projected = new double[2];
            toRasterCRS.transform(lonlat, 0, projected, 0, 1);

            // World -> pixel
            double[] pixelCoords = new double[2];
            gridGeom.getCRSToGrid2D().transform(projected, 0, pixelCoords, 0, 1);
            int x = (int) Math.round(pixelCoords[0]);
            int y = (int) Math.round(pixelCoords[1]);

            if (x < 0 || x >= width || y < 0 || y >= height) {
                System.out.printf("Coordinate (%.5f, %.5f) is outside raster bounds%n", lonlat[0], lonlat[1]);
                continue;
            }

            double value = raster.getSampleDouble(x, y, 0);
            System.out.printf("Coordinate (%.5f, %.5f) -> Pixel (%d,%d) -> Value: %.3f%n",
                    lonlat[0], lonlat[1], x, y, value);
        }

        reader.dispose();
    }
}