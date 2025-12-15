package com.aau.p3.climatetool.dawa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

/**
 * Test for the DAWA autocomplete API call
 */
public class DawaGetAddressesTest {
    List<String > expectedAddresses;

    @BeforeEach
    void setUp() {
        expectedAddresses = new ArrayList<String>();
    }

    @Test
    @DisplayName("Test for auto completion of addresses")
    void getAddresses(){
        // Arrange: Create list of expected response
        expectedAddresses.add("Bondager");
        expectedAddresses.add("Bondagervej");

        // Act: Call the DAWA API
        DawaGetAddresses addressInfo = new DawaGetAddresses("Bondag");

        // Assert: Check whether the result matches the expected value.
        Assertions.assertEquals(expectedAddresses, addressInfo.getAddresses());
    }

}