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
    void setup() {
        expectedCoords = new ArrayList<>();
    }

    @Test
    @DisplayName("autocomplete")
    void autocomplete() {
        //expectedCoords.add("56.25263942");
        //expectedCoords.add("10.02245235");
        expectedCoords.add("563350.22");
        expectedCoords.add("6234668.27");
        DawaGetEastingNorthing addressInfo =  new DawaGetEastingNorthing("Bondagervej+5+8382");
        Assertions.assertEquals(expectedCoords, addressInfo.getEastingNorthing());
    }

    @Test
    @DisplayName("Incomplete address")
    void autocompleteFailure() {
        expectedCoords.add("");
        expectedCoords.add("");
        DawaGetEastingNorthing addressInfo = new DawaGetEastingNorthing("Bondag");
        Assertions.assertEquals(expectedCoords, addressInfo.getEastingNorthing());
    }
}