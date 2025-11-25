package com.aau.p3.platform.controller;

import com.aau.p3.database.PropertyRepository;
import com.aau.p3.database.StaticThresholdRepository;
import com.aau.p3.platform.utilities.ControlledScreen;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class SettingsMenuController implements ControlledScreen {
    private MainController mainController;
    private List<List<TextField>> textFieldList = new ArrayList<>();

    @FXML
    private TextField cloudBurstLower, cloudBurstUpper, groundWaterLower, groundWaterUpper,
                      stormSurgeLower, stormSurgeUpper, coastalErosionLower, coastalErosionUpper;

    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void goBack() {
        mainController.setCenter("/UI/FXML/HydrologicalTool.fxml");
    }

    /**
     * When the "gem" button is clicked this method is initialized.
     * Calls Updates the thresholdValues in the database
     * Clears existing properties as these are outdated and needs to be recalculated
     */
    @FXML
    private void storeNewThresholdValues() {

        StaticThresholdRepository thresholdRepository = new StaticThresholdRepository();

        writeToThresholdFieldInDB(thresholdRepository);
        PropertyRepository.wipeProperties();

        goBack();

    }

    /**
     * Reads values from inputs fields in the settings window, and writes them to the database
     * @param thresholdRepository Object of the StaticThesholdReposity, allows us to update the database
     */
     private void writeToThresholdFieldInDB(StaticThresholdRepository thresholdRepository) {
         textFieldList.addAll(Arrays.asList(
                 Arrays.asList(cloudBurstLower, cloudBurstUpper),
                 Arrays.asList(groundWaterLower, groundWaterUpper),
                 Arrays.asList(stormSurgeLower, stormSurgeUpper),
                 Arrays.asList(coastalErosionLower, coastalErosionUpper)
         ));

         // loop trough each pair of threshold (one for cloudBurst, stormSurge etc..)
         for (List<TextField> pair : textFieldList) {
             // Tries the parse the input to a double (To avoid invalid inputs)
             Double lower = tryParseDouble(pair.get(0));
             Double upper = tryParseDouble(pair.get(1));

             // If statement pases if both fields for a thresholdtype is filled out with a valid input
             if (lower != null && upper != null) {
                 thresholdRepository.updateThreshold(getRiskName(pair), lower, upper);
             }
         }
     }

    /**
     * This method is used to validate the input in the threshold input field
     * @param tf Takes a TextField object as input
     * @return Returns null if "tf" is either empty or not of type double
     */
    private Double tryParseDouble(TextField tf) {
        try {
            String text = tf.getText();
            if (text.isEmpty()) return null;
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * This method is used to match the TextFields id name to the string key used in the database
     * @param tf Takes a pair of the threshold TextField objects
     * @return a string with the name of the field we want to edit in the database eg. "cloudburst"
     */
    private String getRiskName(List<TextField> tf) {
        return tf.get(0).getId().replace("Lower", "").toLowerCase();
    }
}