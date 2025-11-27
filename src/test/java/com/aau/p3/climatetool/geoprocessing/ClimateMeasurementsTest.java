package com.aau.p3.climatetool.geoprocessing;

import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class ClimateMeasurementsTest {

    @Test
    void buildCoordinateTest() throws Exception {
        // Step 1: Make object and make method accessilble
        ClimateMeasurements cm = new ClimateMeasurements();

        Method method = ClimateMeasurements.class.getDeclaredMethod("buildCoordinate", double[][].class);
        method.setAccessible(true); // allow access to private method

        // Step 2: Prepare input
        double[][] input = {
                {1.0, 2.0},
                {3.0, 4.0},
                {5.0, 6.0}
        };

        // Step 3: Call method
        Coordinate[] result = (Coordinate[]) method.invoke(cm, (Object) input);

        // Step 4: Assertions
        assertEquals(1.0, result[0].x);
        assertEquals(2.0, result[0].y);
        assertEquals(3.0, result[1].x);
        assertEquals(4.0, result[1].y);
        assertEquals(1.0, result[3].x); // Closing coordinate should match first
        assertEquals(2.0, result[3].y);
    }
}