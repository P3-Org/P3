package com.aau.p3.geotool;

import org.geotools.coverage.grid.GridCoverage2D;

import java.io.IOException;
import java.util.List;

import static com.aau.p3.geotool.SelectTif.getFileArray;

public class Main {
    public static void main(String[] args) throws IOException {
        // Redundant, only for testing
        double[][] coordinates = {{550900.0, 6320500.0},
                {551100.0, 6320500.0},
                {551100.0, 6320600.0},
                {550900.0, 6320600.0}};

        List<String> listOfTifFiles = getFileArray(coordinates);
        System.out.println(listOfTifFiles);


        RasterManager rm = new RasterManager();
        GridCoverage2D gc = rm.createRaster(listOfTifFiles);
    }



}
