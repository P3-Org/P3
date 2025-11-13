package com.aau.p3.climatetool.groundwater;

import com.aau.p3.climatetool.dawa.DawaAutocomplete;
import com.aau.p3.climatetool.dawa.DawaPolygonForAddress;
import com.aau.p3.climatetool.dawa.DawaPropertyNumbers;
import com.aau.p3.platform.urlmanager.UrlGroundwater;
import com.aau.p3.platform.urlmanager.UrlManager;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//https://api.dataforsyningen.dk/rest/hydro_model/v1.0/grundvandsstand/100m?punkt=POINT(726000%206170000)&token=c6937f7f319698d502a27b3895d26d2d

public class Groundwater {
    private double kote;
    private List<Double> hValues;

    /**
     * @param query the search query
     * Gathers the "kote" value and the h values and stores them
     */
    public Groundwater(String query) {
        // Use URL manager to perform API call and get response
        UrlGroundwater groundwater = new UrlGroundwater(query);
        System.out.println(groundwater.getUrlGroundwater().toString());
        StringBuilder response = groundwater.getUrlGroundwater();

        // Use regex to extract kote value
        Pattern pattern = Pattern.compile("\"kote\":([0-9.]+)");
        Matcher matcher = pattern.matcher(response.toString());
        // If not empty, insert into hold
        String holder = matcher.find() ? matcher.group(1) : "";
        // Parse it to a double
        kote = Double.parseDouble(holder);

        // Initialize hValues as an array list
        hValues = new ArrayList<>();
        JSONObject json = new JSONObject(response.toString());
        // Find the "samlet" array inside the "statistik" array of the geoJson
        JSONArray samletArray = json.getJSONObject("statistik").getJSONArray("samlet");

        // Extract the h values to the array list
        if (!samletArray.isEmpty()) {
            JSONObject hObject = samletArray.getJSONObject(0);
            String[] keys = {"h2", "h5", "h10", "h20", "h50", "h100"};
            for (String key : keys) {
                if (hObject.has(key)) {
                    hValues.add(hObject.getDouble(key));
                }
            }
        }
    }

    // lege main
    public static void main(String[] args) {
        DawaAutocomplete addresse = new DawaAutocomplete("Tryvej+30+9750");
        DawaPropertyNumbers addrNr = new DawaPropertyNumbers(addresse.getCoordinates());
        DawaPolygonForAddress addrPoly = new DawaPolygonForAddress(addrNr.getOwnerLicense(),addrNr.getCadastre());

        List<Double> coords = addrPoly.getPolygon().get(0);
        double x = coords.get(0);
        double y = coords.get(1);

        String wkt = String.format(java.util.Locale.US, "POINT (%.3f %.3f)", x, y);

        Groundwater test = new Groundwater(wkt);
        System.out.println(test.hValues.get(0));
        System.out.println(test.kote);
        System.out.println(test.kote - test.hValues.get(3));
    }
}

