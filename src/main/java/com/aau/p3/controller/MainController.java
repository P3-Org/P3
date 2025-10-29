package com.aau.p3.controller;

import com.aau.p3.service.ControlledScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

public class MainController {
    @FXML private StackPane contentArea;

    @FXML
    public void initialize() {
        setCenter("/UI/HomePage.fxml");
    }

    public void setCenter(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Node view = loader.load();

            // Give sub-controller a reference back to this controller
            Object ctrl = loader.getController();

            if (ctrl instanceof ControlledScreen cs) {
                cs.setMainController(this);
            }
            System.out.println("contentArea" + contentArea.getChildren());
            contentArea.getChildren().setAll(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setHomePage() {
        setCenter("/UI/HomePage.fxml");
    }

    @FXML
    private void openHomePage() {
        setCenter("/UI/HomePage.fxml");
    }

    @FXML
    private void openMyCases() {
        setCenter("/UI/MyCases.fxml");
    }

    @FXML
    private void openAllCases() {
        setCenter("/UI/AllCases.fxml");
    }

    @FXML
    private void openAddressLookup(ActionEvent actionEvent) {
        System.out.println("Address search activated...");
        setCenter("/UI/AddressLookup.fxml");
    }

    @FXML
    private void openHelpDesk(ActionEvent actionEvent) {
        System.out.println("Open help desk activated...");
    }

    @FXML
    private void exitApp() {
        System.exit(0); // Exits the program
    }

}
