package com.aau.p3.climatetool.groundwater;

import com.aau.p3.platform.urlmanager.UrlGroundwater;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Groundwater {

    public Groundwater(String query) {
        String holder;

        UrlGroundwater groundwater = new UrlGroundwater(query);
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
