package com.aau.p3.platform.urlmanager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * How to use:
 * Add a method containing the specific endpoint you want to access
 * Use the getResponse method to make the get request
 * Make a UrlHelper constructor with the base url
 * Handle the response JSON in your own method not in this class
 */
public class UrlManager {
    protected static String BASE_URL;

    /**
     * Constructor for UrlManager
     * @param baseURL
     */
    public UrlManager(String baseURL){
        BASE_URL = baseURL;
    }

    /** Collective method, that with the given Url string performs and API call and returns the response.
     * Features the creation of connection, readings of information, security checks and termination.
     * @param urlString URL string to be created as a URL object
     */
    public StringBuilder getResponse(String urlString){
        try {
            // Create URL object from url string
            URL url = new URL(urlString);

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
