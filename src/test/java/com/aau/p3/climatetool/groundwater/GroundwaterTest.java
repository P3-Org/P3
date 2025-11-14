package com.aau.p3.climatetool.groundwater;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GroundwaterTest {
/*
    @Test
    void groundwaterFetch() {
    }
*/
    @Test
    void testExtractKote() {
        String json = """
        {
          "kote": 12.34
        }
        """;

        double result = Groundwater.extractKote(new StringBuilder(json));

        assertEquals(12.34, result, 0.0001);
    }

    @Test
    void testExtractHValues() {
        String json = """
        {
          "statistik": {
            "samlet": [
              { "h2":1.1, "h5":2.2, "h10":3.3, "h20":4.4, "h50":5.5, "h100":6.6 }
            ]
          }
        }
        """;

        List<Double> values = Groundwater.extractHValues(new StringBuilder(json));

        assertEquals(6, values.size());
        assertEquals(1.1, values.get(0), 0.0001);
        assertEquals(6.6, values.get(5), 0.0001);
    }
}