package com.aau.p3.climatetool.dawa;

import java.util.ArrayList;
import java.util.List;
import com.aau.p3.platform.urlmanager.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class DawaGetCoordinates {
    private final List<String> coordinates = new ArrayList<>();
    /**
     * @param query the search query
     * Assigns the coordinates field with x and y values
     */
    public DawaGetCoordinates(String query) {
        // Get the response with the given query
        UrlAutoComplete autoComplete = new UrlAutoComplete(query);
        StringBuilder response = autoComplete.getAutoComplete();

        JSONArray jsonArray = new JSONArray(response.toString()); // Converts the response to a string which we then make to a JSON array.
        JSONObject item = jsonArray.getJSONObject(0); // Finds the first object in the array
        JSONObject data = item.getJSONObject("data"); // In the Object item, get the Object data with the key: "data".
        String coordinateX = data.optString("x", ""); // In the data object, save the string "x"
        String coordinateY = data.optString("y", ""); // In the data object, save the string "y"
        coordinates.add(coordinateY); // adds the y coordinate to our list
        coordinates.add(coordinateX); // adds the x coordinate to our List
    }
    // Method for returning the coordinates.
    public List<String> getCoordinates() {
        return coordinates;
    }
}
