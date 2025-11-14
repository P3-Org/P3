package com.aau.p3.platform.controller;

import com.aau.p3.climatetool.dawa.*;
import com.aau.p3.platform.urlmanager.UrlAutoComplete;
import com.aau.p3.platform.utilities.ControlledScreen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.lang.String;

import javafx.geometry.Bounds;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Popup;

import java.util.ArrayList;
import java.util.List;

public class MyCasesController implements ControlledScreen {
    private MainController mainController;
    private List<String> addresses = new ArrayList<>();
    private String type = new String();

    /* Method from the interface ControlledScreen that is used to pass the reference of MainController
    *  to the current controller */
    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private TextField addressField;

    private final Popup suggestionsPopup = new Popup();
    private final ListView<String> suggestionsList = new ListView<>();

    @FXML
    public void initialize() {
        // Configuration for the Popup window to contain the ListView suggestionList, and auto hide it.
        suggestionsPopup.getContent().add(suggestionsList);
        suggestionsPopup.setAutoHide(true);

        // When the user types in the address field
        addressField.textProperty().addListener((obs, oldText, newText) -> {
            if (newText.length() > 1) {
                UrlAutoComplete dawaAutoComplete = new UrlAutoComplete(newText);
                dawaAutoComplete.setUrlString("https://api.dataforsyningen.dk");
                DawaGetAddressAutoComplete addressResponse = new DawaGetAddressAutoComplete();
                addresses = addressResponse.DawaGetAddressAutocomplete(dawaAutoComplete);
                ObservableList<String> ObservableAddresses = FXCollections.observableArrayList(addresses);
                if (!addresses.isEmpty()) {
                    suggestionsList.setItems(ObservableAddresses);
                    if (!suggestionsPopup.isShowing()) {
                        Bounds bounds = addressField.localToScreen(addressField.getBoundsInLocal());
                        suggestionsPopup.show(addressField.getScene().getWindow(),
                                bounds.getMinX(),
                                bounds.getMaxY());
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

    /* @FXML tag connects to the onAction in the AllCases.fxml file that is called handleAllCases.
    *  This method is triggerred once the button is clicked.
    *  This method sends the fxml file to MainControllers method setCenter */
    @FXML
    private void handleAllCases() {
        mainController.setCenter("/UI/AllCases.fxml");
    }

}