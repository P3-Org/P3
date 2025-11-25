package com.aau.p3.platform.controller;

import com.aau.p3.platform.model.casefile.Case;
import com.aau.p3.platform.model.casefile.Customer;
import com.aau.p3.platform.model.property.PropertySearch;
import com.aau.p3.platform.utilities.ControlledScreen;
import com.aau.p3.platform.utilities.StatusEnum;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.nio.charset.StandardCharsets;
import java.net.URLDecoder;

/**
 * Class that handles the Address look up controller and window
 */
public class AddressLookupController implements ControlledScreen  {
    @FXML private TextField addressSearchField; // The field where the user types the address
    @FXML private TableView<Case> myCasesTable;
    @FXML private TableColumn<Case, Integer> tableCaseID;
    @FXML private TableColumn<Case, String> tableTitle;   // address
    @FXML private TableColumn<Case, String> tableOwner;
    @FXML private TableColumn<Case, StatusEnum> tableStatus;

    private MainController mainController;

    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

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

        /* Creates a PropertySearch object to be used for looking up an address using DAWA api. It passes the field that will be filled out, and a callback method
           responsible for calling the mainController ones the address has been filled out. */
        PropertySearch search = new PropertySearch(addressSearchField, () -> setWindowHydrologicalTool());
        search.searchAddress();

    }

    /**
     * Method for switching UI window to HydrologicalTool.fxml
     */
    @FXML
    private void setWindowHydrologicalTool() {
        mainController.setCenter("/ui/fxml/HydrologicalTool.fxml");
    }
}