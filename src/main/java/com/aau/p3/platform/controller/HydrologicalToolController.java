package com.aau.p3.platform.controller;

import com.aau.p3.climatetool.geoprocessing.StaticThresholdRepository;
import com.aau.p3.climatetool.geoprocessing.TifGeoDataReader;
import com.aau.p3.climatetool.risk.CloudburstRisk;
import com.aau.p3.climatetool.risk.CoastalErosionRisk;
import com.aau.p3.climatetool.risk.GroundwaterRisk;
import com.aau.p3.climatetool.risk.StormSurgeRisk;
import com.aau.p3.climatetool.utilities.GeoDataReader;
import com.aau.p3.climatetool.utilities.RiskAssessment;
import com.aau.p3.climatetool.utilities.ThresholdRepository;
import com.aau.p3.platform.utilities.ControlledScreen;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HydrologicalToolController implements ControlledScreen {
    public ToggleButton cloudburstToggle = new ToggleButton();
    public ToggleButton cadastralToggle = new ToggleButton();
    public ToggleButton stormsurgeToggle = new ToggleButton();
    public ToggleButton erosionToggle = new ToggleButton();
    public ToggleButton groundwaterToggle = new ToggleButton();
    public ToggleGroup weatherOption;

    private MainController mainController;
    private final List<RiskAssessment> riskAssessment = new ArrayList<>();

    @FXML
    Slider cloudBurstSlider = new Slider();

    @FXML
    Slider stormSurgeSlider = new Slider();

    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private AnchorPane mapAnchor;

    @FXML
    public void initialize() {
        System.out.println("HydrologicalToolController initialized!");

        // initialize Sliders functionality
        cloudBurstSlider.setMin(0); // Value bound settings
        cloudBurstSlider.setMax(150);
        cloudBurstSlider.setShowTickMarks(true); // Tick mark settings
        cloudBurstSlider.setShowTickLabels(true);
        cloudBurstSlider.setSnapToTicks(true);
        cloudBurstSlider.setMajorTickUnit(15); // Value between major ticks
        cloudBurstSlider.setMinorTickCount(0); //Value between minor ticks

        stormSurgeSlider.setMin(0);// Value bound settings
        stormSurgeSlider.setMax(6);
        stormSurgeSlider.setShowTickMarks(true); // Tick mark settings
        stormSurgeSlider.setShowTickLabels(true);
        stormSurgeSlider.setSnapToTicks(true);
        stormSurgeSlider.setMajorTickUnit(0.5); // Value between major ticks
        stormSurgeSlider.setMinorTickCount(0); //Value between minor ticks


        // CALL API DAWA - GETS COORDINATES
            // Redundant, only for testing
        double[][] coordinates = {{550900.0, 6320500.0},
                {551100.0, 6320500.0},
                {551100.0, 6320600.0},
                {550900.0, 6320600.0}};

        /* Sets up the different readers for both the Geo data and the database.
        *  Uses abstractions in form of interfaces (reference types) instead of concrete class types.
        *  Follows the Dependency Inversion Principle */
        GeoDataReader geoReader = new TifGeoDataReader();
        ThresholdRepository thresholdRepo = new StaticThresholdRepository();

        /* Adds a risk to the list of risks. All risks include the same information and follows the Liskov Substitution Principle */
        riskAssessment.add(new CloudburstRisk(geoReader, thresholdRepo));
        //riskAssessment.add(new GroundwaterRisk(geoReader, thresholdRepo));
        //riskAssessment.add(new CoastalErosionRisk(geoReader, thresholdRepo));
        //riskAssessment.add(new StormSurgeRisk(geoReader, thresholdRepo));

        /* Loops through all the risks and gathers their data and prints the color values */
        for (RiskAssessment risk : riskAssessment) {
            double[] colorValue = risk.gatherData(coordinates);
            System.out.println(risk.getClass().getSimpleName() + " => " + Arrays.toString(colorValue));
        }

        // Makes a website(view), and an engine to handle it, so we may display it in a JavaFX scene
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();

        // Make variable for html file with all relevant map-data and information
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
        // Inserts the webView into the JavaFX anchor
        mapAnchor.getChildren().add(webView);

        // Add listener to the valueProperty of our Slider. Then get and save the value with .getValue(),
        // and parse that value to the javascript function "setMapStyle" in @index.html.
        cloudBurstSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            double value = cloudBurstSlider.getValue();
            webEngine.executeScript("cloudBurstStyles(" + value + ")");
        });

        stormSurgeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            double value = stormSurgeSlider.getValue();
            webEngine.executeScript("stormSurgeStyles(" + value + ")");
        });



        /* eventListeners for Toggle buttons
         * */
        cloudburstToggle.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue){
                webEngine.executeScript("setCloudburst()");
            } else {
                webEngine.executeScript("removeClimateLayer()");
            }
        });
        stormsurgeToggle.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue){
                webEngine.executeScript("setStormSurge()");
            } else {
                webEngine.executeScript("removeClimateLayer()");
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
}
