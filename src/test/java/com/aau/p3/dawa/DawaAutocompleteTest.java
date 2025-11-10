package com.aau.p3.dawa;

import com.aau.p3.climatetool.dawa.DawaAutocomplete;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

class DawaAutocompleteTest {
    private List<String> expectedCoords;

    @BeforeEach
    void setUp() {
        expectedCoords = new ArrayList<>();
    }

    @Test
    @DisplayName("autocomplete")
    void autocomplete() {
        expectedCoords.add("10.02245235");
        expectedCoords.add("56.25263942");
        DawaAutocomplete addressInfo =  new DawaAutocomplete("Bondagervej+5+8382");
        Assertions.assertEquals(expectedCoords, addressInfo.getCoordinates());
    }

    @Test
    @DisplayName("Incomplete address")
    void autocompleteFailure() {
        DawaAutocomplete addressInfo = new DawaAutocomplete("Bondag");
        Assertions.assertEquals(expectedCoords, addressInfo.getCoordinates());
    }
}