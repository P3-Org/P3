package com.aau.p3.utility;

import com.aau.p3.platform.urlmanager.UrlPolygon;
import com.aau.p3.platform.urlmanager.UrlManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Check whether UrlManager returns 'null' when an invalid endpoint is reached.
 */
class UrlManagerTest {
    @Test
    @DisplayName("Failed api call test")
    void GETResponseTest() {
        // Arrange: Create a UrlManager with a non-existing URL.
        new UrlManager("https//:NotASite");

        // Act: Fetch a polygon from an invalid endpoint.
        UrlPolygon testPolygon = new UrlPolygon("not", "real");
        StringBuilder response = testPolygon.GETPolygon();

        // Assert: Check if the response is in fact null, from the invalid endpoint.
        assertNull(response);
    }
}