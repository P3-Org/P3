package com.aau.p3.dawa;

import com.aau.p3.climatetool.dawa.DawaPolygonForAddress;
import com.aau.p3.climatetool.dawa.DawaPropertyNumbers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class DawaPolygonForAddressTest {
    private DawaPolygonForAddress dawaPolygonForAddress;
    private List<String> expectedCoords;
    private List<List<Double>> expectedCoordsList;
    DawaPropertyNumbers testProperty = Mockito.mock(DawaPropertyNumbers.class);


    @BeforeEach
    void setUp() {
        // Arrange
        List<String> testCoords = new ArrayList<>();

        testCoords.add("10.02245235");
        testCoords.add("56.25263942");
        //testProperty = new DawaPropertyNumbers(testCoords);
        when(testProperty.getCadastre()).thenReturn("9af");
        when(testProperty.getOwnerLicense()).thenReturn("980251");

    }


    @Test
    void getPolygon() {
        // Arrange
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

        // Act
        DawaPolygonForAddress polygon = new DawaPolygonForAddress(ownerLicense, cadastre);
        
        // Assert
        Assertions.assertEquals(expectedCoordsList, polygon.getPolygon());
    }
}