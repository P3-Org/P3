package com.aau.p3.climatetool.dawa;

import com.aau.p3.platform.urlmanager.UrlAutoComplete;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DawaGetAddresses {
    private final List<String> addresses = new ArrayList<>();

    public DawaGetAddresses(String query){
        // Get the response with the given query
        UrlAutoComplete autoComplete = new UrlAutoComplete(query);
        StringBuilder response = autoComplete.getAutoComplete();

        JSONArray jsonArray = new JSONArray(response.toString()); // Converts the response to a string which we then make to a JSON array.

        for (int i = 0; i < jsonArray.length() && i < 15; i++){
            JSONObject item = jsonArray.getJSONObject(i); // Loops through the JSON array to find each object
            String address = item.optString("forslagstekst",""); // In each object it finds the "forslagstekst"
            addresses.add(address); // adds the address suggestion to our List
        }
    }
    // Method for returning the list of address suggestions.
    public List<String> getAddresses(){
        return addresses;
    }
}
