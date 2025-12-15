package com.aau.p3.climatetool.dawa;

import java.util.ArrayList;
import java.util.List;
import com.aau.p3.platform.urlmanager.UrlAutoComplete;
import org.json.JSONArray;
import org.json.JSONObject;

/** Class that acts as a constructor to get the Latitude and Longitude coordinates with the help from the UrlAutoComplete class */
public class DawaGetLatLong {
    private final List<String> latLong = new ArrayList<>();

    /** Constructor for getting the coordinates from the API dataforsyningen
     * @param query the search query
     */
    public DawaGetLatLong(String query) {
        // Get the response with the given query
        JSONObject data = getJsonObject(query);

        String coordinateX = data.optString("x", ""); // In the data object, save the string "x"
        String coordinateY = data.optString("y", ""); // In the data object, save the string "y"

        latLong.add(coordinateY); // adds the y coordinate to our list
        latLong.add(coordinateX); // adds the x coordinate to our List

    }

    /** Helper function that extracts the key with name "data" from the json response
     * @param query the search query
     * @return the information from the json object associated to key "data"
     * */
    private static JSONObject getJsonObject(String query) {
        UrlAutoComplete autoCompleteLatLong = new UrlAutoComplete(query);
        StringBuilder response = autoCompleteLatLong.GETAutoCompleteLatLong(); // Uses the Latitude Longitude version of auto complete for those coordinates

        JSONArray jsonArray = new JSONArray(response.toString()); // Converts the response to a string which we then make to a JSON array.
        JSONObject item = jsonArray.getJSONObject(0); // Finds the first object in the array
        JSONObject data = item.getJSONObject("data"); // In the Object item, get the Object data with the key: "data".
        return data;
    }

    /** Getter method for the coordinates.
     * @return LatLong coordinates from the class
     */
    public List<String> getLatLong() {
        return latLong;
    }
}
