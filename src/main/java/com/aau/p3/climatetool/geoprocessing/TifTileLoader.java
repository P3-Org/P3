package com.aau.p3.climatetool.geoprocessing;

import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.gce.geotiff.GeoTiffReader;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

public class TifTileLoader {
    private static final String BASE_PATH = "src/main/resources/";
    private final List<String> filePaths = new ArrayList<>();
    private final List<GridCoverage2D> coverages = new ArrayList<>();

    /**
     * Constructor used to point to the correct file path
     * @param tifTileDir contains the specific folder
     * @param listOfTifFiles is a list of tif files gathered by methods in SelectTif.java
     */
    public TifTileLoader(String tifTileDir, List<String> listOfTifFiles) {
        for (String fileTag : listOfTifFiles) {
            this.filePaths.add(BASE_PATH + tifTileDir + "/" + fileTag);
        }
    }

    /**
     * Method used for loading both single or multiple tif files. Reads a GeoTIFF file from the specified path and loads it as a {@code GridCoverage2D}.
     * multiple times based on the amount of tif files.
     * @throws IOException if error happens while reading a file
     */
    public List<GridCoverage2D> load() throws IOException {
        for (String path : filePaths) {
            File file = new File(path);
            if (!file.exists()) {
                throw new IOException("File not found: " + path);
            }
            GeoTiffReader reader = new GeoTiffReader(file);
            coverages.add(reader.read(null));
            reader.dispose();
        }
        return coverages;
    }
}