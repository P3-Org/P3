package com.aau.p3.platform.controller;

import com.aau.p3.climatetool.popUpMessages.RiskInfo;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class PopupWindowController {

    @FXML
    private TextFlow infoDisplay;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    // Adds the information paragraphs for the parsed climate risk, using the .properties files.
    public void displayInfo(RiskInfo info){
        infoDisplay.getChildren().add(new Text(info.getGeneralInfo()));
        infoDisplay.getChildren().add(new Text(info.getCalculationInfo()));
        infoDisplay.getChildren().add(new Text(info.getThresholdInfo()));
        infoDisplay.getChildren().add(new Text(info.getPrecautionInfo()));
    }

    @FXML
    private void closePopup() {
        if (stage != null) {
            stage.close();
        }
    }

}
