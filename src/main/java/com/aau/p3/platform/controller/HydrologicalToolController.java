package com.aau.p3.platform.controller;

import com.aau.p3.climatetool.risk.Cloudburst;
import com.aau.p3.platform.utilities.ControlledScreen;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class HydrologicalToolController implements ControlledScreen {
    private MainController mainController;

    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private AnchorPane mapAnchor;

    @FXML
    public void initialize() {
        // CALL API DAWA - GETS COORDINATES
            // Redundant, only for testing
        double[][] coordinates = {{550900.0, 6320500.0},
                {551100.0, 6320500.0},
                {551100.0, 6320600.0},
                {550900.0, 6320600.0}};

        // CALL DIFFERENT GEOTOOLS
        double[] colorValueCloudburst = Cloudburst.gatherData(coordinates); // First one
    }

}