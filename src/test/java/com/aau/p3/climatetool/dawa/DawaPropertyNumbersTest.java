package com.aau.p3.climatetool.dawa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class DawaPropertyNumbersTest {

    DawaPropertyNumbers testProperty;

    @BeforeEach
    void setup() {

        List<String> coords = new ArrayList<>();

        // List of Lat/Long coordinates if we go back to using them
        //coords.add("56.25263942"); Long
        //coords.add("10.02245235"); Lat

        // Arrange: Create list of expected response
        coords.add("563350.22"); // Easting
        coords.add("6234668.27"); // Northing

        // Act: Call the DAWA API
        testProperty = new DawaPropertyNumbers(coords);
    }

    @Test
    @DisplayName("Unit test for cadastre")
    void cadastreInfoTest() {
        // Assert
        Assertions.assertEquals("9af", testProperty.getCadastre());
    }

    @Test
    @DisplayName("Unit test for owner license")
    void ownerLicenseInfoTest() {
        // Assert
        Assertions.assertEquals("980251", testProperty.getOwnerLicense());
    }
}