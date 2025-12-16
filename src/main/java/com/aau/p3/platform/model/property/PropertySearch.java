package com.aau.p3.platform.model.property;

import com.aau.p3.Main;
import com.aau.p3.climatetool.dawa.*;
import com.aau.p3.climatetool.geoprocessing.TiffFileReader;
import com.aau.p3.climatetool.utilities.GeoDataReader;
import com.aau.p3.climatetool.utilities.ThresholdRepositoryInterface;
import com.aau.p3.database.ThresholdRepository;
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

/**
 * Class responsible for handling the search of a property and utilizing the DAWA API
 */
public class PropertySearch {
    private final TextField addressSearchField;
    private final Popup suggestionsPopup = new Popup(); // Popup window with the suggested addresses
    private final ListView<String> suggestionsList = new ListView<>(); // List of the addresses for the popup window
    private final Runnable onAddressSelected;
    private List<String> addresses = new ArrayList<>(); // List over the addresses that will be suggested to auto complete.
    private final PropertyManager propertyManager = Main.propertyManager;

    /**
     * Constructor for PropertySearch
     * @param addressSearchField contains the text field where the address can be searched
     * @param onAddressSelected is a runnable variable that will run lambda functions which will be explicitly defined in the creation of a PropertySearch object
     */
    public PropertySearch(TextField addressSearchField, Runnable onAddressSelected) {
        this.addressSearchField = addressSearchField;
        this.onAddressSelected = onAddressSelected;
    }

    /**
     * Method for searching up addresses in the address field. Used by both the {@code HydrologicalToolController} and {@code AddressLookupController}.
     */
    public void searchAddress() {
        // Configuration for the Popup window to contain the ListView suggestionList, and auto hide it
        suggestionsPopup.getContent().add(suggestionsList);
        suggestionsPopup.setAutoHide(true);

        // When the user types in the address field, the event listener will be called
        addressSearchField.textProperty().addListener((obs, oldText, newText) -> {
            if (!newText.isEmpty()) {
                // Fetch the response from DAWA and find the addresses from the DawaGetAddress class and method
                DawaGetAddresses addressResponse = new DawaGetAddresses(newText);
                addresses = addressResponse.getAddresses();

                // Addresses are converted to an observable list so it can be put into our ListView suggestionList.
                ObservableList<String> observableAddresses = FXCollections.observableArrayList(addresses);

                if (!addresses.isEmpty()) {
                    // Display suggestions in popup below the text field
                    suggestionsList.setItems(observableAddresses);
                    if (!suggestionsPopup.isShowing()) {
                        Bounds bounds = addressSearchField.localToScreen(addressSearchField.getBoundsInLocal()); // Finds the bounds for the address field
                        suggestionsPopup.show(addressSearchField.getScene().getWindow(), bounds.getMinX(), bounds.getMaxY()); //
                    }
                }
            } else {
                // Hide suggestions when input field is empty
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

    /**
     * Method that takes the selected item from ListView and adds it to addressSearchField.
     * Following this, it checks if the selected text will result in the API call "type" address,
     * and if it does it then gets the coordinates, OwnerLicense and Cadastre for the according property.
     * Checks if the property exist in the DB - if the property already exist it sets the property to the current one.
     * If it does not exist - it creates a new property and saves it to the DB, and then sets it to the current property.
     */
    private void selectItem() {
        // Selected is the selected address from the text field
        String selected = suggestionsList.getSelectionModel().getSelectedItem();

        if (selected != null) {
            /* Helps auto complete the address search.
             * Example: Writing Danmarksgade, which is just a road, and pressing it will paste it to the text field, make a space and positon the caret there. */
            addressSearchField.setText(selected + " ");
            addressSearchField.positionCaret(selected.length() + 1);

            // The type of the searched text is checked and returned
            DawaGetType type = new DawaGetType(selected);

            /* Checks if the type is an address or simply a road name etc. If it is anything other than a concrete address, it will not run. */
            if (type.getType().equals("adresse") || type.getType().equals("adgangsadresse")) {
                // The following code calls the DAWA API and gathers all relevant data needed for the property creation
                String selectedAddress = URLEncoder.encode(selected, StandardCharsets.UTF_8);
                DawaGetEastingNorthing eastNorthCoordinates = new DawaGetEastingNorthing(selected);
                DawaGetLatLong latLong = new DawaGetLatLong(selected);
                DawaPropertyNumbers propertyNumbers = new DawaPropertyNumbers(eastNorthCoordinates.getEastingNorthing());
                DawaPolygonForAddress polygonForAddress = new DawaPolygonForAddress(propertyNumbers.getOwnerLicense(), propertyNumbers.getCadastre());

                // Hides the popup window
                suggestionsPopup.hide();

                // Checks if the property exists either within the local cache or the database. If it does, set it to the current property
                if (propertyManager.checkPropertyExists(selectedAddress)) {
                    propertyManager.setCurrentProperty(propertyManager.getProperty(selectedAddress));
                } else {
                    /* Sets up the different readers for both the Geo data and the threshold database reader.
                     *  Uses abstractions in form of interfaces (reference types) instead of concrete class types.
                     *  Follows the Dependency Inversion Principle */
                    GeoDataReader geoReader = new TiffFileReader();
                    ThresholdRepositoryInterface thresholdRepo = new ThresholdRepository();

                    // Initializes the riskFactory that will be used to create the risks for the property
                    RiskFactory riskFactory = new RiskFactory(geoReader, thresholdRepo);

                    // Finds the polygon coordinates for the property (lat/long coordinates)
                    double[][] polygon = to2dArray(polygonForAddress.getPolygon());

                    // Creates a new property object with relevant input parameters, like creating the risks from RiskFactory
                    Property newProperty = new Property(selectedAddress, polygonForAddress.getPolygon(), eastNorthCoordinates.getEastingNorthing(), latLong.getLatLong(), riskFactory.createRisks(polygon, eastNorthCoordinates.getEastingNorthing()));

                    // Calculates the climate score for the property
                    newProperty.calculateClimateScore();

                    // Add the property to the local cache and the database
                    propertyManager.addProperty(newProperty);

                    // Set the current property to the newly created property
                    propertyManager.setCurrentProperty(newProperty);
                }

                /* Runnable method that will be run as long as it isn't null.
                 * Thus, when the PropertySearch object is created, the function will be called here, as long as the passed parameter isn't null */
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