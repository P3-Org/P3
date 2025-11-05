package com.aau.p3.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class HydrologicalToolController {
    @FXML
    Slider ekstremregnSlider = new Slider();

    @FXML
    private AnchorPane mapAnchor;

    @FXML
    public void initialize() {
        System.out.println("HydrologicalToolController initialized!");

        // Setting slider's Min, Max and start value.
        ekstremregnSlider.setMin(0);
        ekstremregnSlider.setValue(75);
        ekstremregnSlider.setMax(150);
        // Set the slider to show TickMarks, Labels and to snap to each Tick
        ekstremregnSlider.setShowTickMarks(true);
        ekstremregnSlider.setShowTickLabels(true);
        ekstremregnSlider.setSnapToTicks(true);
        // Set the value between each major Tick
        ekstremregnSlider.setMajorTickUnit(15);
        // Set the value between each minor Tick
        ekstremregnSlider.setMinorTickCount(0);

        // Makes a website(view), and an engine to handle it, so we may display it in a JavaFX scene
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();

        // Make variable for html file with all relevant map-data and information
        var mapResource = getClass().getResource("/mapData/index.html");
        if (mapResource == null) {
            System.err.println("Map HTML not found in resources");
            return;
        }
        System.out.println(mapResource);
        // Creates a string representation of the HTML location
        String mapUrl = mapResource.toExternalForm();
        webEngine.load(mapUrl);
        System.out.println("Loading map from: " + mapUrl);

        // Make WebView fill the AnchorPane
        AnchorPane.setTopAnchor(webView, 0.0);
        AnchorPane.setBottomAnchor(webView, 0.0);
        AnchorPane.setLeftAnchor(webView, 0.0);
        AnchorPane.setRightAnchor(webView, 0.0);

        mapAnchor.getChildren().add(webView);

        //
        ekstremregnSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            var value = ekstremregnSlider.getValue();
            webEngine.executeScript("setMapStyle(" + value + ")");
        });
    }
}
