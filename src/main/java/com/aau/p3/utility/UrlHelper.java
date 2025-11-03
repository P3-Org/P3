package com.aau.p3.utility;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UrlHelper {
    private String BASE_URL;

    public void UrlHelper(String urlString){
        this.BASE_URL = urlString;
    }

    public StringBuilder getPolygon(String kode, String matrikel){
        String polygon_url = this.BASE_URL + "/"+ kode + "/" + matrikel + "?format=geojson";
        return this.getResponse(polygon_url);
    }

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
