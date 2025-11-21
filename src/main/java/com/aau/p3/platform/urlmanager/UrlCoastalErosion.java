package com.aau.p3.platform.urlmanager;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

// Takes query to complete URL for getResponse() call
public class UrlCoastalErosion extends UrlManager{
    // The number of meters from the risk, the property must be
    private static final int RISK_DISTANCE_FROM_PROPERTY = 250;
    private final String queryCoordinatePair;

    public UrlCoastalErosion(String queryCoordinatePair){
        super("https://gis.nst.dk");
        this.queryCoordinatePair = queryCoordinatePair;
    }
    public StringBuilder getUrlCoastalErosion(){
        // Encode with URLEncoder
        String urlCoordinates = URLEncoder.encode(queryCoordinatePair, StandardCharsets.UTF_8);

        String urlCoastalErosion = BASE_URL + "/server/rest/services/ekstern/KDI_KystAtlas/MapServer/2/query?geometry=" +
                urlCoordinates + "&geometryType=esriGeometryPoint&distance=" + RISK_DISTANCE_FROM_PROPERTY + "&spatialRel=esriSpatialRelIntersects&units=esriSRUnit_Meter&f=pjson";

        // Get and return response of API call
        return super.getResponse(urlCoastalErosion);
    }
}
