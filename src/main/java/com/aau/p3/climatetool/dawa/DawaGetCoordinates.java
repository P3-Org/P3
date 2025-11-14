package com.aau.p3.climatetool.dawa;

import java.util.ArrayList;                 // List
import java.util.List;                      // List
import java.util.regex.Matcher;             // Used to find matches of a regex pattern in a string (dont understand so no touch)
import java.util.regex.Pattern;             // Defines a compiled regular expression pattern (dont understand so no touch)
import com.aau.p3.platform.urlmanager.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class DawaGetCoordinates {
    List<String> coordinates = new ArrayList<>();
    /**
     * @param query the search query
     * Assings the coordinates field with x and y values
     */
    public DawaGetCoordinates(String query) {
        // Lists to hold information on both addresses, aswell as x and y coordinates.
        UrlAutoComplete autoComplete = new UrlAutoComplete(query);
        StringBuilder response = autoComplete.getAutoComplete();

        JSONArray jsonArray = new JSONArray(response.toString());
        JSONObject item = jsonArray.getJSONObject(0);
        JSONObject data = item.getJSONObject("data");
        String coordinateX = data.optString("x", "");
        String coordinateY = data.optString("y", "");
        coordinates.add(coordinateX);
        coordinates.add(coordinateY);
        System.out.println(coordinates);



        // Find all "forslagstekst"
        //        Pattern pattern = Pattern.compile("\"forslagstekst\"\\s*:\\s*\"(.*x?)\"");
        //        Matcher addressMatcher = pattern.matcher(response.toString());

                // Find relevant longitude and latitude data
        //        Pattern longitude = Pattern.compile("\"x\"\\s*:\\s*(\\d+\\.\\d+)");
        //        Pattern latitude = Pattern.compile("\"y\"\\s*:\\s*(\\d+\\.\\d+)");
        //        Matcher xMatcher = longitude.matcher(response.toString());
        //        Matcher yMatcher = latitude.matcher(response.toString());

                // Add longitude and latitude data to "coordinates" list
        //        while (xMatcher.find() && yMatcher.find()) {
        //            coordinates.add(xMatcher.group(1));
        //            coordinates.add(yMatcher.group(1));
        //        }
        //System.out.println(coordinates);
    }

    public List<String> getCoordinates() {
        return coordinates;
    }
}
