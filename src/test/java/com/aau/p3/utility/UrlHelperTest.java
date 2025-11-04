package com.aau.p3.utility;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.MalformedURLException;

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