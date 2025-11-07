package com.aau.p3.platform.urlmanager;

import java.util.List;

// Takes list of x and y coordinates, creates Url and gets response from API call
public class PropertyNumber extends UrlManager {
    private final List<String> coordinates;
    public PropertyNumber(String urlString, List<String> coordinates) {
        super(urlString);
        this.coordinates = coordinates;
    }

    public StringBuilder getPropertyNumber() {
        // We extract the coordinates, to use them in the final Url
        String x = coordinates.get(0);
        String y = coordinates.get(1);
        String property_url = super.BASE_URL + "/jordstykker?x="+ x + "&y=" + y;

        return super.getResponse(property_url);
    }
}
