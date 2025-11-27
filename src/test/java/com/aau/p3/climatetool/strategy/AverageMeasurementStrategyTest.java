package com.aau.p3.climatetool.strategy;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AverageMeasurementStrategyTest {

    @Test
    void processValues() {
        // Arrange
        AverageMeasurementStrategy test = new AverageMeasurementStrategy();
        List<Double> list = Arrays.asList(1.4, 2.6, 3.9, 0.5, 5.2);

        // Act
        Double averageVal = test.processValues(list);

        // Assert
        assertEquals(2.72, averageVal);
    }
}