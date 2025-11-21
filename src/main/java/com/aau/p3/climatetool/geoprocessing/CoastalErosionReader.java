package com.aau.p3.climatetool.geoprocessing;

import com.aau.p3.climatetool.utilities.RestDataReader;
import com.aau.p3.platform.urlmanager.UrlCoastalErosion;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Class for reading information about coastal erosion on a given address. Holds different methods that fetches and
 * processes data, to return for presentation.
 */
public class CoastalErosionReader implements RestDataReader {
    private List<Double> riskValueArray;

    /**
     * Method for constructing the different calls necessary to gather sample values from a property within a grid.
     * @param query String of query for the URL, is made from coordinates stringified before calling method.
     */
    @Override
    public void riskFetch(String query) {
        // Use URL class to make URL and return the response
        UrlCoastalErosion coastalErosion = new UrlCoastalErosion(query);
        StringBuilder response = coastalErosion.getUrlCoastalErosion();

        // Give extract values the JSON response to read from
        riskValueArray = extractValues(response);
    }

    /**
     * Method for extracting values from the given JSON, ultimately filling out the riskValueArray field
     * @param response The list of risk assessments for a property
     */
    @Override
    public List<Double> extractValues(StringBuilder response) {
        // Make the response into a JSON object
        JSONObject responseObject = new JSONObject(response.toString());
        List<String> riskSeverityList = new ArrayList<>();

        // If the response has content, get the JSON array "features", which holds information.
        // If the response has no content, empty array is intialized, meaning no risk of coastal erosion.

        if (!responseObject.isEmpty()) {
            JSONArray featuresArray = responseObject.getJSONArray("features");

            for (int i = 0; i < featuresArray.length(); i++){
                // For each feature element, get the JSON object attribute and the value from key "KLASSE", add to list
                JSONObject feature = featuresArray.getJSONObject(i);
                JSONObject attributes = feature.getJSONObject("attributes");
                String riskSeverity = attributes.getString("KLASSE");
                riskSeverityList.add(riskSeverity);
            }
        }
        // Convert string values to doubles and assign to private field
        return convertSeverityToValue(riskSeverityList);
    }

    /**
     * Methods, which converts risk serverity descriptions to double values
     * @param riskSeverityList List of strings, with serverity descriptions
     * @return List of doubles with serverity values
     */
    public List<Double> convertSeverityToValue(List<String> riskSeverityList){
        List<Double> riskScores = new ArrayList<>();

        // If list is empty, no risks for coastal erosioan was found.
        // Hence, the max score is appended to the empty array, as the only element
        if (riskSeverityList.isEmpty()) {
            riskScores.add(8.0);
        } else {
            for(String risk : riskSeverityList) {
                switch (risk) {
                    case "Meget Stort":
                        riskScores.add(0.0);
                        break;
                    case "Stort":
                        riskScores.add(2.0);
                        break;
                    case "Moderat":
                        riskScores.add(3.0);
                        break;
                    case "Lille":
                        riskScores.add(4.0);
                        break;
                    case "Fremrykning":
                        riskScores.add(7.0);
                        break;
                    default:
                        break;
                }
            }
        }
        return riskScores;
    }

    public String convertValueToString(double value) {
        return switch ((int) value) {
            case 8 -> "Ingen risiko";
            case 7 -> "Fremrykning";
            case 4 -> "Lille risiko";
            case 3 -> "Moderat risiko";
            case 2 -> "Stor risiko";
            case 0 -> "Meget stor risiko";
            default -> "";
        };
    }

    // Getter
    public List<Double> getRiskValueArray(){
        return this.riskValueArray;
    }
}
