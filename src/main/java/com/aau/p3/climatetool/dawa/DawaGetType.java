package com.aau.p3.climatetool.dawa;

import com.aau.p3.platform.urlmanager.UrlAutoComplete;
import org.json.JSONArray;
import org.json.JSONObject;


public class DawaGetType {
    private final String type;

    public DawaGetType(String query){
        // Get the response with the given query.
        UrlAutoComplete autoComplete = new UrlAutoComplete(query);
        StringBuilder response = autoComplete.getAutoComplete();

        JSONArray jsonArray = new JSONArray(response.toString()); // Converts the response to a string which we then make to a JSON array.
        JSONObject item = jsonArray.getJSONObject(0);
        this.type = item.optString("type","");
    }
    // Method for returning the type.
    public String getType(){
        return type;
    }
}
