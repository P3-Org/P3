package com.aau.p3.climatetool.geoprocessing;

import com.aau.p3.climatetool.utilities.URLFileUtil;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.gce.geotiff.GeoTiffReader;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to define the loading of the geotiff files, which are located on an FTP server.
 * */
public class TiffTileLoader {
    private static final String USER_NAME = "ftpuser";
    private static final String PASSWORD = "0Ett84fGAB:&";
    private static final String IP_ADDRESS = "130.225.39.117";
    private static final String BASE_URL = "http://" + IP_ADDRESS + "/maps/";
    private final List<String> filePaths = new ArrayList<>();
    private final List<GridCoverage2D> coverages = new ArrayList<>();

    /**
     * Constructor used to point to the correct file path
     * @param tifTileDir contains the specific folder
     * @param listOfTifFiles is a list of tif files gathered by methods in SelectTif.java
     */
    public TiffTileLoader(String tifTileDir, List<String> listOfTifFiles) {
        for (String fileTag : listOfTifFiles) {
            this.filePaths.add(BASE_URL + tifTileDir + "/" + fileTag);
        }
    }

    /**
     * Method used for loading both single or multiple tif files. Reads a GeoTIFF file from the specified path,
     * and loads it as a {@code GridCoverage2D}.
     * multiple times based on the amount of tif files.
     * @throws IOException if error happens while reading a file
     */
    public List<GridCoverage2D> load() throws IOException {
        for (String path : filePaths) {
            URLFileUtil.authEnable(USER_NAME, PASSWORD);
            File file = URLFileUtil.downloadGeoTiff(path);
            GeoTiffReader reader = new GeoTiffReader(file);
            coverages.add(reader.read(null));
            reader.dispose();
        }
        return coverages;
    }
}