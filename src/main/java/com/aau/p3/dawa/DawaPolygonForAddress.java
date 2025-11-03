package com.aau.p3.dawa;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author Batman
 * @TODO søg efter href istedet
 * @TODO Lav comments
 * */
public class DawaPolygonForAddress{
    private List<List<Double>> polygon;
    String hej = "";
    private static final String getMatrikelnr = "https://api.dataforsyningen.dk/jordstykker";
    String matrikel = "";
    String kode = "";

    /**
     * @Param todo
     * */
    public void dawapolygonforaddress(List<String> Coordinates){
            String x = Coordinates.get(1);
            String y = Coordinates.get(2);

        String urlString = getMatrikelnr + "?x="+ x + "&y=" + y;

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
                System.out.println(response);

                // Closes connection when all lines are read
                in.close();

                // Find all "Matrikelnr"
                Pattern pattern = Pattern.compile("\"matrikelnr\"\\s*:\\s*\"(.*?)\"");
                Matcher matrikelNr = pattern.matcher(response.toString());

                Pattern kodepattern = Pattern.compile("\"kode\"\\s*:\\s*\"(.*?)\"");
                Matcher ejerlavkode = kodepattern.matcher(response.toString());

                while (matrikelNr.find()) {
                    matrikel = matrikelNr.group(1);
                }

                while (ejerlavkode.find()) {
                    kode = ejerlavkode.group(1);
                }

                System.out.println(matrikel);
                System.out.println(kode);

            } else {
                System.out.println("GET request failed. Response code: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

            }


    private List<List<Double>> GETPolygon(){
        String polygonURL = getMatrikelnr + "/"+ this.kode + "/" + this.matrikel + "?format=geojson";
        try {
            // Create URL object from url string
            URL url = new URL(polygonURL);
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
                System.out.println(response);

                // Closes connection when all lines are read
                in.close();



                JSONObject results = new JSONObject(response.toString());
                // 2. Get the "geometry" object
                JSONObject geometry = results.getJSONObject("geometry");

                // 3. Get the "coordinates" array (This is the triple-nested array structure)
                JSONArray coordinatesRoot = geometry.getJSONArray("coordinates");

                for (int i = 0; i < coordinatesRoot.length(); i++) {
                    JSONArray ringArray = coordinatesRoot.getJSONArray(i); // This is the first inner list

                    // Middle loop: Points within a ring
                    for (int j = 0; j < ringArray.length(); j++) {
                        JSONArray pointArray = ringArray.getJSONArray(j); // This is the [lng, lat] pair
                        List<Double> javaPoint = new ArrayList<>();

                        // Inner logic: Longitude and Latitude values (which are Doubles)
                        // GeoJSON coordinates are [longitude, latitude]
                        double longitude = pointArray.getDouble(0);
                        double latitude = pointArray.getDouble(1);

                        javaPoint.add(longitude);
                        javaPoint.add(latitude);
                        this.polygon.add(javaPoint);
                    }
                }


            } else {
                System.out.println("GET request failed. Response code: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println(polygon);
        return this.polygon;
    };

    public List<List<Double>> getPolygon(){
        return GETPolygon();
    };

}