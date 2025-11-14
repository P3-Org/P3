package com.aau.p3.platform.urlmanager;

// Takes registry number and real property area, which through API call returns the polygon
public class UrlPolygon extends UrlManager {
    private final String ownerLicense;
    private final String cadastre;

    public UrlPolygon(String ownerLicense, String cadastre) {
        super("https://api.dataforsyningen.dk");
        this.ownerLicense = ownerLicense;
        this.cadastre = cadastre;
    }

    public StringBuilder getPolygon() {
        String polygon_url = BASE_URL + "/jordstykker/"+ ownerLicense + "/" + cadastre + "?format=geojson&srid=25832";
        return super.getResponse(polygon_url);
    }

}
