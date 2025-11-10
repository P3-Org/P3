package com.aau.p3.climatetool.geoprocessing;

import org.geotools.api.parameter.ParameterValueGroup;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.processing.CoverageProcessor;

import java.io.IOException;
import java.util.List;

public class CoverageMosaicker {

    /**
     * Method for getting a coverage of the type {@code GridCoverage2D}.
     * If none exists it will throw and {@code IOException}, if there exists just one, it will return it.
     * If there are multiple, it will combine them using mosaic loader and return that.
     * @return a single {@code GridCoverage2D}
     * @throws IOException if no valid raster coverages were loaded
     */
    public GridCoverage2D mosaicTiles(List<GridCoverage2D> coverages) throws IOException {
        // If no coverage, throw exception
        if (coverages.isEmpty()) {
            throw new IOException("No valid raster coverages could be loaded for mosaic.");
        }

        // If just one coverage, return it
        if (coverages.size() == 1) {
            return coverages.getFirst();
        }

        /* If more than one coverage was loaded, they need to be combined into a single coverage.
         * The GeoTools CoverageProcessor handles this using the "Mosaic" operation.
         */
        CoverageProcessor processor = CoverageProcessor.getInstance();

        /* Retrieve the parameter group for the Mosaic operation.
         * Lets us define which coverages to merge and how the mosaic should be handled.
         */
        ParameterValueGroup params = processor.getOperation("Mosaic").getParameters();

        // Specify the list of source coverages (the individual tiles) to be merged into one raster.
        params.parameter("sources").setValue(coverages);

        // Set a background value for areas where no data exists in any tile.
        // Using NaN ensures that nodata regions remain transparent or undefined in the final mosaic.
        params.parameter("backgroundValues").setValue(new double[] { Double.NaN }); // Could also have been set to 0.0

        // Execute the mosaic operation and return the resulting combined GridCoverage2D.
        // The second argument hints can be null for it to use the default hints unless specific rendering options are required.
        return (GridCoverage2D) processor.doOperation(params, null);
    }
}