package com.aau.p3.platform.controller;

import com.aau.p3.platform.model.casefile.Case;
import com.aau.p3.platform.model.casefile.Customer;
import com.aau.p3.platform.model.common.Address;
import com.aau.p3.platform.utilities.StatusEnum;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AddressLookupController {

    @FXML private TableView<Case> myCasesTable;
    @FXML private TableColumn<Case, Integer> tableCaseID;
    @FXML private TableColumn<Case, String> tableTitle;   // address
    @FXML private TableColumn<Case, String> tableOwner;
    @FXML private TableColumn<Case, StatusEnum> tableStatus;

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
    }
}
