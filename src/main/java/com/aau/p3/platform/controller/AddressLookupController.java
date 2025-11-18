package com.aau.p3.platform.controller;

import com.aau.p3.climatetool.dawa.*;
import com.aau.p3.platform.model.casefile.Case;
import com.aau.p3.platform.model.casefile.Customer;
import com.aau.p3.platform.urlmanager.UrlAutoComplete;
import com.aau.p3.platform.utilities.ControlledScreen;
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
import javafx.scene.input.KeyCode;
import javafx.stage.Popup;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class AddressLookupController implements ControlledScreen  {

    private MainController mainController;


    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    // List over the addresses that will be suggested to auto complete.
    private List<String> addresses = new ArrayList<>();

    @FXML private TableView<Case> myCasesTable;
    @FXML private TableColumn<Case, Integer> tableCaseID;
    @FXML private TableColumn<Case, String> tableTitle;   // address
    @FXML private TableColumn<Case, String> tableOwner;
    @FXML private TableColumn<Case, StatusEnum> tableStatus;

    @FXML
    private TextField addressField; // The field where the user types the address

    private final Popup suggestionsPopup = new Popup(); // Popup window with the suggested addresses
    private final ListView<String> suggestionsList = new ListView<>(); // List of the addresses for the popup window.

    @FXML
    public void initialize() {


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
                        URLDecoder.decode("Kildev%C3%A6ldet+5%2C+9000+Aalborg", StandardCharsets.UTF_8),
                        new Customer("Alice Johnson", 9260, 22334455, "alice@johnson.com"),
                        StatusEnum.PENDING),
                new Case(2,
                        URLDecoder.decode("Danmarksgade+88%2C+9000+Aalborg", StandardCharsets.UTF_8),
                        new Customer("Bob Smith", 9261, 33445566, "bob@smith.com"),
                        StatusEnum.APPROVED)
        );

        // Set data in the table
        myCasesTable.setItems(mockData);

        // Configuration for the Popup window to contain the ListView suggestionList, and auto hide it.
        suggestionsPopup.getContent().add(suggestionsList);
        suggestionsPopup.setAutoHide(true);

        // When the user types in the address field
        addressField.textProperty().addListener((obs, oldText, newText) -> {
            if (!newText.isEmpty()) {
                // Creates the API call from the UrlAutoComplete object with the given UrlString.
                UrlAutoComplete dawaAutoComplete = new UrlAutoComplete(newText);

                // Takes the response and finds the addresses from the DawaGetAddress class and method
                DawaGetAddresses addressResponse = new DawaGetAddresses(newText);
                addresses = addressResponse.getAddresses();
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
            addressField.setText(selected + " ");
            addressField.positionCaret(selected.length() +1);
            DawaGetType type = new DawaGetType(selected);
            if (type.getType().equals("adresse")){
                DawaGetCoordinates coordinates = new DawaGetCoordinates(selected);
                DawaPropertyNumbers propertyNumbers = new DawaPropertyNumbers(coordinates.getCoordinates());
                DawaPolygonForAddress polygonForAddress = new DawaPolygonForAddress(propertyNumbers.getOwnerLicense(),propertyNumbers.getCadastre());
                suggestionsPopup.hide();

                this.mainController.globalCoords = coordinates.getCoordinates();
                this.mainController.polygonCoords = polygonForAddress.getPolygon();
                hydrologicalTool();

                // Go to hydro tool
                //HydrologicalToolController.getWebEngine();
                // find addresse på map

                // få data for den addresse og print i console(til at starte med)

            }

        }
    }
    @FXML
    private void hydrologicalTool() {
        mainController.setCenter("/UI/HydrologicalTool.fxml");
    }
}
