package com.aau.p3.platform.urlmanager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

// Takes query to complete URL for getResponse() call
public class UrlCoastalErosion extends UrlManager{
    private final String query;
    private final int distance = 200;

    public UrlCoastalErosion(String query){
        super("https://gis.nst.dk");
        this.query = query;
    }
    public StringBuilder getUrlCoastalErosion(){
        // Encode with URLEncoder
        String urlEncodedGeoJson = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String urlCoastalErosion = BASE_URL + "/server/rest/services/ekstern/KDI_KystAtlas/MapServer/2/query?geometry=" +
                urlEncodedGeoJson + "&geometryType=esriGeometryPoint&distance=" + distance + "&spatialRel=esriSpatialRelIntersects&units=esriSRUnit_Meter&f=pjson";

        System.out.println(urlCoastalErosion);
        // Get and return response of API call
        return super.getResponse(urlCoastalErosion);
    }
}
