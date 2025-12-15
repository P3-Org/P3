package com.aau.p3.climatetool.popUpMessages;

import com.aau.p3.database.ThresholdRepository;
import com.aau.p3.climatetool.utilities.ThresholdRepositoryInterface;

import java.util.ResourceBundle;

/** A class, whose instances serve as helpers. These helpers make use of the methods
 *  to get hold of information for the systems pop-up boxes */
public class RiskInfoService {
    /**
     * Method that locates the proper .properties file, and returns the information stored in each property.
     * @param filename The string that specifies which .properties file the method should read from.
     * @return An object of RiskInfo with the correct information strings.
     */
    public RiskInfo loadInfo(String filename) {
        // Sets up a repository containing the threshold values sourced from the database
        ThresholdRepositoryInterface thresholdRepo = new ThresholdRepository();

        // Using the filename, gets the string for each section, from the drilldown files
        ResourceBundle bundle = ResourceBundle.getBundle("ui/drilldowns/" +filename);
        String general = bundle.getString("general") + "\n\n";
        String threshold = bundle.getString("threshold") + "\n\n";
        String calculation = bundle.getString("calculation") + "\n\n";
        String precaution = bundle.getString("precaution");

        // Furthermore, replaces placeholder values
        threshold = formatString(threshold, thresholdRepo.getThreshold(filename));

        return new RiskInfo(general, threshold, calculation, precaution);
    }

    /**
     * Method that takes a description, and replaces the placeholders with their intended values.
     * @param description The string, which will be scanned for placeholders.
     * @param threshold A list of the values that will replace the placeholders.
     * @return The new description, with the proper values parsed, instead of placeholders.
     */
    private String formatString(String description, double[] threshold){
        description = description.replace("{lowerThresh}", Double.toString(threshold[0]));
        description = description.replace("{upperThresh}", Double.toString(threshold[1]));
        return description;
    }
}
