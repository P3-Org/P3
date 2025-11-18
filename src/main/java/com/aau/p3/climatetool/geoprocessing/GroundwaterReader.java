package com.aau.p3.climatetool.geoprocessing;

import com.aau.p3.platform.urlmanager.UrlGroundwater;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that gets "h" values and "kote" values, to analyze distance to groundwater after certain weather conditions
 * @Author Batman
 */
public class GroundwaterReader {
    private double kote;
    private List<Double> hValues;
    private double distanceFromSurface;

    // Hent koordinater og brug den her linje til at formatere dem til groundwaterFetch
    // String wkt = String.format(java.util.Locale.US, "POINT (%.3f %.3f)", x, y);

    /**
     * @param query the search query, being the coordinates in EPSG:25832 format
     * Performs API call and gathers the "kote" value and the h values and stores them
     */
    public void groundwaterFetch(String query) {
        // Use URL manager to perform API call and get response
        UrlGroundwater groundwater = new UrlGroundwater(query);
        StringBuilder response = groundwater.getUrlGroundwater();

        // Get kote value
        kote = GroundwaterReader.extractKote(response);

        // Get h values
        hValues = GroundwaterReader.extractHValues(response);

        this.distanceFromSurface = kote - hValues.get(3);
    }

    /**
     * Method for constructing the different calls necessary to gather sample values from a property within a grid.
     * @param response The geoJSON response from the URL manager
     * @return a double of the kote value
     */
    public static Double extractKote(StringBuilder response) {
        // Get the double from the object "kote"
        JSONObject obj = new JSONObject(response.toString());
        double holder = obj.getDouble("kote");

        return holder;
    }

    /**
     * Method for constructing the different calls necessary to gather sample values from a property within a grid.
     *
     * @param response The geoJSON response from the URL manager
     * @return a list of h values
     */
    public static List<Double> extractHValues(StringBuilder response) {
        // Initialize hValues as an array list
        ArrayList<Double> holder = new ArrayList<>();
        JSONObject json = new JSONObject(response.toString());
        // Find the "samlet" array inside the "statistik" array of the geoJson
        JSONArray samletArray = json.getJSONObject("statistik").getJSONArray("samlet");

        // Extract the h values to the array list
        if (!samletArray.isEmpty()) {
            JSONObject hObject = samletArray.getJSONObject(0);
            String[] keys = {"h2", "h5", "h10", "h20", "h50", "h100"};
            for (String key : keys) {
                if (hObject.has(key)) {
                    holder.add(hObject.getDouble(key));
                }
            }
        }
        return holder;
    }

    // Getters
    public double getKote() { return this.kote; }

    public List<Double> getHValues() { return this.hValues; }

    public double getDistanceFromSurface() { return this.distanceFromSurface; }
}

