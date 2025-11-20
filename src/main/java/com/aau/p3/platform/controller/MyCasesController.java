package com.aau.p3.platform.controller;

import com.aau.p3.climatetool.dawa.*;
import com.aau.p3.platform.urlmanager.UrlAutoComplete;
import com.aau.p3.platform.utilities.ControlledScreen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.lang.String;

import javafx.geometry.Bounds;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Popup;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that handles my cases controller and the window that
 */
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