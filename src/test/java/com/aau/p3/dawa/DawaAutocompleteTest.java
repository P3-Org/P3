package com.aau.p3.dawa;

import com.aau.p3.utility.UrlHelper;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class DawaAutocompleteTest {
    private DawaAutocomplete dawaAutocomplete;
    private List<String> expectedCoords;

    @BeforeEach
    void setUp() {
        dawaAutocomplete = new DawaAutocomplete();
        expectedCoords = new ArrayList<>();
    }

    @Test
    @DisplayName("autocomplete")
    void autocomplete() {
        expectedCoords.add("10.02245235");
        expectedCoords.add("56.25263942");
        List<String> addressInfo = dawaAutocomplete.autocomplete("Bondagervej+5+8382");
        Assertions.assertEquals(expectedCoords, addressInfo);
    }

    @Test
    @DisplayName("Incomplete address")
    void autocompleteFailure() {
        List<String> addressInfo = dawaAutocomplete.autocomplete("Bondag");
        Assertions.assertEquals(expectedCoords, addressInfo);
    }




}