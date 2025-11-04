package com.aau.p3.dawa;

import com.aau.p3.utility.UrlHelper;
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
 * */
public class DawaPolygonForAddress{
    private List<List<Double>> polygon;
    String matrikel = "";
    String kode = "";
    UrlHelper urlhelper = new UrlHelper("https://api.dataforsyningen.dk");

    /**
     * @Param A list of an x and y coordinate
     * @TODO Change the "kode" so it takes the correct one
     * */
    public void getPropertyNo(List<String> Coordinates){
        StringBuilder response = urlhelper.getPropertyNo(Coordinates);

        // Find all "Matrikelnr"
        Pattern pattern = Pattern.compile("\"matrikelnr\"\\s*:\\s*\"(.*?)\"");
        Matcher matrikelNr = pattern.matcher(response.toString());

        Pattern kodepattern = Pattern.compile("\"ejerlav\"\\s*:\\s*\\{[^}]*?\"kode\"\\s*:\\s*(\\d+)");
        Matcher ejerlavkode = kodepattern.matcher(response.toString());

        while (matrikelNr.find()) {
            matrikel = matrikelNr.group(1);
        }

        if (ejerlavkode.find()) {
            kode = ejerlavkode.group(1);
        }


        System.out.println(matrikel);
        System.out.println(kode);
        }

    /**
     * @return List of coordinates of the polygon in a WGS84 format
     * */
    private List<List<Double>> GETPolygon(){
        StringBuilder response = urlhelper.getPolygon(this.kode, this.matrikel);
        this.polygon = new ArrayList<>();

                JSONObject results = new JSONObject(response.toString());


                // Get the "geometry" object
                JSONObject geometry = results.getJSONObject("geometry");

                // Get the "coordinates" array (This is the triple-nested array structure)
                JSONArray coordinatesRoot = geometry.getJSONArray("coordinates");

                for (int i = 0; i < coordinatesRoot.length(); i++) {
                    JSONArray ringArray = coordinatesRoot.getJSONArray(i); // This is the first inner list

                    // Middle loop: Points within a ring
                    for (int j = 0; j < ringArray.length(); j++) {
                        JSONArray pointArray = ringArray.getJSONArray(j); // This is the [x(lon), y(lat)] pair

                        List<Double> javaPoint = new ArrayList<>();
                        double longitude = pointArray.getDouble(0);
                        double latitude = pointArray.getDouble(1);

                        javaPoint.add(longitude);
                        javaPoint.add(latitude);
                        this.polygon.add(javaPoint);
                    }
                }


        System.out.println(polygon);
        return this.polygon;
    };

    public List<List<Double>> getPolygon(){
        return GETPolygon();
    };

}