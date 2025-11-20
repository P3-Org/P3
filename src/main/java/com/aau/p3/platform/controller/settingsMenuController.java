package com.aau.p3.platform.controller;

import com.aau.p3.platform.utilities.ControlledScreen;
import javafx.fxml.FXML;

public class settingsMenuController implements ControlledScreen {
    private MainController mainController;
    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void storeNewThresholdValues() {
        //code for saving into db but like we dont have that so gg
    }

    @FXML
    private void goBack() {
        mainController.setCenter("/UI/HydrologicalTool.fxml");
    }
}
