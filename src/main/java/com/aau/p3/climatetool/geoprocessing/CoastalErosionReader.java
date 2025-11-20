package com.aau.p3.climatetool.geoprocessing;

import com.aau.p3.platform.urlmanager.UrlCoastalErosion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import static java.util.Collections.min;


public class CoastalErosionReader {
    private double riskNumber;
    public void coastalErosionFetch(String query) {
        UrlCoastalErosion test = new UrlCoastalErosion(query);
        StringBuilder response = test.getUrlCoastalErosion();
        System.out.println(response);
        extractValues(response);
    }

    public void extractValues(StringBuilder response){
        JSONObject obj = new JSONObject(response.toString());
        List<String> riskClass = new ArrayList<>();
        JSONArray features;
        if (!obj.isEmpty()) {
            features = obj.getJSONArray("features");


            for (int i = 0; i < features.length(); i++){
                JSONObject feature = features.getJSONObject(i);
                JSONObject attributes = feature.getJSONObject("attributes");
                String risk = attributes.getString("KLASSE");
                riskClass.add(risk);
            }
        }
        riskNumber = convertRiskToDouble(riskClass);
    }

    public double convertRiskToDouble(List<String> riskClass){
        List<Double> riskScores = new ArrayList<>();
        if (riskClass.isEmpty()) {
            return 1.5;
        } else {
            for(String risk : riskClass) {
                switch (risk) {
                    case "Meget Stort":
                        riskScores.add(-0.5);
                        break;
                    case "Stor":
                        riskScores.add(-0.1);
                        break;
                    case "Moderat":
                        riskScores.add(0.0);
                        break;
                    case "Lille":
                        riskScores.add(0.5);
                        break;
                    case "Fremrykning":
                        riskScores.add(1.0);
                        break;
                    default:
                        riskScores.add(null);
                        break;
                }
            }
        }
        return min(riskScores);
    }

    public double getRiskNumber(){
        return this.riskNumber;
    }

    public static void main(String[] args) {
        /*List<List<Double>> coords = List.of(
                List.of(446747.81234, 6270806.12345),
                List.of(446800.45678, 6270850.98765),
                List.of(446900.11111, 6270806.22222),
                List.of(446747.81234, 6270806.12345) // closing polygon
        );*/
        double[] coords = {
                446747.81234, 6270806.12345
        };

        String wkt = String.format(java.util.Locale.US, "%.3f, %.3f", coords[0], coords[1]);

        CoastalErosionReader test = new CoastalErosionReader();
        test.coastalErosionFetch(wkt);
        System.out.println(test.getRiskNumber());
    }

}
