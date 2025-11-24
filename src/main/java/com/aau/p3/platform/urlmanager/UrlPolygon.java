package com.aau.p3.platform.urlmanager;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Class that takes registry number and real property area, which through API call returns the polygon
 */
public class UrlPolygon extends UrlManager {
    private final String ownerLicense;
    private final String cadastre;

    /**
     * Constructor for the URL Polygon
     * @param ownerLicense one part of the query to be parsed to the API
     * @param cadastre the other part of the query to be parsed
     */
    public UrlPolygon(String ownerLicense, String cadastre) {
        super("https://api.dataforsyningen.dk");
        this.ownerLicense = ownerLicense;
        this.cadastre = cadastre;
    }

    /**
     * Getter for the GET response for the polygon
     * @return GET response
     */
    public StringBuilder getPolygon() {
        String polygon_url = BASE_URL + "/jordstykker/"+ ownerLicense + "/" + URLEncoder.encode(cadastre, StandardCharsets.UTF_8) + "?format=geojson&srid=25832";
        return super.getResponse(polygon_url);
    }
}
