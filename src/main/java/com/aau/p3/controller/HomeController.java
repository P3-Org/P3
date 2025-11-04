package com.aau.p3.controller;

import com.aau.p3.service.ControlledScreen;
import javafx.fxml.FXML;

/* Controller that handles actions performed in the HomePage.fxml file */
public class HomeController implements ControlledScreen {
    private MainController mainController;

    /* Method from the interface ControlledScreen that is used to pass the reference of MainController
     *  to the current controller */
    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}