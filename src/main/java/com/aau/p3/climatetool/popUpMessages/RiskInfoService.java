package com.aau.p3.climatetool.popUpMessages;
import com.aau.p3.climatetool.utilities.ThresholdRepository;
import com.aau.p3.database.StaticThresholdRepository;

import java.util.ResourceBundle;

public class RiskInfoService {

    /**
     * Method that locates the proper .properties file, and returns the information stored in each property
     * @param filename the string that specifies which .properties file that should be read from
     * @return returns an object of RiskInfo with the correct information strings
     */
    public RiskInfo loadInfo(String filename) {
        ThresholdRepository thresholdRepo = new StaticThresholdRepository();
        ResourceBundle bundle = ResourceBundle.getBundle("UI/Drilldowns/"+filename);
        String general = bundle.getString("general") + "\n\n";
        String threshold = bundle.getString("threshold") + "\n\n";
        String calculation = bundle.getString("calculation") + "\n\n";
        String precaution = bundle.getString("precaution");

        threshold = formatString(threshold, thresholdRepo.getThreshold(filename));

      return new RiskInfo(general, threshold, calculation, precaution);

    }

    private String formatString(String description, double[] threshold){
        description = description.replace("{lowerThresh}", Double.toString(threshold[0]));
        description = description.replace("{upperThresh}", Double.toString(threshold[1]));
        return description;
    }
}
