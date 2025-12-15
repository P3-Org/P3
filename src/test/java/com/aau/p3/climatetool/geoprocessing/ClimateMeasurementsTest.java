package com.aau.p3.climatetool.geoprocessing;

import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import java.lang.reflect.Method;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test that checks if buildCoordinate successfully converts double[][] to Coordinate[]
 */
class ClimateMeasurementsTest {

    @Test
    void buildCoordinateTest() throws Exception {
        // Act: Make object and make method accessible, since buildCoordinate is a private method
        ClimateMeasurements cm = new ClimateMeasurements();

        Method method = ClimateMeasurements.class.getDeclaredMethod("buildCoordinate", double[][].class);
        method.setAccessible(true);

        // Input to be converted from double nested array to Coordinate array
        double[][] input = {
                {1.0, 2.0},
                {3.0, 4.0},
                {5.0, 6.0}
        };

        // Act: Call the method and store in result
        Coordinate[] result = (Coordinate[]) method.invoke(cm, (Object) input);

        // Assert: Check whether the coordinates match the expected results
        assertEquals(1.0, result[0].x);
        assertEquals(2.0, result[0].y);
        assertEquals(3.0, result[1].x);
        assertEquals(4.0, result[1].y);
        assertEquals(1.0, result[3].x); // Closing coordinate should match first
        assertEquals(2.0, result[3].y);
    }
}