package com.aau.p3.platform.urlmanager;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Takes rest of the query and assembles final url, which it calls and gets response from
 */
public class UrlAutoComplete extends UrlManager {
    private final String query;

    /**
     * Constructor for URL auto complete
     * @param query the query added to baseURL
     */
    public UrlAutoComplete(String query){
        super("https://api.dataforsyningen.dk");
        this.query = query;
    }

    /**
     * Getter for the GET response
     * @return GET response
     */
    public StringBuilder getAutoComplete(){
        String urlString = BASE_URL + "/autocomplete?q=" + URLEncoder.encode(query, StandardCharsets.UTF_8) + "&srid=25832";
        return super.getResponse(urlString);
    }
}