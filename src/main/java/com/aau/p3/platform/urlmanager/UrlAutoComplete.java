package com.aau.p3.platform.urlmanager;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Takes rest of the query and assembles final url, which it gets API response from.
 */
public class UrlAutoComplete extends UrlManager {
    private final String query;

    /**
     * Constructor for URL auto complete.
     * @param query the query added to baseURL.
     */
    public UrlAutoComplete(String query) {
        super("https://api.dataforsyningen.dk");
        this.query = query;
    }

    /**
     * Creates URL string and performs API call for easting norting coordinates.
     * @return GET response in a StringBuilder object.
     */
    public StringBuilder GETAutoComplete() {
        // Construct URL string from base url and given query.
        String urlString = BASE_URL + "/autocomplete?q=" + URLEncoder.encode(query, StandardCharsets.UTF_8) + "&srid=25832";

        // Return getResponse from parent class.
        return super.GETResponse(urlString);
    }
    /**
     * Creates URL string and performs API call for latitude longitude coordinates.
     * @return GET response in a StringBuilder object.
     */
    public StringBuilder GETAutoCompleteLatLong() {
        String urlString = BASE_URL + "/autocomplete?q=" + URLEncoder.encode(query, StandardCharsets.UTF_8);
        return super.GETResponse(urlString);
    }
}