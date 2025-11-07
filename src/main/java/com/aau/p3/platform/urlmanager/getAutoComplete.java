package com.aau.p3.platform.urlmanager;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class getAutoComplete extends UrlHelper {
    public getAutoComplete(String query, String urlString){
        super(urlString);
        System.out.println(URLEncoder.encode(query, StandardCharsets.UTF_8));
        String urlString = BASE_URL + "/autocomplete?q=" + URLEncoder.encode(query, StandardCharsets.UTF_8);
        return this.getResponse(urlString);
    }
}