package com.aau.p3.platform.controller;

import com.aau.p3.platform.utilities.ControlledScreen;
import javafx.fxml.FXML;

/**
 * Controller that handles actions performed in the AllCases.fxml file.
 * If we create a button in AllCases.fxml a method here will define the functionality of the button
 */
public class AllCasesController implements ControlledScreen {
    private MainController mainController;

    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /* @FXML tag connects to the onAction in the MyCases.fxml file that is called handleMyCases.
     *  This method is triggerred once the button is clicked.
     *  This method sends the fxml file to MainControllers method setCenter */
    @FXML
    private void handleMyCases() {
        mainController.setCenter("/UI/MyCases.fxml");
    }

    @FXML
    private void handleExport() {
        System.out.println("Exporting all cases...");
    }
}