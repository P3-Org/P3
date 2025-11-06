package com.aau.p3.platform.controller;

import com.aau.p3.platform.utilities.ControlledScreen;
import javafx.fxml.FXML;

public class MyCasesController implements ControlledScreen {
    private MainController mainController;

    /* Method from the interface ControlledScreen that is used to pass the reference of MainController
    *  to the current controller */
    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /* @FXML tag connects to the onAction in the AllCases.fxml file that is called handleAllCases.
    *  This method is triggerred once the button is clicked.
    *  This method sends the fxml file to MainControllers method setCenter */
    @FXML
    private void handleAllCases() {
        mainController.setCenter("/UI/AllCases.fxml");
    }
}