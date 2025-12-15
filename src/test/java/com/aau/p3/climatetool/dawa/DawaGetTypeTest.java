package com.aau.p3.climatetool.dawa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test for the DAWA autocomplete API call to receive type of searched address
 */
public class DawaGetTypeTest {
    String expected;

    @Test
    void getVejnavnTest() {
        // Arrange: Create expected string
        expected = "vejnavn";

        // Act: Call the DAWA API
        DawaGetType addressInfo = new DawaGetType("Bondag");

        // Assert: Check whether the result matches the expected value.
        Assertions.assertEquals(expected, addressInfo.getType());
    }

    @Test
    void getAdgangsAddresseTest() {
        // Arrange: Create expected string
        expected = "adgangsadresse";

        // Act: Call the DAWA API
        DawaGetType addressInfo = new DawaGetType("Bondagervej");

        // Assert: Check whether the result matches the expected value.
        Assertions.assertEquals(expected, addressInfo.getType());
    }

    @Test
    void getAddressTest() {
        // Arrange: Create expected string
        expected = "adresse";

        // Act: Call the DAWA API
        DawaGetType addressInfo = new DawaGetType("Tryvej+30+9750");

        // Assert: Check whether the result matches the expected value.
        Assertions.assertEquals(expected, addressInfo.getType());
    }
}