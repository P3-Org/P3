package com.aau.p3.geotool;


import org.geotools.api.parameter.ParameterValueGroup;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.gce.geotiff.GeoTiffReader;
import org.geotools.coverage.processing.CoverageProcessor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TifTile {
    final String dir = "src/main/resources/TIF-files/";
    List<String> filePaths = new ArrayList<>();
    List<GridCoverage2D> coverage = new ArrayList<>();

    public TifTile(String fileName) {
        this.filePaths.add(dir + fileName);
    }

    public TifTile(List<String> fileNames) {
        for (String fileName : fileNames) {
            this.filePaths.add(dir + fileName);
        }
    }

    public void load() throws IOException {
        for (String path : filePaths) {
            load(path);
        }
    }

    private void load(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            throw new IOException("File not found: " + path);
        }
        GeoTiffReader reader = new GeoTiffReader(file);
        coverage.add(reader.read(null));
        reader.dispose();
    }

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
