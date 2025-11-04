package com.aau.p3.geotool;

import org.geotools.api.referencing.operation.MathTransform;
import org.geotools.coverage.grid.GridCoverage2D;
import java.util.List;
import static com.aau.p3.geotool.SelectTif.getFileArray;
import org.locationtech.jts.geom.*;

public class Main {
    public static void main(String[] args) throws Exception {
        // Redundant, only for testing
        double[][] coordinates = {{550900.0, 6320500.0},
                {551100.0, 6320500.0},
                {551100.0, 6320600.0},
                {550900.0, 6320600.0}};
        double[][] coords = {{550897.0, 6320500.0}};

        List<String> listOfTifFiles = getFileArray(coordinates);
        System.out.println(listOfTifFiles);

        TifTile tile = new TifTile(listOfTifFiles);
        tile.load();
        GridCoverage2D gc = tile.getCoverage();

        double[] pixels = getPixels(gc, 550900.0, 6320500.0);
        double value = getValue(pixels, gc);

        System.out.println(value);
    }

    public static double getValue(GridCoverage2D coverage, double worldX, double worldY) throws Exception {
        GridGeometry2D gridGeom = coverage.getGridGeometry();

        // Transform from world (map) to grid (pixel) coordinates
        MathTransform worldToGrid = gridGeom.getCRSToGrid2D();
        double[] srcPt = new double[] { worldX, worldY };
        double[] destPt = new double[2];
        worldToGrid.transform(srcPt, 0, destPt, 0, 1);

            return dst;

        } catch (Exception e) {
            e.printStackTrace();
            return new double[]{Double.NaN, Double.NaN};
        }
    }

    public static double getValue(double[] coords, GridCoverage2D coverage) {
        int x = (int) coords[0];
        int y = (int) coords[1];

        double[] pixel = new double[1];
        coverage.evaluate(new org.geotools.coverage.grid.GridCoordinates2D(x, y), pixel);

        return pixel[0];
    }
}
