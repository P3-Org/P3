package com.aau.p3.climatetool.dawa;


import com.aau.p3.platform.urlmanager.UrlAutoComplete;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;


public class DawaGetTypeTest {

    @Mock
    DawaGetType typeResponse;

    @Mock
    UrlAutoComplete autoComplete;


    @Test
    void getVejnavnTest(){
        DawaGetType addressInfo = new DawaGetType("Bondag");
        Assertions.assertEquals("vejnavn", addressInfo.getType());
    }

    @Test
    void getAdgangsAddresseTest(){
        DawaGetType addressInfo = new DawaGetType("Bondagervej");
        Assertions.assertEquals("adgangsadresse", addressInfo.getType());
    }

    @Test
    void getAddresseTest(){
        DawaGetType addressInfo = new DawaGetType("Tryvej+30+9750");
        Assertions.assertEquals("adresse", addressInfo.getType());
    }


    @BeforeEach
    void Setup() {
    }

}