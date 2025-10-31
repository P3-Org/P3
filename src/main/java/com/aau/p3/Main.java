package com.aau.p3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import org.geotools.coverage.grid.GridCoordinates2D;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.gce.geotiff.GeoTiffReader;
import org.geotools.gce.imagemosaic.ImageMosaicReader;
import org.geotools.image.ImageWorker;

import javax.media.jai.Interpolation;
import java.awt.RenderingHints;
import java.awt.image.Raster;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;


public class Main extends Application {
    private final double NO_DATA_VAL = -340282346638528860000000000000000000000.000;

    @Override
    public void start(Stage primaryStage) {
        try {
            // Path to folder of .tif files
            //File input = new File("src/main/resources/TIF_files/SIM-rain_631_56_TIF_UTM32-ETRS89/");
            File input = new File("src/main/resources/TIF_files/SIMRAIN_1km_6310_551.tif");

            GridCoverage2D coverage;

            if (input.isDirectory()) {
                // Load a folder of TIFs as mosaic
                ImageMosaicReader mosaicReader = new ImageMosaicReader(input, null);
                coverage = mosaicReader.read(null);
            } else if (input.isFile() && input.getName().toLowerCase().endsWith(".tif")) {
                // Load single GeoTIFF
                GeoTiffReader tiffReader = new GeoTiffReader(input);
                coverage = tiffReader.read(null);
            } else {
                throw new IllegalArgumentException("Input is not a valid GeoTIFF file or folder: " + input);
            }

            // Get the raw mosaic image
            RenderedImage rendered = coverage.getRenderedImage();
            int width = rendered.getWidth();
            int height = rendered.getHeight();
            Raster raster = rendered.getData();
            int minX = raster.getMinX();
            int minY = raster.getMinY();
            System.out.printf("Raster bounds: minX=%d, minY=%d, width=%d, height=%d%n", minX, minY, width, height);

            double min = Double.POSITIVE_INFINITY;
            double max = Double.NEGATIVE_INFINITY;
            int countValid = 0;

            int maxX = -1;
            int maxY = -1;

            for (int y = minY; y < minY + height; y ++) {
                for (int x = minX; x < minX + width; x ++) {
                    double val = raster.getSampleDouble(x, y, 0);
                    if (val != NO_DATA_VAL) {
                        countValid++;
                        if (val > max) {
                            max = val;
                            maxX = x;
                            maxY = y;
                        }
                        min = Math.min(min, val);
                        max = Math.max(max, val);
                    }
                }
            }

            if (countValid > 0) {
                System.out.printf("Found %d valid samples. Min = %.6f, Max = %.6f%n",
                        countValid, min, max);
                System.out.printf("Max = %.6f at pixel (x=%d, y=%d)%n", max, maxX, maxY);
            } else {
                System.out.println("No valid data found in this GeoTIFF or mosaic.");
            }

            /* Convert pixels to real-world coordinates */
            System.out.println("Check this data:");

            // For the combined tif files
            //int thisX = 15000;
            //int thisY = 5201;
            //System.out.println(raster.getSampleDouble(15000, 5201, 0));

            // For the single tif file: (2000, 800) worked before for the single file
            int thisX = 2054;
            int thisY = 677;
            System.out.println(raster.getSampleDouble(thisX, thisY, 0));

            // Pixels to world coordinates
            double[] worldCoords = GeoUtils.pixelToWorld(coverage, thisX, thisY);
            System.out.printf("Pixel (%d, %d) -> Easting: %.3f, Northing: %.3f%n", thisX, thisY, worldCoords[0], worldCoords[1]);

            // World coordinates to pixels
            double[] pixel = GeoUtils.worldToPixel(coverage, worldCoords[0], worldCoords[1]);
            System.out.printf("World -> Pixel: %.1f, %.1f%n", pixel[0], pixel[1]);

            // Define a safe scale factor
            double scaleFactor = 0.1;
            if (width > 10000 || height > 10000) scaleFactor = 0.05;
            if (width > 20000 || height > 20000) scaleFactor = 0.02;

            System.out.printf("Scaling mosaic from %dx%d → %.0f%%%n", width, height, scaleFactor * 100);

            // Proper scale() call for GeoTools 30.x
            ImageWorker worker = new ImageWorker(rendered);
            worker.setRenderingHint(RenderingHints.KEY_INTERPOLATION, Interpolation.getInstance(Interpolation.INTERP_NEAREST));
            worker.scale(
                    scaleFactor,  // x scale
                    scaleFactor,  // y scale
                    0,            // x translation
                    0,            // y translation
                    Interpolation.getInstance(Interpolation.INTERP_NEAREST) // interpolation
            );
            BufferedImage scaled = worker.getBufferedImage();

            // Convert scaled image to JavaFX WritableImage
            WritableImage fxImage = GeoTiffConverter.toFXImage(scaled);

            // Display image in window
            ImageView view = new ImageView(fxImage);
            view.setPreserveRatio(true);
            view.setFitWidth(1000);

            StackPane root = new StackPane(view);
            Scene scene = new Scene(root, 1200, 900);
            primaryStage.setScene(scene);
            primaryStage.setTitle("GeoTIFF Mosaic Viewer");
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}