package com.aau.p3.climatetool.dawa;

import com.aau.p3.platform.urlmanager.UrlPolygon;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class DawaPolygonForAddress{
    private List<List<Double>> polygon;
    private Integer bfeNumber;


    /**
     * Fills out the field of polygon with coordinates of the polygon
     * */
    public DawaPolygonForAddress(String ownerLicense, String cadastre){
        // Get response from urlhelper,with the search of owner license and cadastre information
        UrlPolygon dawaPolygon = new UrlPolygon(ownerLicense, cadastre);

        StringBuilder response = dawaPolygon.getPolygon();
        this.polygon = new ArrayList<>();

                JSONObject results = new JSONObject(response.toString());

                // Get the BFE number for the property
                JSONObject properties = results.getJSONObject("properties");
                this.bfeNumber = Integer.parseInt(properties.optString("bfenummer", ""));
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
     * @return Returns the nested double list with the coordinates of the polygon
     */
    public Integer getBfeNumber() { return this.bfeNumber; }

    public List<List<Double>> getPolygon() {
        return this.polygon;
    }
}