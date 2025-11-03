package com.aau.p3.geotool;

import org.geotools.api.data.DataSourceException;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.gce.geotiff.GeoTiffReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TifTile {
    final String dir = "";
    String filePath;
    List<String> filePaths = new ArrayList<>();
    GridCoverage2D coverageSingle;
    List<GridCoverage2D> coverageMultiple = new ArrayList<>();

    public TifTile(String fileName) {
        this.filePath = dir + fileName;
    }

    public TifTile(List<String> fileNames) {
        for (String fileName : fileNames) {
            this.filePaths.add(dir + fileName);
        }
    }

    public GridCoverage2D getSingleCoverage() {
        return this.coverageSingle;
    }

    public List<GridCoverage2D> getMultipleCoverage() {
        return this.coverageMultiple;
    }

    public void loadSingle() throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("File not found: " + filePath);
        }

        GeoTiffReader reader = new GeoTiffReader(file);
        coverageSingle = reader.read(null);
    }

    public void loadMultiple() throws IOException {
        for (String path : filePaths) {

            File file = new File(path);
            if (!file.exists()) {
                throw new IOException("File not found: " + path);
            }

            GeoTiffReader reader = new GeoTiffReader(file);
            coverageMultiple.add(reader.read(null));
            reader.dispose();
        }
    }
}
