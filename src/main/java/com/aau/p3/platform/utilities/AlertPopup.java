package com.aau.p3.platform.utilities;

import javafx.scene.control.Alert;

public class AlertPopup {
    /**
     * A static method for generating an error message, with a specified message
     * @param message The message to be displayed in the error popup
     */
    public static void errorMessage(String message) {
        // Create alert type and sets title
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fejl");

        // Turns off header
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Program waits until popup is resolved
        alert.showAndWait();
    }
}
