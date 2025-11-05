package com.aau.p3.climatetool.dawa;

import com.aau.p3.platform.utilities.UrlHelper;

import java.util.ArrayList;                 // List
import java.util.List;                      // List
import java.util.regex.Matcher;             // Used to find matches of a regex pattern in a string (dont understand so no touch)
import java.util.regex.Pattern;             // Defines a compiled regular expression pattern (dont understand so no touch)


/**
* @author Batman
* */
public class DawaAutocomplete {



    /**
     * @param query the search query
     * @return The addresses you have searched for
     */
    public List<String> autocomplete(String query) {
        // Lists to hold information on both addresses, aswell as x and y coordinates.
        List<String> addresses = new ArrayList<>();
        List<String> coordinates = new ArrayList<>();
        UrlHelper urlhelper = new UrlHelper("https://api.dataforsyningen.dk");

        StringBuilder response = urlhelper.getAutoComplete(query);

                // Find all "forslagstekst"
                Pattern pattern = Pattern.compile("\"forslagstekst\"\\s*:\\s*\"(.*?)\"");
                Matcher addressMatcher = pattern.matcher(response.toString());

                // Find relevant longitude and latitude data
                Pattern longitude = Pattern.compile("\"x\"\\s*:\\s*(\\d+\\.\\d+)");
                Pattern latitude = Pattern.compile("\"y\"\\s*:\\s*(\\d+\\.\\d+)");
                Matcher xMatcher = longitude.matcher(response.toString());
                Matcher yMatcher = latitude.matcher(response.toString());

                // Add all relevant "forslagstekst" to "addresses" list
                while (addressMatcher.find()) {
                    addresses.add(addressMatcher.group(1));
                }

                // Add longitude and latitude data to "coordinates" list
                while (xMatcher.find() && yMatcher.find()) {
                    coordinates.add(xMatcher.group(1));
                    coordinates.add(yMatcher.group(1));
                }

        System.out.println(coordinates);
        return coordinates;
    }
}
