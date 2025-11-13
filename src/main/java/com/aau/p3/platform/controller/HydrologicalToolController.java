package com.aau.p3.platform.controller;

import com.aau.p3.climatetool.dawa.DawaAutocomplete;
import com.aau.p3.climatetool.geoprocessing.StaticThresholdRepository;
import com.aau.p3.climatetool.geoprocessing.TifFileReader;
import com.aau.p3.climatetool.risk.CloudburstRisk;
import com.aau.p3.climatetool.risk.CoastalErosionRisk;
import com.aau.p3.climatetool.risk.GroundwaterRisk;
import com.aau.p3.climatetool.risk.StormSurgeRisk;
import com.aau.p3.climatetool.strategy.MaxMeasurementStrategy;
import com.aau.p3.climatetool.utilities.*;
import com.aau.p3.platform.utilities.ControlledScreen;
import com.aau.p3.climatetool.utilities.Indicator;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class HydrologicalToolController implements ControlledScreen {
    // initialisation of toggles, s.t. they're visible to the FXML file
    public ToggleButton cloudburstToggle = new ToggleButton();
    public ToggleButton cadastralToggle = new ToggleButton();
    public ToggleButton stormsurgeToggle = new ToggleButton();
    public ToggleButton erosionToggle = new ToggleButton();
    public ToggleButton groundwaterToggle = new ToggleButton();
    public ToggleGroup weatherOption;

    private MainController mainController;
    private final List<RiskAssessment> riskAssessment = new ArrayList<>();

    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    Slider cloudBurstSlider = new Slider();

    @FXML
    Slider stormSurgeSlider = new Slider();

    @FXML
    private AnchorPane mapAnchor;

    @FXML
    private GridPane labelContainer;

    @FXML
    private Pane cloudBurstIndicator;
    @FXML
    private Pane stormSurgeIndicator;
    @FXML
    private Pane groundWaterIndicator;
    @FXML
    private Pane coastalErosionIndicator;



    @FXML
    public void initialize() {
        System.out.println("HydrologicalToolController initialized!");

        // initialize Sliders functionality
        this.setStormSurgeSlider();
        this.setCloudBurstSlider();

        // CALL API DAWA - GETS COORDINATES
            // Redundant, only for testing
        double[][] coordinates = {
                {550900.0, 6320500.0},
                {551100.0, 6320500.0},
                {551100.0, 6320600.0},
                {550900.0, 6320600.0}};

        /* Sets up the different readers for both the Geo data and the database.
         *  Uses abstractions in form of interfaces (reference types) instead of concrete class types.
         *  Follows the Dependency Inversion Principle */
        GeoDataReader geoReader = new TifFileReader();
        ThresholdRepository thresholdRepo = new StaticThresholdRepository();
        RiskBinderInterface riskLabelBinder = new RiskLabelBinder(labelContainer);

        /* Adds a risk to the list of risks. All risks include the same information and follows the Liskov Substitution Principle */
        riskAssessment.add(new CloudburstRisk(geoReader, thresholdRepo, new MaxMeasurementStrategy()));
        riskAssessment.add(new GroundwaterRisk(geoReader, thresholdRepo));
        riskAssessment.add(new CoastalErosionRisk(geoReader, thresholdRepo));
        riskAssessment.add(new StormSurgeRisk(geoReader, thresholdRepo, new MaxMeasurementStrategy()));


        riskLabelBinder.applyColors(riskAssessment, coordinates);


        Indicator indicator = new Indicator();
        indicator.setThresholdsLines("", cloudBurstIndicator);
        indicator.setThresholdsLines("", groundWaterIndicator);
        indicator.setThresholdsLines("", stormSurgeIndicator);
        indicator.setThresholdsLines("", coastalErosionIndicator);




        // Makes a website(view), and an engine to handle it, so we may display it in a JavaFX scene
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();

        // Make variable for HTML file with all relevant map data and information
        var mapResource = getClass().getResource("/mapData/index.html");
        if (mapResource == null) {
            System.err.println("Map HTML not found in resources");
            return;
        }

        // Creates a string representation of the HTML location to use for the "mapEngine"
        String mapUrl = mapResource.toExternalForm();
        webEngine.load(mapUrl);
        System.out.println("Loading map from: " + mapUrl);

        // Adjust how the webView fills the anchorpane
        AnchorPane.setTopAnchor(webView, 0.0);
        AnchorPane.setBottomAnchor(webView, 0.0);
        AnchorPane.setLeftAnchor(webView, 0.0);
        AnchorPane.setRightAnchor(webView, 0.0);
        // Inserts the webView into the JavaFX anchorpane
        mapAnchor.getChildren().add(webView);

        /* Add listener to the valueProperty of our Slider. Then get and save the value with .getValue(),
         *  and parse that value to the javascript function "setMapStyle" in @index.html.
         */
        cloudBurstSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            double value = cloudBurstSlider.getValue();
            webEngine.executeScript("cloudBurstStyles(" + value + ")");
        });

        stormSurgeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            double value = stormSurgeSlider.getValue();
            webEngine.executeScript("stormSurgeStyles(" + value + ")");
        });


        // Event listeners for Toggle buttons
        cloudburstToggle.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue){
                cloudBurstSlider.setVisible(true);
                webEngine.executeScript("setCloudburst()");

            } else {
                webEngine.executeScript("removeClimateLayer()");
                cloudBurstSlider.setVisible(false);
                cloudBurstSlider.setValue(0);
            }
        });
        stormsurgeToggle.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue){
                stormSurgeSlider.setVisible(true);
                webEngine.executeScript("setStormSurge()");

            } else {
                webEngine.executeScript("removeClimateLayer()");
                stormSurgeSlider.setVisible(false);
                stormSurgeSlider.setValue(0);
            }
        });
        erosionToggle.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue){
                webEngine.executeScript("setErosion()");
            } else {
                webEngine.executeScript("removeClimateLayer()");
            }
        });
        groundwaterToggle.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue){
                webEngine.executeScript("setGroundwater()");
            } else {
                webEngine.executeScript("removeClimateLayer()");
            }
        });
        cadastralToggle.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue){
                webEngine.executeScript("setCadastral()");
            } else {
                webEngine.executeScript("removeCadastralLayer()");
            }
        });
    }

    // helper functions here
    private void setStormSurgeSlider(){
        stormSurgeSlider.setMin(0);// Value bound settings
        stormSurgeSlider.setMax(6);
        stormSurgeSlider.setShowTickMarks(true); // Tick mark settings
        stormSurgeSlider.setShowTickLabels(true);
        stormSurgeSlider.setSnapToTicks(true);
        stormSurgeSlider.setMajorTickUnit(0.5); // Value between major ticks
        stormSurgeSlider.setMinorTickCount(0); //Value between minor ticks

    }

    private void setCloudBurstSlider(){
        cloudBurstSlider.setMin(0); // Value bound settings
        cloudBurstSlider.setMax(150);
        cloudBurstSlider.setShowTickMarks(true); // Tick mark settings
        cloudBurstSlider.setShowTickLabels(true);
        cloudBurstSlider.setSnapToTicks(true);
        cloudBurstSlider.setMajorTickUnit(15); // Value between major ticks
        cloudBurstSlider.setMinorTickCount(0); //Value between minor ticks
    }
}