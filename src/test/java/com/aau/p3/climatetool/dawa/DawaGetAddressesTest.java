package com.aau.p3.climatetool.dawa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class DawaGetAddressesTest {
    List<String > expectedAddresses;

    @BeforeEach
    void setUp() {
        expectedAddresses = new ArrayList<String>();
    }

    @Test
    @DisplayName("Test for auto completion of addresses")
    void getAddresses(){
        expectedAddresses.add("Bondager");
        expectedAddresses.add("Bondagervej");
        DawaGetAddresses addressInfo = new DawaGetAddresses("Bondag");
        Assertions.assertEquals(expectedAddresses, addressInfo.getAddresses());
    }

}