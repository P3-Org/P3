package com.aau.p3.platform.urlmanager;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

// Takes rest of query and assembles final url, which it calls and gets response from
public class AutoComplete extends UrlManager {
    private final String query;
    public AutoComplete(String urlString, String query){
        super(urlString);
        this.query = query;
    }
    public StringBuilder getAutoComplete(){
        String urlString = super.BASE_URL + "/autocomplete?q=" + URLEncoder.encode(query, StandardCharsets.UTF_8);
        return super.getResponse(urlString);
    }
}