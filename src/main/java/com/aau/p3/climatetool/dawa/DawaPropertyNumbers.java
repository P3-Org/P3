package com.aau.p3.climatetool.dawa;

import com.aau.p3.platform.urlmanager.UrlPropertyNumber;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DawaPropertyNumbers {
    private List<List<Double>> polygon;
    private String cadastre = "";
    private String ownerLicense = "";

    /** Constructor for the DawaPropertyNumbers class
     * A list of an x and y coordinate
     * Finds and stores info about cadastre and owner license
     * */
    public DawaPropertyNumbers(List<String> coordinates){
        UrlPropertyNumber propertyCoordinates = new UrlPropertyNumber(coordinates);
        StringBuilder response = propertyCoordinates.getPropertyNumber();


        // Fetches all cadastre info, for the given coordinates, from the API
        Pattern cadastrePattern = Pattern.compile("\"matrikelnr\"\\s*:\\s*\"(.*?)\"");
        Matcher cadastreInfo = cadastrePattern.matcher(response.toString());

        // Fetches all owner license info, for the given coordinates, from the API
        Pattern ownerLicensePattern = Pattern.compile("\"ejerlav\"\\s*:\\s*\\{[^}]*?\"kode\"\\s*:\\s*(\\d+)");
        Matcher ownerLicenseInfo = ownerLicensePattern.matcher(response.toString());

        // Add the cadastreInfo to object field when found
        while (cadastreInfo.find()) {
            this.cadastre = cadastreInfo.group(1);
        }

        /* The first and only the first ownerLicenseInfo found is entered in the object field. This is because in
        geojson, multiple fields are labelled as "kode", but only the first on corresponds to the owner license */
        if (ownerLicenseInfo.find()) {
            this.ownerLicense = ownerLicenseInfo.group(1);
        }
    }

    /** Getter method
     * @return Returns string of the cadastre code
     */
    public String getCadastre() {
        return this.cadastre;
    }

    /** Getter method
     * @return Returns string of the owner license code
     */
    public String getOwnerLicense() {
        return this.ownerLicense;
    }
}
