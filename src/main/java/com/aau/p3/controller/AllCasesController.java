package com.aau.p3.controller;

import com.aau.p3.service.ControlledScreen;
import javafx.fxml.FXML;

public class AllCasesController implements ControlledScreen {
    private MainController mainController;

    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void handleExport() {
        System.out.println("Exporting all cases...");
    }

    @FXML
    private void handleMyCases() {
        mainController.setCenter("/UI/MyCases.fxml");
    }
}
