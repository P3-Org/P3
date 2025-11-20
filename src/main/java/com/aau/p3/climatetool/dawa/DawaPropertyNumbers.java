package com.aau.p3.climatetool.dawa;

import com.aau.p3.platform.urlmanager.UrlPropertyNumber;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Class that acts as a constructor to get Cadastre and Owner license with the help from UrlPropertyNumber
 */
public class DawaPropertyNumbers {
    private String cadastre = "";
    private String ownerLicense = "";

    /** Constructor for the DawaPropertyNumbers class
     * @param coordinates list of an x and y coordinate (also known as Latitude and Longitude)
     * Finds and stores info about cadastre and owner license
     * */
    public DawaPropertyNumbers(List<String> coordinates){
        // Get the response with the given query for the UrlPropertyNumber
        UrlPropertyNumber propertyCoordinates = new UrlPropertyNumber(coordinates);
        StringBuilder response = propertyCoordinates.getPropertyNumber();

        JSONArray jsonArray = new JSONArray(response.toString()); // Converts the response to a string which we then make to a JSON array.
        JSONObject item = jsonArray.getJSONObject(0); // Finds the first object in the array
        this.cadastre = item.optString("matrikelnr",""); // In the first object it finds the "matrikelnr" and saves it
        JSONObject ejerlav = item.getJSONObject("ejerlav"); // In object item it finds the object "ejerlav"
        this.ownerLicense = ejerlav.optString("kode",""); // In object ejerlav find "kode"
    }

    /** Getter method
     * @return returns string of the cadastre code
     */
    public String getCadastre() {
        return this.cadastre;
    }

    /** Getter method
     * @return returns string of the owner license code
     */
    public String getOwnerLicense() {
        return this.ownerLicense;
    }
}
