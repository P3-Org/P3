package com.aau.p3.climatetool.dawa;

import com.aau.p3.platform.urlmanager.UrlAutoComplete;
import org.json.JSONArray;
import org.json.JSONObject;


public class DawaGetType {
    private final String type;

    /** Constructor for getting the type of search on addresses from the API dataforsyningen
     * @param query the search query
     */
    public DawaGetType(String query){
        // Get the response with the given query.
        UrlAutoComplete autoComplete = new UrlAutoComplete(query);
        StringBuilder response = autoComplete.getAutoComplete();

        JSONArray jsonArray = new JSONArray(response.toString()); // Converts the response to a string which we then make to a JSON array.
        JSONObject item = jsonArray.getJSONObject(0);
        this.type = item.optString("type","");
    }
    /** Getter method
     * @return the type of search as a String
     */
    public String getType(){
        return type;
    }
}
