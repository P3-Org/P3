package com.aau.p3.platform.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PopupWindowController {

    @FXML
    private Label popupLabel;

    private Stage stage;

    public void setMessage(String message) {
        popupLabel.setText(message);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void closePopup() {
        if (stage != null) {
            stage.close();
        }
    }
}
