package com.aau.p3.climatetool.dawa;

import com.aau.p3.platform.urlmanager.UrlPropertyNumber;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Class that acts as a constructor to get Cadastre and Owner license with the help from UrlPropertyNumber
 */
public class DawaPropertyNumbers {
    private String cadastre;
    private String ownerLicense;

    /** Constructor for the DawaPropertyNumbers class
     * @param coordinates list of an x and y coordinate (also known as easting and northing)
     * Finds and stores info about cadastre and owner license
     * */
    public DawaPropertyNumbers(List<String> coordinates) {
        // Get the response with the given query for the UrlPropertyNumber
        UrlPropertyNumber propertyCoordinates = new UrlPropertyNumber(coordinates);
        StringBuilder response = propertyCoordinates.GETPropertyNumber();

        // Converts the response to a string which we then make to a JSON array.
        JSONArray jsonArray = new JSONArray(response.toString());

        // Finds the first object in the array.
        JSONObject item = jsonArray.getJSONObject(0);

        // In the first object it finds the "matrikelnr" and saves it.
        this.cadastre = item.optString("matrikelnr","");

        // In object item it finds the object "ejerlav".
        JSONObject ejerlav = item.getJSONObject("ejerlav");

        // In object ejerlav find "kode".
        this.ownerLicense = ejerlav.optString("kode","");
    }

    /** Getter method
     * @return string of the cadastre code
     */
    public String getCadastre() {
        return this.cadastre;
    }

    /** Getter method
     * @return string of the owner license code
     */
    public String getOwnerLicense() {
        return this.ownerLicense;
    }
}
