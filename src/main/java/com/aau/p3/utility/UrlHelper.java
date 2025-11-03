package com.aau.p3.utility;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;


/*
*  * How to use:
 * Add a method containing the specific endpoint you want to access
 * Use the getResponse method to make the get request
 * Make a UrlHelper constructor with the base url
 * Handle the response JSON in your own method not in this class
* */


/**
 * @Author Batman
 * */

public class UrlHelper {
    private String BASE_URL;

    // Constructor that gives the object the base Url of the domain
    public UrlHelper(String urlString){
        this.BASE_URL = urlString;
    }

    // Takes rest of query and assembles final url, which it calls and gets response from.
    public StringBuilder getAutoComplete(String query){
        System.out.println(URLEncoder.encode(query, StandardCharsets.UTF_8));
        String urlString = BASE_URL + "/autocomplete?q=" + URLEncoder.encode(query, StandardCharsets.UTF_8);
        return this.getResponse(urlString);
    }

    // Takes list of x and y coordinates, creates Url and gets response from API call
    public StringBuilder getPropertyNo(List<String> Coordinates){
        // We extract the coordinates, to use them in the final Url
        String x = Coordinates.get(0);
        String y = Coordinates.get(1);
        String property_url = BASE_URL + "/jordstykker?x="+ x + "&y=" + y;

        return this.getResponse(property_url);
    }

    // Takes registry number and real property area, which through API call returns the polygon
    public StringBuilder getPolygon(String kode, String matrikel){
        String polygon_url = this.BASE_URL + "/jordstykker/"+ kode + "/" + matrikel + "?format=geojson";
        return this.getResponse(polygon_url);
    }


    /* Collective function, that with the given Url string performs and API call and returns the response
    * Features the creation of connection, readings of information, security checks and termination.
    */
    public StringBuilder getResponse(String urlString){
        try {
            // Create URL object from url string
            URL url = new URL(urlString);
            System.out.println(url);

            // Create connection and request "GET"
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Check if response was successful
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                // While there are still lines, append lines to our response.
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }

                // Closes connection when all lines are read
                in.close();

                return response;

            } else {
                System.out.println("GET request failed. Response code: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
