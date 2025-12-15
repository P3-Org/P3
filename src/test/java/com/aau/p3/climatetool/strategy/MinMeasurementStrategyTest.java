package com.aau.p3.climatetool.strategy;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for checking the logic of the MinMeasurementStrategy in geoprocessing
 */
class MinMeasurementStrategyTest {

    @Test
    void processValues() {
        // Arrange: Create object and a list of measurements.
        MinMeasurementStrategy test = new MinMeasurementStrategy();
        List<Double> list = Arrays.asList(1.4, 2.6, 3.9, 0.5, 5.2);

        // Act: Call the method on the list.
        Double lowestVal = test.processValues(list);

        // Assert: Check whether the result matches the expected value.
        assertEquals(0.5, lowestVal);
    }
}