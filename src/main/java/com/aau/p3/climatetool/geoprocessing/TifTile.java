package com.aau.p3.climatetool.geoprocessing;

import org.geotools.api.parameter.ParameterValueGroup;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.gce.geotiff.GeoTiffReader;
import org.geotools.coverage.processing.CoverageProcessor;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

public class TifTile {
    final String dir = "src/main/resources/";
    List<String> filePaths = new ArrayList<>();
    List<GridCoverage2D> coverage = new ArrayList<>();

    /**
     * Constructor used to point to the correct file path
     * @param tifTileDir contains the specific folder
     * @param listOfTifFiles is a list of tif files gathered by methods in SelectTif.java
     */
    public TifTile(String tifTileDir, List<String> listOfTifFiles) {
        for (String fileTag : listOfTifFiles) {
            this.filePaths.add(dir + tifTileDir + "/" + fileTag);
        }
    }

    /**
     * Method used for loading both single or multiple tif files. Calls private method {@link #load(String)} in for loop
     * multiple times based on the amount of tif files.
     * @throws IOException if error happens while reading a file
     */
    public void load() throws IOException {
        for (String path : filePaths) {
            load(path);
        }
    }

    /**
     * Reads a GeoTIFF file from the specified path and loads it as a {@code GridCoverage2D}
     * @param path is the path of the file that needs to be loaded into the grid
     * @throws IOException if error happens while reading a file
     */
    private void load(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            throw new IOException("File not found: " + path);
        }
        GeoTiffReader reader = new GeoTiffReader(file);
        coverage.add(reader.read(null));
        reader.dispose();
    }

    /**
     * Method for getting a coverage of the type {@code GridCoverage2D}.
     * If none exists it will throw and {@code IOException}, if there exists just one, it will return it.
     * If there are multiple, it will combine them using mosaic loader and return that.
     * @return a single {@code GridCoverage2D}
     * @throws IOException if no valid raster coverages were loaded
     */
    public GridCoverage2D getCoverage() throws IOException {
        if (coverage.isEmpty()) {
            throw new IOException("No valid raster coverages could be loaded for mosaic.");
        }

        if (coverage.size() == 1) {
            return coverage.getFirst();
        }

        CoverageProcessor processor = CoverageProcessor.getInstance();
        ParameterValueGroup params = processor.getOperation("Mosaic").getParameters();

        // set sources (accepts List<GridCoverage2D>)
        params.parameter("sources").setValue(coverage);

        // optional: set background nodata value(s)
        params.parameter("backgroundValues").setValue(new double[]{Double.NaN});

        // perform the operation (some versions accept a second 'hints' argument; null is fine)
        return (GridCoverage2D) processor.doOperation(params, null);
    }
}