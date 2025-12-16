package com.aau.p3.platform.controller;

import com.aau.p3.climatetool.utilities.ThresholdRepositoryInterface;
import com.aau.p3.database.PropertyRepository;
import com.aau.p3.database.ThresholdRepository;
import com.aau.p3.platform.model.property.PropertyManager;
import com.aau.p3.platform.utilities.ControlledScreen;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class SettingsMenuController implements ControlledScreen {
    private MainController mainController;
    private List<List<TextField>> textFieldList = new ArrayList<>();
    @FXML private TextField cloudBurstLower, cloudBurstUpper, groundWaterLower, groundWaterUpper, stormSurgeLower, stormSurgeUpper, coastalErosionLower, coastalErosionUpper;
    @FXML private Label cloudBurstCurrentLower, cloudBurstCurrentUpper, groundWaterCurrentLower, groundWaterCurrentUpper, stormSurgeCurrentLower, stormSurgeCurrentUpper, coastalErosionCurrentLower, coastalErosionCurrentUpper;

    /**
     * Method called upon loading the controller, retrieves the correct threshold values stored in the database
     * and displays then on a label.
     */
    @FXML
    public void initialize() {
        ThresholdRepositoryInterface repo = new ThresholdRepository();

        setThresholdLabels(repo.getThreshold("cloudburst"), cloudBurstCurrentLower, cloudBurstCurrentUpper);
        setThresholdLabels(repo.getThreshold("groundwater"), groundWaterCurrentLower, groundWaterCurrentUpper);
        setThresholdLabels(repo.getThreshold("stormsurge"), stormSurgeCurrentLower, stormSurgeCurrentUpper);
        setThresholdLabels(repo.getThreshold("coastalerosion"), coastalErosionCurrentLower, coastalErosionCurrentUpper);
    }

    private void setThresholdLabels(double[] thresholdValues, Label lowerLbl, Label upperLbl) {
        // Checks if there is no value, and sets to label to display empty if true.
        if (thresholdValues == null || thresholdValues.length < 2) {
            lowerLbl.setText("-");
            upperLbl.setText("-");
            return;
        }

        // Set the label text to the values of the thresholds.
        lowerLbl.setText(String.valueOf(thresholdValues[0]));
        upperLbl.setText(String.valueOf(thresholdValues[1]));
    }

    /**
     * Sets SettingMenuController as the current main controller.
     * @param mainController The controller that we want to set as the new main controller.
     */
    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Method that is called when the "cancel" button is clicked. Sets the window back to the climate tool.
     */
    @FXML
    private void goBack() {
        mainController.setCenter("/ui/fxml/HydrologicalTool.fxml");
    }


    /**
     * When the "gem" button is clicked this method is initialized.
     * Updates the thresholdValues in the database. Clears existing properties both in the database
     * and memory as these are outdated and needs to be recalculated.
     */
    @FXML
    private void storeNewThresholdValues() {
        ThresholdRepositoryInterface thresholdRepository = new ThresholdRepository();

        writeToThresholdFieldInDB(thresholdRepository);
        PropertyRepository.wipeProperties();
        PropertyManager.emptyMemory();

        goBack();
    }

    /**
     * Reads values from inputs fields in the settings window, and writes them to the database
     * @param thresholdRepository Object of the ThresholdRepository, allows us to update the database
     */
     private void writeToThresholdFieldInDB(ThresholdRepositoryInterface thresholdRepository) {
         textFieldList.addAll(Arrays.asList(
                 Arrays.asList(cloudBurstLower, cloudBurstUpper),
                 Arrays.asList(groundWaterLower, groundWaterUpper),
                 Arrays.asList(stormSurgeLower, stormSurgeUpper),
                 Arrays.asList(coastalErosionLower, coastalErosionUpper)
         ));

         // Loop trough each pair of threshold (one for cloudBurst, stormSurge etc..).
         for (List<TextField> pair : textFieldList) {
             // Tries the parse the input to a double (To avoid invalid inputs).
             Double lower = tryParseDouble(pair.get(0));
             Double upper = tryParseDouble(pair.get(1));

             // If statement pases when both fields for a threshold type is filled out with a valid input.
             if (lower != null && upper != null) {
                 thresholdRepository.updateThreshold(getRiskName(pair), lower, upper);
             }
         }
     }

    /**
     * This method is used to validate the input in the threshold input field.
     * @param tf Takes a TextField object as input.
     * @return Null if "tf" is either empty or not of type double.
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
     * This method is used to match the TextFields id name to the string key used in the database.
     * @param tf Takes a pair of the threshold TextField objects.
     * @return A string with the name of the field we want to edit in the database e.g. "cloudburst".
     */
    private String getRiskName(List<TextField> tf) {
        return tf.get(0).getId().replace("Lower", "").toLowerCase();
    }
}