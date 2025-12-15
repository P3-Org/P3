package com.aau.p3.climatetool.strategy;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for checking the logic of the MaxMeasurementStrategy in geoprocessing
 */
class MaxMeasurementStrategyTest {

    @Test
    void processValues() {
        // Arrange: Create object and a list of measurements.
        MaxMeasurementStrategy test = new MaxMeasurementStrategy();
        List<Double> list = Arrays.asList(1.4, 2.6, 3.9, 0.5, 5.2);

        // Act: Call the method on the list.
        Double highestVal = test.processValues(list);

        // Assert: Check whether the result matches the expected value.
        assertEquals(5.2, highestVal);
    }
}