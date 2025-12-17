package com.aau.p3.platform.urlmanager;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Class that takes query to complete URL for groundwater API.
 */
public class UrlGroundwater extends UrlManager{
    private final String query;

    /**
     * Constructor for UrlGroundWater class.
     * @param query added to baseURL, being a coordinate pair in string format.
     */
    public UrlGroundwater(String query) {
        super("https://api.dataforsyningen.dk");
        this.query = query;
    }

    /**
     * Method for creating URL string and performing API call.
     * @return GET response in a StringBuilder object.
     */
    public StringBuilder GETUrlGroundwater() {
        // Encode with URLEncoder
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8).replace("+", "%20");
        String urlString = BASE_URL + "/rest/hydro_model/v1.0/grundvandsstand/100m?punkt=POINT(" +
                encodedQuery + ")&token=c6937f7f319698d502a27b3895d26d2d";

        // Return getResponse from parent class
        return super.GETResponse(urlString);
    }
}
