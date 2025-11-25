package com.aau.p3.platform.model.property;

import com.aau.p3.Main;
import com.aau.p3.climatetool.dawa.*;
import com.aau.p3.climatetool.geoprocessing.TiffFileReader;
import com.aau.p3.climatetool.utilities.GeoDataReader;
import com.aau.p3.climatetool.utilities.ThresholdRepository;
import com.aau.p3.database.StaticThresholdRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Popup;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class PropertySearch {
    private final TextField addressSearchField;
    private final Popup suggestionsPopup = new Popup(); // Popup window with the suggested addresses
    private final ListView<String> suggestionsList = new ListView<>(); // List of the addresses for the popup window.
    private List<String> addresses = new ArrayList<>(); // List over the addresses that will be suggested to auto complete.
    private final Runnable onAddressSelected;
    private final PropertyManager propertyManager = Main.propertyManager;

    public PropertySearch(TextField addressSearchField, Runnable onAddressSelected) {
        this.addressSearchField = addressSearchField;
        this.onAddressSelected = onAddressSelected;
    }

    /**
     * Method for searching up addresses in the address field. Used by both the HydrologicalToolController and AddressLookupController.
     */
    public void searchAddress() {
        // Configuration for the Popup window to contain the ListView suggestionList, and auto hide it.
        suggestionsPopup.getContent().add(suggestionsList);
        suggestionsPopup.setAutoHide(true);

        // When the user types in the address field
        addressSearchField.textProperty().addListener((obs, oldText, newText) -> {
            if (!newText.isEmpty()) {
                // Takes the response and finds the addresses from the DawaGetAddress class and method
                DawaGetAddresses addressResponse = new DawaGetAddresses(newText);
                addresses = addressResponse.getAddresses();
                // Addresses are converted to an observable list so it can be put into our ListView suggestionList.
                ObservableList<String> observableAddresses = FXCollections.observableArrayList(addresses);
                if (!addresses.isEmpty()) {
                    suggestionsList.setItems(observableAddresses);
                    if (!suggestionsPopup.isShowing()) {
                        Bounds bounds = addressSearchField.localToScreen(addressSearchField.getBoundsInLocal()); // Finds the bounds for the address field
                        suggestionsPopup.show(addressSearchField.getScene().getWindow(), bounds.getMinX(), bounds.getMaxY()); //
                    }
                }
            } else {
                suggestionsPopup.hide();
            }
        });
        // Calls the methods selectItem on mouse click in suggestionsList
            suggestionsList.setOnMouseClicked(e -> selectItem());
        // Calls the method selectItem on a keypress, but only if it is the ENTER Key.
        suggestionsList.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                selectItem();
            }
        });
    }

    /** Method that takes the selected item from ListView and adds it to addressSearchField.
     * After this it checks if the selected text will result in the API call "type" adresse,
     * and if it does it then get the coordinates, Ownerlicense and Cadastre for the according property
     */
    private void selectItem() {
        String selected =  suggestionsList.getSelectionModel().getSelectedItem();
        if (selected != null){
            addressSearchField.setText(selected + " ");
            addressSearchField.positionCaret(selected.length() +1);
            DawaGetType type = new DawaGetType(selected);
            if (type.getType().equals("adresse") || type.getType().equals("adgangsadresse")) {
                String selectedAddress = URLEncoder.encode(selected, StandardCharsets.UTF_8);
                DawaGetEastingNorthing eastNorthCoordinates = new DawaGetEastingNorthing(selected);
                DawaGetLatLong latLong = new DawaGetLatLong(selected);
                DawaPropertyNumbers propertyNumbers = new DawaPropertyNumbers(eastNorthCoordinates.getEastingNorthing());
                DawaPolygonForAddress polygonForAddress = new DawaPolygonForAddress(propertyNumbers.getOwnerLicense(), propertyNumbers.getCadastre());
                suggestionsPopup.hide();

                if (propertyManager.checkPropertyExists(selectedAddress)) {
                    propertyManager.setCurrentProperty(propertyManager.getProperty(selectedAddress));
                } else {
                    /* Sets up the different readers for both the Geo data and the database.
                     *  Uses abstractions in form of interfaces (reference types) instead of concrete class types.
                     *  Follows the Dependency Inversion Principle */
                    GeoDataReader geoReader = new TiffFileReader();
                    ThresholdRepository thresholdRepo = new StaticThresholdRepository();

                    RiskFactory riskFactory = new RiskFactory(geoReader, thresholdRepo);
                    double[][] polygon = to2dArray(polygonForAddress.getPolygon());

                    Property newProperty = new Property(selectedAddress, polygonForAddress.getPolygon(), eastNorthCoordinates.getEastingNorthing(), latLong.getLatLong(), riskFactory.createRisks(polygon, eastNorthCoordinates.getEastingNorthing()));
                    newProperty.calculateClimateScore();
                    propertyManager.addProperty(newProperty);
                    propertyManager.setCurrentProperty(newProperty);
                }

                if (onAddressSelected != null) {
                    onAddressSelected.run();
                }

            }
        }
    }

    /**
     * Method for converting a List<List<double>> to a double[][]
     * @param list List to be converted to array
     * @return arr as a double[][]
     */
    public static double[][] to2dArray(List<List<Double>> list) {
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
}