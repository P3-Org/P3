package com.aau.p3.platform.controller;

import com.aau.p3.climatetool.dawa.DawaGetAddressAutoComplete;
import com.aau.p3.platform.urlmanager.UrlAutoComplete;
import com.aau.p3.platform.utilities.ControlledScreen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.lang.String;

import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Popup;

import java.util.ArrayList;
import java.util.List;

public class MyCasesController implements ControlledScreen {
    private MainController mainController;
    private List<String> addresses = new ArrayList<>();

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
        // When the user types in the address field

        addressField.textProperty().addListener((obs, oldText, newText) -> {
            if (newText.length() > 1) {
                UrlAutoComplete dawaAutocomplete = new UrlAutoComplete(newText);
                dawaAutocomplete.setUrlString("https://api.dataforsyningen.dk");
                DawaGetAddressAutoComplete addressResponse = new DawaGetAddressAutoComplete();
                addresses = addressResponse.DawaGetAddressAutocomplete(dawaAutocomplete);
                ObservableList<String> ObservableAddresses = FXCollections.observableArrayList(addresses);
                if (!addresses.isEmpty()) {
                    suggestionsList.setItems(ObservableAddresses);

                }

                suggestionsPopup.getContent().add(suggestionsList);
                suggestionsPopup.setAutoHide(true);





                        //var answer = dawaAutocomplete.getAutoComplete();
                        //System.out.println(answer);



                };

        });
    };

    /* @FXML tag connects to the onAction in the AllCases.fxml file that is called handleAllCases.
    *  This method is triggerred once the button is clicked.
    *  This method sends the fxml file to MainControllers method setCenter */
    @FXML
    private void handleAllCases() {
        mainController.setCenter("/UI/AllCases.fxml");
    }

}