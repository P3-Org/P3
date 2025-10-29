package com.aau.p3.controller;

import com.aau.p3.service.ControlledScreen;
import javafx.fxml.FXML;

public class HomeController implements ControlledScreen {
    private MainController mainController;

    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

}
