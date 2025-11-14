package com.aau.p3.climatetool.dawa;

import com.aau.p3.platform.urlmanager.UrlAutoComplete;
import org.json.JSONArray;
import org.json.JSONObject;

public class DawaGetType {
    private String type = new String();

    public String DawaGetType(UrlAutoComplete autoComplete){
        StringBuilder response = autoComplete.getAutoComplete();

        JSONArray jsonArray = new JSONArray(response.toString());
        JSONObject item = jsonArray.getJSONObject(0);
        this.type = item.optString("type","");
        System.out.println(type);
        return type;
    }
}
