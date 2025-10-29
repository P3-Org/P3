package com.aau.p3.controller;

import com.aau.p3.service.ControlledScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

public class MainController {
    /* contentArea is used to work as the area of the screen where the different "windows" will be shown.
    *  The specific name contentArea is needed as the tag @FXML connects the java code to the fxml id tag "contentArea" */
    @FXML private StackPane contentArea;

    /* void Method that is ALWAYS called during the initialization process of FXML from Main.java
    *  setCenter is called in this class and the page HomePage.fxml is set in the contentArea */
    @FXML
    public void initialize() {
        setCenter("/UI/HomePage.fxml");
    }

    /* setCenter method takes an FXML file type (window) and replaces the current window with that content */
    public void setCenter(String fxml) {
        try {
            /* Creates a FXMLloader object based on the given fxml file and loads it into the class Node (in javafx.scene)
            *  Node is a superclass to Parent in javafx that is used to hold any scene object, where Parent class only holds containers
            *  such as "Vbox, Hbox, etc." */
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));

            /* loader.load() returns the outermost tag <> in the fxml file */
            Node view = loader.load();

            /* Give sub-controller a reference back to this controller so a two-way communication is possible
            *  loader.getController() finds which controller os calling it from the FXML file that was found in getResource() */
            Object ctrl = loader.getController();

            /* Checks if the current controller is an instance of the interface ControlledScreen
            * such that the subcontroller implements the method setMainController() and can communicate with the MainController
            * while in theory not being linked at all with it */
            if (ctrl instanceof ControlledScreen cs) {
                cs.setMainController(this);
            }

            /* Prints out to show how the contentArea is replaces after each navigation in the GUI.
            *  contentArea.getChildren.setAll(view) is the code in charge of actually changing the FXML data below the StackPane tag
            * with id contentArea in the MainWindow.fxml */
            System.out.println("contentArea" + contentArea.getChildren());
            contentArea.getChildren().setAll(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* @FXML tag is used to grab a hold of the specific onAction id "openHomePage" inside MainWindow.fxml */
    @FXML
    private void openHomePage(ActionEvent actionEvent) {
        setCenter("/UI/HomePage.fxml");
    }

    @FXML
    private void openMyCases(ActionEvent actionEvent) {
        setCenter("/UI/MyCases.fxml");
    }

    @FXML
    private void openAllCases(ActionEvent actionEvent) {
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
        System.exit(0);
    }
}