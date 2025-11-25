package com.aau.p3.climatetool.dawa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DawaGetTypeTest {
    @BeforeEach
    void Setup() {
    }

    @Test
    void getVejnavnTest() {
        DawaGetType addressInfo = new DawaGetType("Bondag");
        Assertions.assertEquals("vejnavn", addressInfo.getType());
    }

    @Test
    void getAdgangsAddresseTest() {
        DawaGetType addressInfo = new DawaGetType("Bondagervej");
        Assertions.assertEquals("adgangsadresse", addressInfo.getType());
    }

    @Test
    void getAddressTest() {
        DawaGetType addressInfo = new DawaGetType("Tryvej+30+9750");
        Assertions.assertEquals("adresse", addressInfo.getType());
    }
}