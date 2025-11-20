package com.aau.p3.platform.urlmanager;

import java.util.List;

// Takes list of x and y coordinates, creates Url and gets response from API call
public class UrlPropertyNumber extends UrlManager {
    private final List<String> coordinates;
    public UrlPropertyNumber(List<String> coordinates) {
        super("https://api.dataforsyningen.dk");
        this.coordinates = coordinates;
    }

    public StringBuilder getPropertyNumber() {
        // We extract the coordinates, to use them in the final Url
        String y = coordinates.get(0);
        String x = coordinates.get(1);
        String property_url = BASE_URL + "/jordstykker?x="+ x + "&y=" + y;
        System.out.println(property_url);

        return super.getResponse(property_url);
    }
}
