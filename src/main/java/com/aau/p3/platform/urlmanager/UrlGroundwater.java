package com.aau.p3.platform.urlmanager;

public class UrlGroundwater extends UrlManager{
    private final String query;

    public UrlGroundwater(String query){
        this.BASE_URL = "https://api.dataforsyningen.dk/rest/hydro_model/v1.0/grundvandsstand/100m?punkt=";
        this.query = query;
    }
    public StringBuilder getUrlGroundwater(){
        String urlGroundwater = BASE_URL + query + "&token=c6937f7f319698d502a27b3895d26d2d";
        System.out.println(urlGroundwater);
        return super.getResponse(urlGroundwater);
    }

}
