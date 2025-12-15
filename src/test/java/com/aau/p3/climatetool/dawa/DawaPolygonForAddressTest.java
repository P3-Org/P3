package com.aau.p3.climatetool.dawa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Test for the DAWA autocomplete API call to receive nested list of doubles of coordinates
 */
class DawaPolygonForAddressTest {
    private List<List<Double>> expectedCoordsList;
    DawaPropertyNumbers testProperty = Mockito.mock(DawaPropertyNumbers.class);


    @BeforeEach
    void setup() {
        // Arrange: Mock cadastre number and owner license
        when(testProperty.getCadastre()).thenReturn("9af");
        when(testProperty.getOwnerLicense()).thenReturn("980251");

    }


    @Test
    void getPolygon() {
        // Arrange: Create list of expected response
        expectedCoordsList = Arrays.asList(
                Arrays.asList(563376.144, 6234684.897),
                Arrays.asList(563342.218, 6234679.676),
                Arrays.asList(563342.719, 6234656.802),
                Arrays.asList(563406.443, 6234666.867),
                Arrays.asList(563405.644, 6234690.422),
                Arrays.asList(563401.735, 6234689.653),
                Arrays.asList(563376.144, 6234684.897)
        );
        String cadastre = testProperty.getCadastre();
        String ownerLicense = testProperty.getOwnerLicense();

        // Act: Call the DAWA API
        DawaPolygonForAddress polygon = new DawaPolygonForAddress(ownerLicense, cadastre);
        
        // Assert
        Assertions.assertEquals(expectedCoordsList, polygon.getPolygon());
    }
}