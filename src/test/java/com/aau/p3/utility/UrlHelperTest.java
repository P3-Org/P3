package com.aau.p3.utility;

import com.aau.p3.platform.urlmanager.UrlHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author Batman
 * @TODO mock data for the other methods
 * */
class UrlHelperTest {

    @Test
    @DisplayName("Failed api call test")
    void getResponseTest() {
        UrlHelper helper = new UrlHelper("https//:notarealthingbabadoi");
        StringBuilder response = helper.getPolygon("hej", "hello");
        assertNull(response);
    }
}