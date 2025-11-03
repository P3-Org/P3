package com.aau.p3.geotool;

import java.io.IOException;
import java.util.List;
import org.geotools.api.parameter.ParameterValueGroup;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.processing.operation.Mosaic;

public class RasterManager {

    public GridCoverage2D createRaster(List<String> fileNames) throws IOException {
        if (fileNames.isEmpty()){
            throw new IOException("No valid raster coverages could be loaded for mosaic.");
        }
        if (fileNames.size() == 1) {
            TifTile tile = new TifTile(fileNames.getFirst());
            tile.loadSingle();
            return tile.getSingleCoverage();
        }

        TifTile tile = new TifTile(fileNames);
        tile.loadMultiple();

        Mosaic mosaicOp = new Mosaic();
        ParameterValueGroup params = mosaicOp.getParameters();
        params.parameter("sources").setValue(tile.getMultipleCoverage());

        return (GridCoverage2D) mosaicOp.doOperation(params, null);
    }
}
