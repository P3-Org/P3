package com.aau.p3.climatetool.dawa;

import com.aau.p3.platform.urlmanager.UrlAutoComplete;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/** Class that acts as a constructor to get suggested addresses with the help from the UrlAutoComplete class */
public class DawaGetAddresses {
    private final List<String> addresses = new ArrayList<>();

    /** Method for getting the addresses from the API dataforsyningen that will be auto suggested for the user
     * @param query the search query
     */
    public DawaGetAddresses(String query) {
        // Get the response with the given query
        UrlAutoComplete autoComplete = new UrlAutoComplete(query);
        StringBuilder response = autoComplete.GETAutoComplete();

        JSONArray jsonArray = new JSONArray(response.toString()); // Converts the response to a string which we then make to a JSON array.

        for (int i = 0; i < jsonArray.length() && i < 15; i++) {
            JSONObject item = jsonArray.getJSONObject(i); // Loops through the JSON array to find each object
            String address = item.optString("forslagstekst",""); // In each object it finds the "forslagstekst"
            addresses.add(address); // adds the address suggestion to our List
        }
    }

    /** Getter method for the list of address suggestions.
     * @return list of addresses
     */
    public List<String> getAddresses() {
        return addresses;
    }
}
