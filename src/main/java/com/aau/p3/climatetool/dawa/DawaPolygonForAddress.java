package com.aau.p3.climatetool.dawa;

import com.aau.p3.platform.urlmanager.UrlPolygon;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that acts as a constructor to get the Polygon coordinates with the help from UrlPolygon class
 */
public class DawaPolygonForAddress{
    private final List<List<Double>> polygon = new ArrayList<>();

    /** Constructor for getting the polygon from the API dataforsyningen.
     * Fills out the field of polygon with coordinates of the polygon
     * @param ownerLicense one part of the query needed for the API
     * @param cadastre the other part needed for the query
     */
    public DawaPolygonForAddress(String ownerLicense, String cadastre){
        // Get response from urlhelper, with the search of owner license and cadastre information
        UrlPolygon dawaPolygon = new UrlPolygon(ownerLicense, cadastre);

        StringBuilder response = dawaPolygon.GETPolygon();

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

                // Add x and y to javaPoint and insert as a list inside polygon
                javaPoint.add(longitude);
                javaPoint.add(latitude);
                this.polygon.add(javaPoint);
            }
        }
    }
    /** Getter method
     * @return returns the nested double list with the coordinates of the polygon
     */
    public List<List<Double>> getPolygon() {
        return this.polygon;
    }
}