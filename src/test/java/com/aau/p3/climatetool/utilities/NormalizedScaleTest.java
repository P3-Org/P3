package com.aau.p3.climatetool.utilities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/***
 * testNormalized, tests that the X position we place the slider at is as we expect it.
 * The implemented slider uses the normalized measurementValue along with a normalized scale, to place the slider.
 * We check to see if the thumb it placed the same on the normalized scale as we would expect on a normal scale.
 * With the mock values we expect a value of 0.365 meaning that the slider should be set 36.5% of the way up the scale
 */
public class NormalizedScaleTest {
    double[] mockThresholds = {10,30};
    double minNormalized;
    double maxNormalized;
    double minNormal;
    double maxNormal;
    double measurementValue;

    // Initialize values before each test is run.
    @BeforeEach
    void setup() {
        minNormalized = -0.5;
        maxNormalized = 1.5;
        minNormal = 0;
        maxNormal = 40;
        measurementValue = 14.6;
    }


    @Test
    void testNormalized() {
        // Arrange: Get normalized measurement value.
        double normalizedMeasurementValue = NormalizeSample.minMaxNormalization(14.6, mockThresholds);

        // Act: Perform operations
        double percentOfScaleNormalized = (normalizedMeasurementValue - minNormalized) / (maxNormalized - minNormalized);
        double percentofScaleNormal = (measurementValue/(minNormal+maxNormal));

        // Assert:
        assertEquals(percentofScaleNormal, percentOfScaleNormalized);
    }


}
