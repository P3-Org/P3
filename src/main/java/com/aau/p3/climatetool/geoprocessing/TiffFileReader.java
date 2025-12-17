package com.aau.p3.climatetool.geoprocessing;

import com.aau.p3.climatetool.utilities.GeoDataReader;
import org.geotools.api.referencing.operation.TransformException;
import org.geotools.coverage.grid.GridCoverage2D;
import java.io.IOException;
import java.util.List;
import static com.aau.p3.climatetool.geoprocessing.SelectTiff.getFileArray;

/**
 * Performs all the necessary calls needed to read from GeoTiff files.
 */
public class TiffFileReader implements GeoDataReader {

    /**
     * Method for reading geo data based on any type of tiff file.
     * @param coordinates The EPSG:25832 coordinates for the polygons corners
     * @param tiffTileDir The directory containing the relevant tiff files
     * @param fileTag The file tag based on the specific climate risk i.e. "SIMRAIN", "DTM"...
     * @return list of sample values from gathered from {@code getMeasurements()}
     */
    @Override
    public List<Double> readValues(double[][] coordinates, String tiffTileDir, String fileTag) {
        try {
            /* Locate which tiff files that the coordinates fall in to */
            List<String> listOfTiffFiles = getFileArray(coordinates, fileTag);

            /* Instantiates a tiff tile object that will create the coverage grid based on the tiff files */
            TiffTileLoader loader = new TiffTileLoader(tiffTileDir, listOfTiffFiles);
            List<GridCoverage2D> tiles = loader.load();

            /* Mosaic the given coverage(s) (tiles) */
            CoverageMosaicker mosaicker = new CoverageMosaicker();
            GridCoverage2D unifiedCoverage = mosaicker.mosaicTiles(tiles);

            /* Returns the list of measurements points, that fall within the polygon encapsulating the property */
            ClimateMeasurements valuesOfProperty = new ClimateMeasurements();
            return valuesOfProperty.getMeasurements(unifiedCoverage, coordinates);

        } catch (IOException | TransformException e) {
            throw new RuntimeException(e);
        }
    }
}