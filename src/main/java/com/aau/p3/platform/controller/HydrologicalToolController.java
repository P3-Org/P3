package com.aau.p3.platform.controller;

import com.aau.p3.Main;
import com.aau.p3.climatetool.popUpMessages.RiskInfo;
import com.aau.p3.climatetool.popUpMessages.RiskInfoService;
import com.aau.p3.climatetool.utilities.color.RiskBinderInterface;
import com.aau.p3.climatetool.utilities.color.RiskLabelBinder;
import com.aau.p3.platform.model.property.Property;
import com.aau.p3.platform.model.property.PropertyManager;
import com.aau.p3.platform.utilities.ControlledScreen;
import com.aau.p3.climatetool.utilities.Indicator;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Popup;

import javafx.stage.Stage;

import java.util.List;

public class HydrologicalToolController implements ControlledScreen {
    // initialisation of toggles, s.t. they're visible to the FXML file
    public ToggleButton cloudburstToggle = new ToggleButton();
    public ToggleButton cadastralToggle = new ToggleButton();
    public ToggleButton stormsurgeToggle = new ToggleButton();
    public ToggleButton erosionToggle = new ToggleButton();
    public ToggleButton groundwaterToggle = new ToggleButton();
    public ToggleGroup weatherOption;
    private WebEngine webEngine;
    private PropertyManager propertyManager;
    private Property currentProperty;

    private MainController mainController;


    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
        this.propertyManager = Main.propertyManager;
        this.currentProperty = propertyManager.currentProperty;
    }

    @FXML
    private TextField addressField; // The field where the user types the address
    private final Popup suggestionsPopup = new Popup(); // Popup window with the suggested addresses
    private final ListView<String> suggestionsList = new ListView<>(); // List of the addresses for the popup window.

    @FXML
    Slider cloudBurstSlider = new Slider();

    @FXML
    Slider stormSurgeSlider = new Slider();

    @FXML
    private AnchorPane mapAnchor; //Anchor pane for the webmap

    @FXML
    public AnchorPane climateToolScene; //Anchor pane for the entire climate tool page

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






        // Makes a website(view), and an engine to handle it, so we may display it in a JavaFX scene
        WebView webView = new WebView();
        webEngine = webView.getEngine();

        // Make variable for HTML file with all relevant map data and information
        var mapResource = getClass().getResource("/mapData/index.html");

        if (mapResource == null) {
            System.err.println("Map HTML not found in resources");
            return;
        }

        // Creates a string representation of the HTML location to use for the "mapEngine"
        String mapUrl = mapResource.toExternalForm();
        webEngine.load(mapUrl);

        // Adjust how the webView fills the anchorpane
        AnchorPane.setTopAnchor(webView, 0.0);
        AnchorPane.setBottomAnchor(webView, 0.0);
        AnchorPane.setLeftAnchor(webView, 0.0);
        AnchorPane.setRightAnchor(webView, 0.0);
        // Inserts the webView into the JavaFX anchorpane
        mapAnchor.getChildren().add(webView);


        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {

            }
        });




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

    public void panTo(List<String> coords) {
        webEngine.executeScript("panTo(" + coords + ")");
    }

    private double[][] to2dArray(List<List<Double>> list) {
        double[][] arr = new double[list.size()][];

        for (int i = 0; i < list.size(); i++) {
            List<Double> inner = list.get(i);
            arr[i] = new double[inner.size()];

            for (int j = 0; j < inner.size(); j++) {
                arr[i][j] = inner.get(j);
            }
        }
        return arr;
    }

    private void doRiskstuff(double[][] polygon){
        /* Sets up the different readers for both the Geo data and the database.
         *  Uses abstractions in form of interfaces (reference types) instead of concrete class types.
         *  Follows the Dependency Inversion Principle */

        //Property property = new Property(polygon, riskFactory.createRisks(polygon));

        RiskBinderInterface riskLabelBinder = new RiskLabelBinder(labelContainer);

        // Calling applyColors to apply the correct colors to the labels inside JavaFX
        riskLabelBinder.applyColors(currentProperty.getRisks(), polygon);

        Indicator indicator = new Indicator();
        indicator.setThresholdsLines("", cloudBurstIndicator);
        indicator.setThresholdsLines("", groundWaterIndicator);
        indicator.setThresholdsLines("", stormSurgeIndicator);
        indicator.setThresholdsLines("", coastalErosionIndicator);
    }

    @FXML
    private void popUpHandler(ActionEvent event) {
        try {
            // Readies the popup scene, much like the main stage.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/PopupWindow.fxml"));
            VBox popupRoot = loader.load();

            // When the button is clicked, the correct .properties file is found using the fx:id.
            Button clicked = (Button) event.getSource();
            String category = clicked.getId().replace("Popup", "").toLowerCase();

            // Instantialises RiskInfoService, to enable calling the loadInfo method, and sourcing the proper information.
            RiskInfoService infoService = new RiskInfoService();
            RiskInfo RiskDrilldown = infoService.loadInfo(category);

            // Sets PopupWindowController as the controller for the popup that arises. Runs displayInfo from the controller.
            PopupWindowController controller = loader.getController();
            controller.displayInfo(RiskDrilldown);

            // Properly initialises, positions and fills the page with the generated and gathered information.
            Stage popupStage = new Stage();
            controller.setStage(popupStage);
            popupStage.setScene(new Scene(popupRoot));
            popupStage.initOwner(climateToolScene.getScene().getWindow());
            popupStage.setAlwaysOnTop(true);
            popupStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void afterInitialize(){
        double[][] polygonArray = this.to2dArray(this.currentProperty.getPolygonCoordinates());
        this.doRiskstuff(polygonArray);

        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                panTo(this.currentProperty.getLatLongCoordinates());
            }
        });
    }
}