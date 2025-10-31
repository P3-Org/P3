package com.aau.p3;

import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.awt.image.Raster;
import java.awt.image.RenderedImage;

public class GeoTiffConverter {

    public static WritableImage toFXImage(RenderedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        WritableImage fxImage = new WritableImage(width, height);
        PixelWriter writer = fxImage.getPixelWriter();
        Raster raster = image.getData();

        double min = Double.POSITIVE_INFINITY;
        double max = Double.NEGATIVE_INFINITY;

        // ✅ First pass: find min and max pixel values
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double value = raster.getSampleDouble(x, y, 0);
                if (Double.isFinite(value)) {
                    if (value < min) min = value;
                    if (value > max) max = value;
                }
            }
        }

        if (min == max) {
            min = 0;
            max = 1;
        }

        double range = max - min;
        System.out.printf("Auto-normalized image: min=%.3f, max=%.3f%n", min, max);

        // ✅ Second pass: draw pixels using normalized brightness
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double value = raster.getSampleDouble(x, y, 0);
                double gray = (value - min) / range;
                gray = Math.max(0, Math.min(1, gray)); // clamp to [0,1]
                writer.setColor(x, y, Color.gray(gray));
            }
        }

        return fxImage;
    }
}
