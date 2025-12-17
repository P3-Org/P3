package com.aau.p3.climatetool.dawa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

/**
 * Test for the DAWA autocomplete API call to receive coordinates
 */
class DawaGetCoordinatesTest  {
    private List<String> expectedCoords;

    @BeforeEach
    void setup() {
        expectedCoords = new ArrayList<>();
    }

    @Test
    @DisplayName("autocomplete")
    void autocomplete() {
        // Arrange: Create list of expected response
        expectedCoords.add("563350.22");
        expectedCoords.add("6234668.27");

        // Act: Call the DAWA API
        DawaGetEastingNorthing addressInfo =  new DawaGetEastingNorthing("Bondagervej+5+8382");

        // Assert: Check whether the result matches the expected value.
        Assertions.assertEquals(expectedCoords, addressInfo.getEastingNorthing());
    }

    @Test
    @DisplayName("Incomplete address")
    void autocompleteFailure() {
        // Arrange: create list of expected response
        expectedCoords.add("");
        expectedCoords.add("");

        // Act: Call the DAWA API
        DawaGetEastingNorthing addressInfo = new DawaGetEastingNorthing("Bondag");

        // Assert: Check whether the result matches the expected value.
        Assertions.assertEquals(expectedCoords, addressInfo.getEastingNorthing());
    }
}