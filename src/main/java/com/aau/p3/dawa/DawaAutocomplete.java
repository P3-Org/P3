package com.aau.p3.dawa;

import java.io.BufferedReader;              // Reads text from http inputstream
import java.io.InputStreamReader;           // Converts byte streams (like HTTP responses) into character streams
import java.net.HttpURLConnection;          // Send HTTP requests and receive responses
import java.net.URL;                        // URL class
import java.net.URLEncoder;                 // Rewrites æøå to unicode for api search
import java.nio.charset.StandardCharsets;   // Standard character set
import java.util.ArrayList;                 // List
import java.util.List;                      // List
import java.util.regex.Matcher;             // Used to find matches of a regex pattern in a string (dont understand so no touch)
import java.util.regex.Pattern;             // Defines a compiled regular expression pattern (dont understand so no touch)


/**
* @author Batman
* */
public class DawaAutocomplete {

    private static final String BASE_URL = "https://api.dataforsyningen.dk/autocomplete";

    /**
     * @param query the search query
     * @return The addresses you have searched for
     */
    public List<String> autocomplete(String query) {
        // Lists to hold information on both addresses, aswell as x and y coordinates.
        List<String> addresses = new ArrayList<>();
        List<String> coordinates = new ArrayList<>();

        // Create url string with base URL + UTF_8 encoder, to ensure æ,ø and å are handled
        String urlString = BASE_URL + "?q=" + URLEncoder.encode(query, StandardCharsets.UTF_8);

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

                // Find all "forslagstekst"
                Pattern pattern = Pattern.compile("\"forslagstekst\"\\s*:\\s*\"(.*?)\"");
                Matcher addressMatcher = pattern.matcher(response.toString());

                // Find relevant longitude and latitude data
                Pattern longitude = Pattern.compile("\"x\"\\s*:\\s*(\\d+\\.\\d+)");
                Pattern latitude = Pattern.compile("\"y\"\\s*:\\s*(\\d+\\.\\d+)");
                Matcher xMatcher = longitude.matcher(response.toString());
                Matcher yMatcher = latitude.matcher(response.toString());

                // Add all relevant "forslagstekst" to "addresses" list
                while (addressMatcher.find()) {
                    addresses.add(addressMatcher.group(1));
                }

                // Add longitude and latitude data to "coordinates" list
                while (xMatcher.find() && yMatcher.find()) {
                    coordinates.add(xMatcher.group(1));
                    coordinates.add(yMatcher.group(1));
                }


            } else {
                System.out.println("GET request failed. Response code: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(coordinates);
        return coordinates;
    }
}
