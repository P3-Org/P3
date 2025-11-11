package com.aau.p3.climatetool.groundwater;

import com.aau.p3.climatetool.dawa.DawaAutocomplete;
import com.aau.p3.platform.urlmanager.UrlGroundwater;
import com.aau.p3.platform.urlmanager.UrlManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//https://api.dataforsyningen.dk/rest/hydro_model/v1.0/grundvandsstand/100m?punkt=POINT(726000%206170000)&token=c6937f7f319698d502a27b3895d26d2d

public class Groundwater {

    public String holder;
    public Groundwater(String query) {
        UrlGroundwater groundwater = new UrlGroundwater(query);
        System.out.println(groundwater.getUrlGroundwater().toString());
        StringBuilder response = groundwater.getUrlGroundwater();

        Pattern pattern = Pattern.compile("\"kote\":([0-9.]+)");
        Matcher matcher = pattern.matcher(response.toString());

        holder = matcher.find() ? matcher.group(1) : "";
        System.out.println(holder);
    }

    public static void main(String[] args) {
        Groundwater test = new Groundwater("POINT(726000%206170000)");
    }
}
