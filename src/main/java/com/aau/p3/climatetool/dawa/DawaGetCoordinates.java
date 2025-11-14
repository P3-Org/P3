package com.aau.p3.climatetool.dawa;

import java.util.ArrayList;
import java.util.List;
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
    }

    public List<String> getCoordinates() {
        return coordinates;
    }
}
