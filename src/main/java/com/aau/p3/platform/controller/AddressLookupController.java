package com.aau.p3.platform.controller;

import com.aau.p3.climatetool.dawa.*;
import com.aau.p3.platform.model.casefile.Case;
import com.aau.p3.platform.model.casefile.Customer;
import com.aau.p3.platform.model.common.Address;
import com.aau.p3.platform.urlmanager.UrlAutoComplete;
import com.aau.p3.platform.utilities.StatusEnum;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.stage.Popup;

import java.util.ArrayList;
import java.util.List;

public class AddressLookupController {

    private List<String> addresses = new ArrayList<>();
    private String type = new String();

    @FXML private TableView<Case> myCasesTable;
    @FXML private TableColumn<Case, Integer> tableCaseID;
    @FXML private TableColumn<Case, String> tableTitle;   // address
    @FXML private TableColumn<Case, String> tableOwner;
    @FXML private TableColumn<Case, StatusEnum> tableStatus;

    @FXML
    private TextField addressField;

    private final Popup suggestionsPopup = new Popup();
    private final ListView<String> suggestionsList = new ListView<>();

    @FXML
    public void initialize() {
        /// Defensive: ensure FXML was wired
        if (myCasesTable == null) {
            System.err.println("myCasesTable is null — check fx:id and fx:controller in the FXML");
            return;
        }

        // Map columns using lambdas (explicit, avoids reflection issues)
        tableCaseID.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getCaseID()));
        tableTitle.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getAddress()));
        tableOwner.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getOwner()));
        // Convert StatusEnum to a readable String (you can change formatting here)
        tableStatus.setCellValueFactory(cell ->
                new SimpleObjectProperty<>(cell.getValue().getStatus()));

        // Mock Data — adjust Address/Customer constructors to match your real classes
        ObservableList<Case> mockData = FXCollections.observableArrayList(
                new Case(1,
                        new Address("Denmark", 8382, "Hinnerup", "Bondagervej", "5"),
                        new Customer("Alice Johnson", 9260, 22334455, "alice@johnson.com"),
                        StatusEnum.PENDING),
                new Case(2,
                        new Address("Denmark", 9280, "Storvorde", "Ceciliavej", "13"),
                        new Customer("Bob Smith", 9261, 33445566, "bob@smith.com"),
                        StatusEnum.APPROVED),
                new Case(3,
                        new Address("Denmark", 9260, "Gistrup", "Mølleskoven", "28"),
                        new Customer("Charlie Brown", 9263, 44556677, "charlie@brown.com"),
                        StatusEnum.REJECTED)
        );

        // Set data in the table
        myCasesTable.setItems(mockData);

        // Configuration for the Popup window to contain the ListView suggestionList, and auto hide it.
        suggestionsPopup.getContent().add(suggestionsList);
        suggestionsPopup.setAutoHide(true);

        // When the user types in the address field
        addressField.textProperty().addListener((obs, oldText, newText) -> {
            if (newText.length() > 1) {
                // Creates the API call from the UrlAutoComplete object with the given UrlString.
                UrlAutoComplete dawaAutoComplete = new UrlAutoComplete(newText);

                // Takes the response and finds the addresses from the DawaGetAddress class and method
                DawaGetAddressAutoComplete addressResponse = new DawaGetAddressAutoComplete();
                addresses = addressResponse.DawaGetAddressAutocomplete(dawaAutoComplete);
                // Addresses are converted to a observable list so it can be put into our ListView suggestionList.
                ObservableList<String> ObservableAddresses = FXCollections.observableArrayList(addresses);
                if (!addresses.isEmpty()) {
                    suggestionsList.setItems(ObservableAddresses);
                    if (!suggestionsPopup.isShowing()) {
                        Bounds bounds = addressField.localToScreen(addressField.getBoundsInLocal()); // Finds the bounds for the address field
                        suggestionsPopup.show(addressField.getScene().getWindow(), bounds.getMinX(), bounds.getMaxY()); //
                    }
                }
            } else {
                suggestionsPopup.hide();
            }
        });
        // calls the methods selectItem on mouse click in suggestionsList
        suggestionsList.setOnMouseClicked(e -> selectItem());
        // Calls the method selectItem on a keypress, but only if it is the ENNTER Key.
        suggestionsList.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                selectItem();
            }
        });
    }
    /* Method that takes the selected item from ListView and adds it to addressField.
     * After this it checks if the selected text will result in the API call "type" adresse,
     * and if it does it then get the coordinates, Ownerlicense and Cadastre for the according property */
    private void selectItem(){
        String selected = suggestionsList.getSelectionModel().getSelectedItem();
        if (selected != null){
            addressField.setText(selected);
            UrlAutoComplete buffer = new UrlAutoComplete(selected);
            DawaGetType typeResponse = new DawaGetType();
            this.type = typeResponse.DawaGetType(buffer);
            if (type.equals("adresse")){
                DawaGetCoordinates coordinates = new DawaGetCoordinates(selected);
                DawaPropertyNumbers propertyNumbers = new DawaPropertyNumbers(coordinates.getCoordinates());
                DawaPolygonForAddress polygonForAddress = new DawaPolygonForAddress(propertyNumbers.getOwnerLicense(),propertyNumbers.getCadastre());
                suggestionsPopup.hide();
            }
        }
    }
}
