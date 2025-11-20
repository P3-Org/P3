package com.aau.p3.climatetool.dawa;

import com.aau.p3.climatetool.dawa.DawaPropertyNumbers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class DawaPropertyNumbersTest {

    DawaPropertyNumbers testProperty;

    @BeforeEach
    void setUp() {
        // Arrange
        List<String> coords = new ArrayList<>();
        coords.add("56.25263942");
        coords.add("10.02245235");

        // Act
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