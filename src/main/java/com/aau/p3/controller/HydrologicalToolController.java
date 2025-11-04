package com.aau.p3.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class HydrologicalToolController {

    @FXML
    private AnchorPane mapAnchor;

    @FXML
    public void initialize() {
        System.out.println("HydrologicalToolController initialized!");

        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();

        var mapResource = getClass().getResource("/mapData/index.html");
        if (mapResource == null) {
            System.err.println("❌ Map HTML not found in resources! Expected at: /mapData/index.html");
            return;
        }
        String mapUrl = mapResource.toExternalForm();
        webEngine.load(mapUrl);

        System.out.println("✅ Loading map from: " + mapUrl);

        // Load the Leaflet map HTML from resources
        webEngine.load(mapResource.toExternalForm());

        // Make WebView fill the AnchorPane
        AnchorPane.setTopAnchor(webView, 0.0);
        AnchorPane.setBottomAnchor(webView, 0.0);
        AnchorPane.setLeftAnchor(webView, 0.0);
        AnchorPane.setRightAnchor(webView, 0.0);

        mapAnchor.getChildren().add(webView);
    }
}
