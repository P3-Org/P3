package com.aau.p3.climatetool.groundwater;

import com.aau.p3.climatetool.dawa.DawaAutocomplete;
import com.aau.p3.climatetool.dawa.DawaPolygonForAddress;
import com.aau.p3.climatetool.dawa.DawaPropertyNumbers;
import com.aau.p3.platform.urlmanager.UrlGroundwater;
import com.aau.p3.platform.urlmanager.UrlManager;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//https://api.dataforsyningen.dk/rest/hydro_model/v1.0/grundvandsstand/100m?punkt=POINT(726000%206170000)&token=c6937f7f319698d502a27b3895d26d2d

public class Groundwater {
    private double kote;

    public String holder;
    public Groundwater(String query) {
        UrlGroundwater groundwater = new UrlGroundwater(query);
        System.out.println(groundwater.getUrlGroundwater().toString());
        StringBuilder response = groundwater.getUrlGroundwater();

        Pattern pattern = Pattern.compile("\"kote\":([0-9.]+)");
        Matcher matcher = pattern.matcher(response.toString());

        holder = matcher.find() ? matcher.group(1) : "";
        kote = Double.parseDouble(holder);
        System.out.println(kote);
    }

    public static void main(String[] args) {
        DawaAutocomplete addresse = new DawaAutocomplete("Læsøgade+18+9000");
        DawaPropertyNumbers addrNr = new DawaPropertyNumbers(addresse.getCoordinates());
        DawaPolygonForAddress addrPoly = new DawaPolygonForAddress(addrNr.getOwnerLicense(),addrNr.getCadastre());

        List<Double> coords = addrPoly.getPolygon().get(0);
        double x = coords.get(0);
        double y = coords.get(1);

        String wkt = String.format(java.util.Locale.US, "POINT (%.3f %.3f)", x, y);

        System.out.println(wkt);
        Groundwater test = new Groundwater(wkt);
    }
}

