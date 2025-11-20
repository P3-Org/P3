package com.aau.p3.climatetool.dawa;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class DawaGetCoordinatesTest  {
    private List<String> expectedCoords;

    @BeforeEach
    void setUp() {
        expectedCoords = new ArrayList<>();
    }

    @Test
    @DisplayName("autocomplete")
    void autocomplete() {
        expectedCoords.add("56.25263942");
        expectedCoords.add("10.02245235");
        DawaGetCoordinates addressInfo =  new DawaGetCoordinates("Bondagervej+5+8382");
        Assertions.assertEquals(expectedCoords, addressInfo.getCoordinates());
    }

    @Test
    @DisplayName("Incomplete address")
    void autocompleteFailure() {
        expectedCoords.add("");
        expectedCoords.add("");
        DawaGetCoordinates addressInfo = new DawaGetCoordinates("Bondag");
        Assertions.assertEquals(expectedCoords, addressInfo.getCoordinates());
    }
}