package com.aau.p3.platform.urlmanager;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Class that takes query to complete URL for getResponse() call.
 */
public class UrlCoastalErosion extends UrlManager{
    // The number of meters from the risk, the property must be.
    private static final int RISK_DISTANCE_FROM_PROPERTY = 250;
    private final String queryCoordinatePair;

    /**
     * Constructor for URL coastal erosion.
     * @param queryCoordinatePair Pair of coordinates for the query.
     */
    public UrlCoastalErosion(String queryCoordinatePair){
        super("https://gis.nst.dk");
        this.queryCoordinatePair = queryCoordinatePair;
    }

    /**
     * Create URL from base url and query and get response from API.
     * @return response of API call in a StringBuilder object.
     */
    public StringBuilder GETUrlCoastalErosion(){
        // Encode with URLEncoder.
        String urlCoordinates = URLEncoder.encode(queryCoordinatePair, StandardCharsets.UTF_8);

        // Create the final URL for the API call.
        String urlString = BASE_URL + "/server/rest/services/ekstern/KDI_KystAtlas/MapServer/2/query?geometry=" +
                urlCoordinates + "&geometryType=esriGeometryPoint&distance=" + RISK_DISTANCE_FROM_PROPERTY + "&spatialRel=esriSpatialRelIntersects&units=esriSRUnit_Meter&f=pjson";

        // Return getResponse from parent class.
        return super.GETResponse(urlString);
    }
}
