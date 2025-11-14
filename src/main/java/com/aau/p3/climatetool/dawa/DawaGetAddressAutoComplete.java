package com.aau.p3.climatetool.dawa;

import com.aau.p3.platform.urlmanager.UrlAutoComplete;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DawaGetAddressAutoComplete {

    private final List<String> addresses = new ArrayList<>();


    public List<String> DawaGetAddressAutocomplete(UrlAutoComplete autoComplete){
        StringBuilder response = autoComplete.getAutoComplete();
        System.out.println(response);

        JSONArray jsonArray = new JSONArray(response.toString());

        for (int i = 0; i < jsonArray.length() && i < 10; i++){
            JSONObject item = jsonArray.getJSONObject(i);
            String address = item.optString("forslagstekst","");
            //System.out.println(address);
            addresses.add(address);
        }
        return addresses;


    }



}
