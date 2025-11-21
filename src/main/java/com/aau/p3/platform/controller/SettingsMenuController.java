package com.aau.p3.platform.controller;

import com.aau.p3.climatetool.geoprocessing.TiffFileReader;
import com.aau.p3.climatetool.utilities.GeoDataReader;
import com.aau.p3.climatetool.utilities.ThresholdRepository;
import com.aau.p3.database.StaticThresholdRepository;
import com.aau.p3.platform.model.property.Property;
import com.aau.p3.platform.model.property.RiskFactory;
import com.aau.p3.platform.utilities.ControlledScreen;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import static com.aau.p3.Main.propertyManager;

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
        mainController.setCenter("/UI/HydrologicalTool.fxml");
    }

    @FXML
    private void storeNewThresholdValues() {
        textFieldList.addAll(Arrays.asList(
                Arrays.asList(cloudBurstLower, cloudBurstUpper),
                Arrays.asList(groundWaterLower, groundWaterUpper),
                Arrays.asList(stormSurgeLower, stormSurgeUpper),
                Arrays.asList(coastalErosionLower, coastalErosionUpper)
        ));

        StaticThresholdRepository thresholdRepository = new StaticThresholdRepository();

        writeToThresholdFieldInDB(thresholdRepository);

        goBack();


    }

     private void writeToThresholdFieldInDB(StaticThresholdRepository thresholdRepository) {

         for (List<TextField> pair : textFieldList){
             if (isDouble(pair)) {
                 thresholdRepository.updateThreshold(
                         pair.get(0).getId().replace("Lower", "").toLowerCase(),
                         readInput(pair.get(0)),
                         readInput(pair.get(1))
                 );
             }
         }
     }

     private void updatePropertyObjectInDB(String selectedAddress) {
     }

    private double readInput(TextField textInput) {
        try {
            double convertedInput = Double.parseDouble(textInput.getText());
            return convertedInput;
        } catch (NumberFormatException e) {
            throw e;
        }
    }

    private boolean isDouble(List<TextField> textInput) {
        try {
            Double.parseDouble(textInput.get(0).getText());
            Double.parseDouble(textInput.get(1).getText());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }

    }
}