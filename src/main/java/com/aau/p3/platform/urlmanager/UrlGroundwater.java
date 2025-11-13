package com.aau.p3.platform.urlmanager;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class UrlGroundwater extends UrlManager{
    private final String query;

    public UrlGroundwater(String query){
        super("https://api.dataforsyningen.dk");
        this.query = query;
    }
    public StringBuilder getUrlGroundwater(){
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8).replace("+", "%20");
        String urlGroundwater = BASE_URL + "/rest/hydro_model/v1.0/grundvandsstand/100m?punkt=" +
                encodedQuery +
                "&token=c6937f7f319698d502a27b3895d26d2d";

        System.out.println(urlGroundwater);
        return super.getResponse(urlGroundwater);
    }
}
