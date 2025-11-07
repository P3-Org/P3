package com.aau.p3.climatetool.geoprocessing;

import com.aau.p3.climatetool.utilities.GeoDataReader;
import org.geotools.api.referencing.operation.TransformException;
import org.geotools.coverage.grid.GridCoverage2D;
import java.io.IOException;
import java.util.List;
import static com.aau.p3.climatetool.geoprocessing.SelectTif.getFileArray;

public class TifFileReader implements GeoDataReader {

    /**
     * Method for reading geo data based on any type of tif file.
     * @param coordinates The EPSG:25832 coordinates for the polygons corners
     * @param tifTileDir The directory containing the relevant tif files
     * @param fileTag The file tag based on the specific climate risk i.e. "SIMRAIN", "DTM"...
     * @return list of sample values from gathered from {@code getMeasurements()}
     */
    @Override
    public List<Double> readValues(double[][] coordinates, String tifTileDir, String fileTag) {
        try {
            /* Locate which tif files that the coordinates fall in to */
            List<String> listOfTifFiles = getFileArray(coordinates, fileTag);

            /* Instantiates a tif tile object that will create the coverage grid based on the tif files */
            TifTileLoader loader = new TifTileLoader(tifTileDir, listOfTifFiles);
            List<GridCoverage2D> tiles = loader.load();

            CoverageMosaicker mosaicker = new CoverageMosaicker();
            GridCoverage2D unifiedCoverage = mosaicker.mosaicTiles(tiles);

            /* Gathers the max value from the tif file that is within the bounds of the polygon (property).
            *  Then returns it */
            ClimateMeasurements valuesOfProperty = new ClimateMeasurements();

            return valuesOfProperty.getMeasurements(unifiedCoverage, coordinates);
        } catch (IOException | TransformException e) {
            throw new RuntimeException(e);
        }
    }
}