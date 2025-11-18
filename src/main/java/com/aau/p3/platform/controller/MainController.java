package com.aau.p3.platform.controller;

import com.aau.p3.Main;
import com.aau.p3.platform.model.common.Address;
import com.aau.p3.platform.model.pdfcontents.PdfChapter;
import com.aau.p3.platform.model.pdfcontents.PdfClimateState;
import com.aau.p3.platform.model.pdfcontents.PdfOverview;
import com.aau.p3.platform.utilities.ControlledScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Region;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainController {
    /* contentArea is used to work as the area of the screen where the different "windows" will be shown.
    *  The specific name contentArea is needed as the tag @FXML connects the java code to the fxml id tag "contentArea" */
    @FXML private AnchorPane contentArea;

    /* void Method that is ALWAYS called during the initialization process of FXML from Main.java
    *  setCenter is called in this class and the page HomePage.fxml is set in the contentArea */
    @FXML
    public void initialize() {
        setCenter("/UI/AddressLookup.fxml");
    }

    /* setCenter method takes an FXML file
    type (window) and replaces the current window with that content */
    public void setCenter(String fxml) {
        try {
            /* Creates a FXMLloader object based on the given fxml file and loads it into the class Node (in javafx.scene)
            *  Node is a superclass to Parent in javafx that is used to hold any scene object, where Parent class only holds containers
            *  such as "Vbox, Hbox, etc." */
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));

            /* loader.load() returns the outermost tag <> in the fxml file */
            Node view = loader.load();

            /* Give sub-controller a reference back to this controller so a two-way communication is possible
            *  loader.getController() finds which controller os calling it from the FXML file that was found in getResource() */
            Object ctrl = loader.getController();

            /* Checks if the current controller is an instance of the interface ControlledScreen
            * such that the subcontroller implements the method setMainController() and can communicate with the MainController
            * while in theory not being linked at all with it */
            if (ctrl instanceof ControlledScreen cs) {
                cs.setMainController(this);
            }


            /* Prints out to show how the contentArea is replaces after each navigation in the GUI.
            *  contentArea.getChildren.setAll(view) is the code in charge of actually changing the FXML data below the StackPane tag
            * with id contentArea in the MainWindow.fxml */
            System.out.println("contentArea" + contentArea.getChildren());

            // Make the node resize to fill the StackPane
            view.setManaged(true);
            view.setVisible(true);
            StackPane.setAlignment(view, Pos.TOP_LEFT); // optional, usually default
            if (view instanceof Region region) {
                region.prefWidthProperty().bind(contentArea.widthProperty());
                region.prefHeightProperty().bind(contentArea.heightProperty());
            }

            AnchorPane.setTopAnchor(view, 0.0);
            AnchorPane.setBottomAnchor(view, 0.0);
            AnchorPane.setLeftAnchor(view, 0.0);
            AnchorPane.setRightAnchor(view, 0.0);

            contentArea.getChildren().setAll(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* @FXML tag is used to grab a hold of the specific onAction id "openHomePage" inside MainWindow.fxml */
    @FXML
    private void openMyCases(ActionEvent actionEvent) {
        setCenter("/UI/MyCases.fxml");
    }

    @FXML
    private void openAddressLookup(ActionEvent actionEvent) {
        System.out.println("Address search activated...");
        setCenter("/UI/AddressLookup.fxml");
    }

    @FXML
    private void openHydrologicalTool(ActionEvent actionEvent) {
        System.out.println("Hydrological tool activated...");
        setCenter("/UI/HydrologicalTool.fxml");
    }

    @FXML
    private void exportDocument (ActionEvent actionEvent) {
        System.out.println("Exporting document...");

        PDDocument document = new PDDocument();
        List<PdfChapter> chapters = new ArrayList<>();

        String soughtAddress = Main.propertyManager.currentProperty.getAddress();

        chapters.add(new PdfOverview(soughtAddress));
        chapters.add(new PdfClimateState(3, givenComment, climateRisksInfo));

        for (PdfChapter chapter : chapters) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                chapter.render(document, contentStream);
            }
        }

        document.save("report.pdf");
        document.close();
    } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void exitApp() {
        System.exit(0);
    }
}