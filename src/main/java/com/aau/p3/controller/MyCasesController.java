package com.aau.p3.controller;

import com.aau.p3.service.ControlledScreen;
import javafx.fxml.FXML;

public class MyCasesController implements ControlledScreen {
    private MainController mainController;

    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void handleAllCases() {
        mainController.setCenter("/UI/AllCases.fxml");
    }

}
