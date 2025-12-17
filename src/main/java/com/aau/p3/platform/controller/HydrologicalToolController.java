package com.aau.p3.platform.controller;

import com.aau.p3.Main;
import com.aau.p3.climatetool.popUpMessages.RiskInfo;
import com.aau.p3.climatetool.popUpMessages.RiskInfoService;
import com.aau.p3.climatetool.utilities.color.RiskBinderInterface;
import com.aau.p3.climatetool.utilities.color.RiskLabelBinder;
import com.aau.p3.climatetool.utilities.*;
import com.aau.p3.platform.model.property.Property;
import com.aau.p3.platform.model.property.PropertyManager;
import com.aau.p3.platform.model.property.PropertySearch;
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
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextFlow;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import javafx.util.StringConverter;
import javafx.scene.text.Text;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.List;

/**
 * Class that handles the hydrological tool controller and window
 */
public class HydrologicalToolController implements ControlledScreen {
    @FXML private ToggleButton cloudburstToggle = new ToggleButton();
    @FXML public ToggleButton coastalToggle = new ToggleButton();
    @FXML private ToggleButton cadastralToggle = new ToggleButton();
    @FXML private ToggleButton stormsurgeToggle = new ToggleButton();
    @FXML private ToggleButton groundwaterToggle = new ToggleButton();

    @FXML private Button returnToCenter = new Button();

    @FXML private Slider cloudBurstThumb, groundWaterThumb, stormSurgeThumb, coastalErosionThumb;
    @FXML private Slider cloudBurstSlider = new Slider();
    @FXML private Slider stormSurgeSlider = new Slider();

    @FXML private ToggleButton cloudBurst2year, cloudBurst20year, cloudBurst50year, stormSurge20year, stormSurge50year, stormSurge100year, groundWater2year, groundWater5year, groundWater10year, groundWater20year, groundWater50year, groundWater100year;

    @FXML private VBox cloudBurstReturnEvent, stormSurgeReturnEvent, groundWaterReturnEvent;

    @FXML private GridPane labelContainer;

    @FXML private AnchorPane cloudBurstIndicator, stormSurgeIndicator, groundWaterIndicator, coastalErosionIndicator;
    @FXML private AnchorPane mapAnchor;
    @FXML public AnchorPane climateToolScene;

    @FXML private Label overallScoreId, groundwaterDescription, addressLabel, cloudburstDescription, stormSurgeDescription, coastalErosionDescription;

    @FXML private TextField addressSearchField;

    @FXML private TextArea commentArea;

    @FXML private Button scoreDownButton, scoreUpButton;

    @FXML private VBox previousComments;

    private WebEngine webEngine;
    private PropertyManager propertyManager;
    private Property currentProperty;
    private MainController mainController;

    /**
     * Controller, that sets the instance as the main controller.
     * Also, transfers the propertyManager, along with the currentProperty.
     */
    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
        this.propertyManager = Main.propertyManager;
        this.currentProperty = propertyManager.currentProperty;
        mainController.updateClimateButtonVisibility();
    }

    /**
     * Event handler. Upon pressing the settings button, sets the corresponding scene as main.
     */
    @FXML
    private void settingsMenu() {
        mainController.setCenter("/ui/fxml/SettingsMenu.fxml");
    }

    /**
     * Puts map pin on the property coordinates.
     * @param coords A list of strings, intended to be lat-long coordinates for the property.
     */
    public void showPropertyMarker(List<String> coords) {
        // Convert List<Double> to JS array syntax.
        String jsArray = "[" + coords.get(0) + "," + coords.get(1) + "]";
        webEngine.executeScript("showPropertyMarker(" + jsArray + ")");
    }

    /**
     * Initialization method. This method is run to assemble contents of the page.
     */
    @FXML
    public void initialize() {
        // Initialize sliders functionality.
        this.setStormSurgeSlider();
        this.setCloudBurstSlider();

        // Makes a website-view (WebView), and an engine to handle it, so we may display it in a JavaFX scene.
        WebView webView = new WebView();
        webEngine = webView.getEngine();

        // Makes a variable for the HTML file holding all relevant map data and information.
        var mapResource = getClass().getResource("/mapData/index.html");
        if (mapResource == null) {
            System.err.println("Map HTML not found in resources");
            return;
        }

        // Creates a string representation of the HTML location to use for the "mapEngine".
        String mapUrl = mapResource.toExternalForm();
        webEngine.load(mapUrl);

        // Adjusts how the webView fills the AnchorPane.
        AnchorPane.setTopAnchor(webView, 0.0);
        AnchorPane.setBottomAnchor(webView, 0.0);
        AnchorPane.setLeftAnchor(webView, 0.0);
        AnchorPane.setRightAnchor(webView, 0.0);
        mapAnchor.getChildren().add(0, webView); // Then inserts into corresponding AnchorPane.


        /* Add listener to the valueProperty of sliders. Then get and save the value with .getValue(),
         * and parse that value to the javascript function; setMapStyle in index.html.
         */
        cloudBurstSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            double value = cloudBurstSlider.getValue();
            webEngine.executeScript("cloudBurstStyles(" + value + ")");
        });
        stormSurgeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            double value = stormSurgeSlider.getValue();
            webEngine.executeScript("stormSurgeStyles(" + value + ")");
        });

        /* Event listeners for cloudburst and storm surge toggle buttons. Changes map upon adjusting slider.
        *  Else, the button is untoggled. */
        cloudburstToggle.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                cloudBurstSlider.setVisible(true);
                cloudBurstReturnEvent.setVisible(true);
                webEngine.executeScript("setCloudburst()");
            } else {
                webEngine.executeScript("removeClimateLayer()");
                cloudBurstSlider.setVisible(false);
                cloudBurstReturnEvent.setVisible(false);
                cloudBurstSlider.setValue(0);
            }
        });
        stormsurgeToggle.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                stormSurgeSlider.setVisible(true);
                stormSurgeReturnEvent.setVisible(true);
                webEngine.executeScript("setStormSurge()");

            } else {
                webEngine.executeScript("removeClimateLayer()");
                stormSurgeSlider.setVisible(false);
                stormSurgeReturnEvent.setVisible(false);
                stormSurgeSlider.setValue(0);
            }
        });

        /* Event listeners for toggle buttons corresponding to predefined X-year incidents.
        *  For cloudburst and stormsurge alike. */
        cloudBurst2year.selectedProperty().addListener((overservable, oldvalue, newValue) -> {
            if (newValue) { cloudBurstSlider.setValue(15); }
        });
        cloudBurst20year.selectedProperty().addListener((overservable, oldvalue, newValue) -> {
            if (newValue) { cloudBurstSlider.setValue(30); }
        });
        cloudBurst50year.selectedProperty().addListener((overservable, oldvalue, newValue) -> {
            if (newValue) { cloudBurstSlider.setValue(45); }
        });
        stormSurge20year.selectedProperty().addListener((overservable, oldvalue, newValue) -> {
            if (newValue) { stormSurgeSlider.setValue(2); }
        });
        stormSurge50year.selectedProperty().addListener((overservable, oldvalue, newValue) -> {
            if (newValue) { stormSurgeSlider.setValue(2.1); }
        });
        stormSurge100year.selectedProperty().addListener((overservable, oldvalue, newValue) -> {
            if (newValue) { stormSurgeSlider.setValue(2.2); }
        });

        /* Event listener for groundwater toggle button. Else, removes layer if button is untoggled.
        *  Followed by listeners for toggle buttons corresponding to predefined X-year incidents */
        groundwaterToggle.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                groundWaterReturnEvent.setVisible(true);
                webEngine.executeScript("setGroundwater()");
            } else {
                groundWaterReturnEvent.setVisible(false);
                webEngine.executeScript("removeClimateLayer()");
            }
        });
        groundWater2year.selectedProperty().addListener((overservable, oldvalue, newValue) -> {
            if (newValue) { webEngine.executeScript("groundWaterLayers(" + 2 + ")"); }
        });
        groundWater5year.selectedProperty().addListener((overservable, oldvalue, newValue) -> {
            if (newValue) { webEngine.executeScript("groundWaterLayers(" + 5 + ")"); }
        });
        groundWater10year.selectedProperty().addListener((overservable, oldvalue, newValue) -> {
            if (newValue) { webEngine.executeScript("groundWaterLayers(" + 10 + ")"); }
        });
        groundWater20year.selectedProperty().addListener((overservable, oldvalue, newValue) -> {
            if (newValue) { webEngine.executeScript("groundWaterLayers(" + 20 + ")"); }
        });
        groundWater50year.selectedProperty().addListener((overservable, oldvalue, newValue) -> {
            if (newValue) { webEngine.executeScript("groundWaterLayers(" + 50 + ")"); }
        });
        groundWater100year.selectedProperty().addListener((overservable, oldvalue, newValue) -> {
            if (newValue) { webEngine.executeScript("groundWaterLayers(" + 100 + ")"); }
        });

        /* Event listener for coastal erosion toggle button. Else, removes layer if button is untoggled. */
        coastalToggle.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                webEngine.executeScript("setErosion()");
            } else {
                webEngine.executeScript("removeClimateLayer()");
            }
        });

        /* Event listener for cadastral map toggle button. Else, removes layer if button is untoggled. */
        cadastralToggle.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                webEngine.executeScript("setCadastral()");
            } else {
                webEngine.executeScript("removeCadastralLayer()");
            }
        });

        /* Uses the returnToCenter button intended for the center button
        *  to set the initial view on the sought property */
        returnToCenter.setOnAction(event -> {
            List<String> coords = this.currentProperty.getLatLongCoordinates();
            panTo(coords);
        });

        // Event listener for changing size of page containing the WebView
        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            webView.widthProperty().addListener((observable, oldVal, newVal) -> {
                webView.getEngine().executeScript("updateLegendSize(" + newVal.doubleValue() + ")");
            });
        });

        /* Creates a PropertySearch object to be used for looking up an address using DAWA api.
        *  It passes the field that will be filled out, and a callback method responsible for
        *  refreshing the climate information regarding the new property once the address has been filled out. */
        PropertySearch search = new PropertySearch(addressSearchField, () -> refresh());
        search.searchAddress();

        /* Centers the map once the WebEngine has finished loading the map page. */
        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                panTo(this.currentProperty.getLatLongCoordinates());
                showPropertyMarker(this.currentProperty.getLatLongCoordinates());
            }
        });
    }

    /**
     * This method is intended to run when changes to the page are made, equivalent to a refresh.
     */
    public void afterInitialize() {
        double[][] polygonArray = PropertySearch.to2dArray(this.currentProperty.getPolygonCoordinates());
        this.evaluateRiskProfile(polygonArray);

        setupSliderIndicators(currentProperty.getRisks());
        updateScoreButtons();
        showPreviousComments();

        // Decode URL string through UTF-8.
        String encodedAddress = currentProperty.getAddress();
        String decodedAddress = URLDecoder.decode(encodedAddress, StandardCharsets.UTF_8);
        addressLabel.setText(decodedAddress);

        updateRiskDescriptions(cloudburstDescription, currentProperty.getRisks().get(0).getDescription());
        updateRiskDescriptions(groundwaterDescription, currentProperty.getRisks().get(1).getDescription());
        updateRiskDescriptions(stormSurgeDescription, currentProperty.getRisks().get(2).getDescription());
        updateRiskDescriptions(coastalErosionDescription, currentProperty.getRisks().get(3).getDescription());
    }

    /**
     * Method that initializes the slider for storm surge.
     */
    private void setStormSurgeSlider() {
        // Value bound settings.
        stormSurgeSlider.setMin(0);
        stormSurgeSlider.setMax(3);

        // Changes the text corresponding to ticks on the slider.
        stormSurgeSlider.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double value) {
                return value + "m";
            }
            @Override
            public Double fromString(String s) {
                String digits = s.replaceAll("[^0-9.-]","");
                return Double.parseDouble(digits);
            }
        });

        stormSurgeSlider.setShowTickMarks(true); // Tick mark settings.
        stormSurgeSlider.setShowTickLabels(true);
        stormSurgeSlider.setSnapToTicks(true);
        stormSurgeSlider.setMajorTickUnit(0.5); // Value between major ticks.
        stormSurgeSlider.setMinorTickCount(4); // Value between minor ticks.
    }

    /**
     * Method that initializes the slider for cloudburst.
     */
    private void setCloudBurstSlider() {
        // Value bound settings.
        cloudBurstSlider.setMin(0);
        cloudBurstSlider.setMax(150);

        // Changes the text corresponding to ticks on the slider.
        cloudBurstSlider.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double value) {
                int i = value.intValue();
                return i + "mm";
            }
            @Override
            public Double fromString(String s) {
                String digits = s.replaceAll("[^0-9.-]","");
                return Double.parseDouble(digits);
            }
        });

        cloudBurstSlider.setShowTickMarks(true); // Tick mark settings.
        cloudBurstSlider.setShowTickLabels(true);
        cloudBurstSlider.setSnapToTicks(true);
        cloudBurstSlider.setMajorTickUnit(15); // Value between major ticks.
        cloudBurstSlider.setMinorTickCount(0); // Value between minor ticks.
    }

    /**
     * Method that calls JavaScript code. The function called is a leaflet function that can panTo coordinates.
     * @param coords The coordinates to pan to.
     */
    public void panTo(List<String> coords) {
        webEngine.executeScript("panTo(" + coords + ")");
    }

    /**
     * Method for computing the climate score and setting appropriate colors for the page.
     * @param polygon The polygon of the property.
     */
    private void evaluateRiskProfile(double[][] polygon) {
        overallScoreId.setText(Double.toString(currentProperty.getClimateScore()));
        RiskBinderInterface riskLabelBinder = new RiskLabelBinder(labelContainer);

        // Calling applyColors to apply the correct colors to the labels inside JavaFX.
        riskLabelBinder.applyColors(currentProperty.getRisks(), polygon);
    }

    /**
     * Event handler for increasing and updating the climate score button, if measures have been taken to better it.
     */
    @FXML
    private void increaseScore() {
        if (currentProperty.getSpecialistAdjustment() == -1) {
            currentProperty.applySpecialistAdjustment(0);
            overallScoreId.setText(Double.toString(currentProperty.getClimateScore()));
            PropertyManager.updateDBSpecialistAdjustment(currentProperty);
            updateScoreButtons();
        } else {
            currentProperty.applySpecialistAdjustment(1);
            overallScoreId.setText(Double.toString(currentProperty.getClimateScore()));
            PropertyManager.updateDBSpecialistAdjustment(currentProperty);
            updateScoreButtons();
        }
    }

    /**
     * Event handler for decreasing and updating the climate score button, if measures have been taken to lessen it.
     */
    @FXML
    private void decreaseScore() {
        if (currentProperty.getSpecialistAdjustment() == 1) {
            currentProperty.applySpecialistAdjustment(0);
            overallScoreId.setText(Double.toString(currentProperty.getClimateScore()));
            PropertyManager.updateDBSpecialistAdjustment(currentProperty);
            updateScoreButtons();
        } else {
            currentProperty.applySpecialistAdjustment(-1);
            overallScoreId.setText(Double.toString(currentProperty.getClimateScore()));
            PropertyManager.updateDBSpecialistAdjustment(currentProperty);
            updateScoreButtons();
        }
    }

    /**
     * Method for changing the climate score, in case any measures have been taken to better the score.
     */
    private void updateScoreButtons() {
        // Calculate new score
        int overallClimateScore = currentProperty.getClimateScore();
        int specialistScoreFactor = currentProperty.getSpecialistAdjustment();

        // Shows the buttons if the score is in a certain given range.
        scoreDownButton.setVisible(overallClimateScore > 1);
        scoreUpButton.setVisible(overallClimateScore < 5);

        // Only show buttons if actions should be permitted.
        if (specialistScoreFactor == 1) {
            scoreUpButton.setVisible(false);
        }

        if (specialistScoreFactor == -1) {
            scoreDownButton.setVisible(false);
        }

        overallScoreId.setText(Double.toString(currentProperty.getClimateScore()));
    }

    /**
     * Event handler to act upon activating the info buttons to get more information on a page.
     * @param event This is the ActionEvent activated through the UI.
     */
    @FXML
    private void popUpHandler(ActionEvent event) {
        try {
            // Readies the popup scene, much like the main stage.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/PopupWindow.fxml"));
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

    /**
     * Event handler to activate upon sending a comment through the interface.
     */
    @FXML
    private void commentButtonHandler() {
        String comment = commentArea.getText();
        if (!comment.isEmpty()) {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            String timestamp = LocalDate.now().format(dateFormatter);
            String fullComment = timestamp + "\n" + comment;
            Main.propertyManager.currentProperty.setComment(fullComment);
            PropertyManager.addCommentToDB(currentProperty, fullComment);
            showPreviousComments();
            commentArea.clear();
        } else {
            System.out.println("The comment box is empty");
        }
    }

    /**
     * Method for showing previous comments on a property. Gets comments in currentProperty as a List,
     * then loops through the list and creates a Text for each comment, and adds them to Vbox previousComments.
     */
    private void showPreviousComments() {
        // Clears comments. When the function is called again when submitting a new comment, the new comment is also shown.
        previousComments.getChildren().clear();

        // Gathers previous comments from the current property.
        List<String> prevComments = Main.propertyManager.currentProperty.getComments();

        if (!prevComments.isEmpty()) {
            for (String comment : prevComments) {
                // Make the comment a Text, so it may parse.
                Text text = new Text(comment);
                text.setFont(Font.font("Arial", FontWeight.NORMAL, 15));

                // Bind wrapping width to previousComments width property, so it works on first launch.
                text.wrappingWidthProperty().bind(previousComments.widthProperty().subtract(10));

                // Convert to a TextFlow, set width and line spacing.
                TextFlow flow = new TextFlow(text);
                flow.prefWidthProperty().bind(previousComments.widthProperty());
                flow.maxWidthProperty().bind(previousComments.widthProperty());
                flow.setLineSpacing(1.5);

                // Adds the TextFlow element to the Vbox.
                previousComments.getChildren().add(flow);
            }
        }
    }

    /**
     * Updates the descriptions for a risk, given its ID and the text to replace with.
     * @param descriptionId The ID of the description field that is to be updated.
     * @param textToShow The text to insert for the description.
     */
    private void updateRiskDescriptions(Label descriptionId, String textToShow) {
        descriptionId.setText(textToShow);
    }

    /**
     * Method for inputting the overlay elements over the slider.
     * These include threshold Lines, threshold values and moving the slider appropriately.
     * @param listOfRisks Takes the list of all the risk objects, as we need to read the values for each of these.
     */
    private void setupSliderIndicators(List<RiskAssessment> listOfRisks) {
        Indicator indicator = new Indicator();
        indicator.setThresholdsLines(cloudBurstIndicator,"cloudburst", "mm");
        indicator.setThresholdsLines(groundWaterIndicator,"groundwater", "m");
        indicator.setThresholdsLines(stormSurgeIndicator,"stormsurge", "m");
        indicator.setThresholdsLines(coastalErosionIndicator,"coastalerosion", "m");

        ThumbEditor thumbEditor = new ThumbEditor();
        thumbEditor.setLimits(listOfRisks.get(0), cloudBurstThumb);
        thumbEditor.setLimits(listOfRisks.get(1), groundWaterThumb);
        thumbEditor.setLimits(listOfRisks.get(2), stormSurgeThumb);
        thumbEditor.setLimits(listOfRisks.get(3), coastalErosionThumb);

        animateSliderTo(cloudBurstThumb, listOfRisks.get(0).getNormalizedValue());
        animateSliderTo(groundWaterThumb, listOfRisks.get(1).getNormalizedValue());
        animateSliderTo(stormSurgeThumb, listOfRisks.get(2).getNormalizedValue());
        animateSliderTo(coastalErosionThumb, listOfRisks.get(3).getNormalizedValue());
    }

    /***
     * Helper function to setupSliderIndicators. It controls the logic for animating the thumb of the slider.
     * It uses javaFX.Animations to achieve this.
     * @param slider A reference to the slider object that we want to animate.
     * @param targetValue corresponds to the normalized measurement value,
     *                    and defines what the thumb should move towards and stop at.
     */
    private void animateSliderTo(Slider slider, double targetValue) {
        double midpoint = (slider.getMin() + slider.getMax()) / 2.0;
        slider.setValue(midpoint);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.5), new KeyValue(slider.valueProperty(), midpoint)),
                new KeyFrame(Duration.seconds(0.8), new KeyValue(slider.valueProperty(), targetValue))
        );
        timeline.play();
    }

    /**
     * Method used for refreshing the following: The currentProperty object,
     * recalls afterInitialize to reevaluate risks, descriptions and receive coordinates.
     * Lastly sets the map to the correct property using panTo().
     */
    public void refresh() {
        // Update controller reference to the new property.
        this.currentProperty = propertyManager.currentProperty;

        // Re-evaluate risk, labels, sliders and coordinates.
        afterInitialize();

        // Pans the map to the correct property.
        if (currentProperty != null) {
            panTo(currentProperty.getLatLongCoordinates()); // always run on new selection
            showPropertyMarker(this.currentProperty.getLatLongCoordinates());
        }
    }

    @Override
    public String toString() {
        return "HydrologicalToolController";
    }

}