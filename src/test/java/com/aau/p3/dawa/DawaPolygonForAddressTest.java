package com.aau.p3.dawa;

import com.aau.p3.climatetool.dawa.DawaPolygonForAddress;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DawaPolygonForAddressTest {
    private DawaPolygonForAddress dawaPolygonForAddress;
    private List<String> expectedCoords;
    private List<List<Double>> expectedCoordsList;

    @BeforeEach
    void setUp() {
        dawaPolygonForAddress = new DawaPolygonForAddress();
        expectedCoords = new ArrayList<>();
        //expectedCoordsList = new ArrayList<>();
    }

    @Test
    @DisplayName("Get matrikelNo and city code")
    void getPropertyNo() {
        expectedCoords.add("10.02245235");
        expectedCoords.add("56.25263942");
        dawaPolygonForAddress.getPropertyNo(expectedCoords);
        Assertions.assertEquals( "9af", dawaPolygonForAddress.matrikel);
        Assertions.assertEquals("980251", dawaPolygonForAddress.kode);
    }



    @Test
    void getPolygon() {
        expectedCoordsList = Arrays.asList(
                Arrays.asList(563376.144, 6234684.897),
                Arrays.asList(563342.218, 6234679.676),
                Arrays.asList(563342.719, 6234656.802),
                Arrays.asList(563406.443, 6234666.867),
                Arrays.asList(563405.644, 6234690.422),
                Arrays.asList(563401.735, 6234689.653),
                Arrays.asList(563376.144, 6234684.897)
        );
        dawaPolygonForAddress.matrikel = "9af";
        dawaPolygonForAddress.kode = "980251";
        System.out.println(dawaPolygonForAddress.kode);
        List<List<Double>> polygon = dawaPolygonForAddress.getPolygon();
        Assertions.assertEquals(expectedCoordsList, polygon);
    }
}