package com.aau.p3.platform.urlmanager;

import java.util.List;

/**
 * Class that takes list of x and y coordinates, creates Url and gets response from API call
 */
public class UrlPropertyNumber extends UrlManager {
    private final List<String> coordinates;

    /**
     * Constructor for the URL property number
     * @param coordinates list of the latitude and longitude coordinates
     */
    public UrlPropertyNumber(List<String> coordinates) {
        super("https://api.dataforsyningen.dk");
        this.coordinates = coordinates;
    }

    /**
     * Getter for the GET Response of propertyNumber
     * @return GET response
     */
    public StringBuilder getPropertyNumber() {
        // We extract the coordinates, to use them in the final Url
        String x = coordinates.get(0);
        String y = coordinates.get(1);
        String property_url = BASE_URL + "/jordstykker?x="+ x + "&y=" + y + "&srid=25832";

        return super.getResponse(property_url);
    }
}
