package com.aau.p3.platform.urlmanager;

public class UrlGroundwater extends UrlManager{
    private final String query;

    public UrlGroundwater(String query){
        super("https://api.dataforsyningen.dk");
        this.query = query;
    }
    public StringBuilder getUrlGroundwater(){
        String urlGroundwater = BASE_URL + "/rest/hydro_model/v1.0/grundvandsstand/100m?punkt=" +
                query + "&token=c6937f7f319698d502a27b3895d26d2d";
        System.out.println(urlGroundwater);
        return super.getResponse(urlGroundwater);
    }

}
