package com.aau.p3.platform.controller;
import com.aau.p3.climatetool.popUpMessages.RiskInfo;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * Class that holds methods for handling the activation of a pop-up info box.
 */
public class PopupWindowController {
    @FXML private TextFlow infoDisplay;
    private Stage stage;

    /**
     * Setter method that the stage to the popup windows stage.
     * @param stage The stage that is to be inserted for the pop-up window.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Adds the information paragraphs for the parsed climate risk, using the .properties files.
     * @param info Risk info object to use the getters inside the class.
     */
    public void displayInfo(RiskInfo info){
        infoDisplay.getChildren().add(new Text(info.getGeneralInfo()));
        infoDisplay.getChildren().add(new Text(info.getCalculationInfo()));
        infoDisplay.getChildren().add(new Text(info.getThresholdInfo()));
        infoDisplay.getChildren().add(new Text(info.getPrecautionInfo()));
    }

    /**
     * Event handler for the close button, that closes the pop-up.
     */
    @FXML
    private void closePopup() { if (stage != null) { stage.close(); }}
}
