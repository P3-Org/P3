package com.aau.p3.platform.urlmanager;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

// Takes query to complete URL for getResponse() call
public class UrlGroundwater extends UrlManager{
    private final String query;

    public UrlGroundwater(String query){
        super("https://api.dataforsyningen.dk");
        this.query = query;
    }
    public StringBuilder getUrlGroundwater(){
        // Encode with URLEncoder
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8).replace("+", "%20");
        String urlGroundwater = BASE_URL + "/rest/hydro_model/v1.0/grundvandsstand/100m?punkt=POINT(" +
                encodedQuery +
                ")&token=c6937f7f319698d502a27b3895d26d2d";
        // Get and return response of API call
        return super.getResponse(urlGroundwater);
    }
}
