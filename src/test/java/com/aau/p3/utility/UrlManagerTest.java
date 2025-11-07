package com.aau.p3.utility;

import com.aau.p3.platform.urlmanager.Polygon;
import com.aau.p3.platform.urlmanager.UrlManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @TODO mock data for the other methods
 * */
class UrlManagerTest {

    @Test
    @DisplayName("Failed api call test")
    void getResponseTest() {
        UrlManager helper = new UrlManager("https//:NotASite");
        Polygon testPolygon = new Polygon("https//:NotASite", "not", "real");
        StringBuilder response = testPolygon.getPolygon();
        assertNull(response);
    }
}