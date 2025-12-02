package com.aau.p3.climatetool.strategy;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MinMeasurementStrategyTest {

    @Test
    void processValues() {
        // Arrange
        MinMeasurementStrategy test = new MinMeasurementStrategy();
        List<Double> list = Arrays.asList(1.4, 2.6, 3.9, 0.5, 5.2);

        // Act
        Double lowestVal = test.processValues(list);

        // Assert
        assertEquals(0.5, lowestVal);

    }
}