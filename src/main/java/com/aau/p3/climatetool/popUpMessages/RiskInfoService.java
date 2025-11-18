package com.aau.p3.climatetool.popUpMessages;
import java.util.ResourceBundle;

public class RiskInfoService {

    // Method that locates the proper .properties file, and returns the information stored in each property

    /**
     *
     * @param filename the string that specifies which .properties file that should be read from
     * @return returns an object of RiskInfo with the correct information strings
     */
    public RiskInfo loadInfo(String filename) {
      ResourceBundle bundle = ResourceBundle.getBundle("UI/Drilldowns/"+filename);
      String general = bundle.getString("general") + "\n\n";
      String threshold = bundle.getString("threshold") + "\n\n";
      String calculation = bundle.getString("calculation") + "\n\n";
      String precaution = bundle.getString("precaution");

      return new RiskInfo(general, threshold, calculation, precaution);

    }
}
