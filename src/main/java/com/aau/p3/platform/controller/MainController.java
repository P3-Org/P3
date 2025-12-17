package com.aau.p3.platform.controller;

import com.aau.p3.Main;
import com.aau.p3.platform.model.pdfcontents.PDFChapter;
import com.aau.p3.platform.model.pdfcontents.PDFClimateState;
import com.aau.p3.platform.model.pdfcontents.PDFOverview;
import com.aau.p3.platform.model.property.Property;
import com.aau.p3.platform.utilities.ControlledScreen;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Region;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.aau.p3.platform.utilities.openPdfFile;

/**
 * Class that handles the main controller and the windows that can be called from within it.
 */
public class MainController {
    @FXML private Button climateLookupButton;

    /* contentArea functions as the area of the screen where the different "windows" will be shown.
    *  must have the name contentArea, as the @FXML tag connects the java code to the fx:id "contentArea" */
    @FXML private AnchorPane contentArea;

    private static ControlledScreen activeScreen;
    Object ctrl;

    /**
     * Method that is ALWAYS called during the initialization process of FXML from Main.java.
     * setCenter is called in this class, and the page AddressLookup.fxml is inserted in the contentArea
     */
    @FXML
    public void initialize() {
        setCenter("/ui/fxml/AddressLookup.fxml");
        updateClimateButtonVisibility();
    }

    /**
     * Method called after initialization. If no currentProperty is assigned, hide the "Klimaopslag"
     * button, and when assigned showcase it. setManaged() ensures the toolbar does not leave a blank space.
     */
    public void updateClimateButtonVisibility() {
        boolean hasProperty = Main.propertyManager.currentProperty != null;
        climateLookupButton.setVisible(hasProperty);
        climateLookupButton.setManaged(hasProperty);
    }

    /**
     * Method that takes an FXML file (window), and replaces the current window with the given content.
     * @param fxml The FXML file that is to be shown upon call.
     */
    public void setCenter(String fxml) {
        try {
            /* Creates a FXMLLoader object based on the given FXML file and loads it into the class Node (in javafx.scene).
            *  Node is a superclass to Parent in JavaFX that is used to hold any scene object,
            * where Parent class only holds containers such as Vbox, Hbox, etc. */
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));

            /* loader.load() returns the outermost tag <> in the FXML file */
            Node view = loader.load();

            /* Give the sub-controller a reference back to this controller, so a two-way communication is possible.
            *  loader.getController() finds which controller is calling it from the FXML file that was found in getResource() */
            this.ctrl = loader.getController();

            /* Checks if the current controller is an instance of the interface ControlledScreen, such that
            * the subcontroller implements the method setMainController(), and can communicate with the MainController
            * while not theoretically being linked with it */
            if (ctrl instanceof ControlledScreen cs) {
                cs.setMainController(this);

                    // Runs afterInitialize() after main controller is set and variables are moved.
                if (ctrl instanceof HydrologicalToolController htc) {
                    htc.afterInitialize();
                }
                activeScreen = cs;
            } else {
                activeScreen = null;
            }

            /* Prints out to show how the contentArea is replaces after each navigation in the GUI.
             *  contentArea.getChildren.setAll(view) is the code in charge of actually changing the FXML data below the StackPane tag
             * with id contentArea in the MainWindow.fxml */
            System.out.println("contentArea" + contentArea.getChildren());

            // Make the node resize to fill the StackPane.
            view.setManaged(true);
            view.setVisible(true);
            StackPane.setAlignment(view, Pos.TOP_LEFT); // Ensures alignment, but usually default.

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

    /**
     * Method for centering AddressLookup.fxml.
     */
    @FXML
    private void openAddressLookup() {
        setCenter("/ui/fxml/AddressLookup.fxml");
    }

    /**
     * Method for centering HydrologicalTool.fxml.
     */
    @FXML
    private void openHydrologicalTool() {
        setCenter("/ui/fxml/HydrologicalTool.fxml");
    }

    /**
     * Method for creating a PDF document, upon activating the export button.
     */
    @FXML
    private void exportDocument () throws IOException {
        // Initialize the PDF itself.
        PDDocument document = new PDDocument();
        List<PDFChapter> chapters = new ArrayList<>();

        // Gather information on what the current property is.
        Property currentProperty = Main.propertyManager.currentProperty;

        // Add chapters with content (structured in respective classes) to list.
        chapters.add(new PDFOverview(currentProperty.getAddress()));
        chapters.add(new PDFClimateState(currentProperty));

        for (PDFChapter chapter : chapters) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                chapter.render(document, contentStream);
            }
        }

        document.save("report.pdf");
        System.out.println("Document saved!"); // Notify in the terminal, the document was attempted saved.
        openPdfFile.openPdf();
        document.close();
    }

    /**
     * Quits and shuts down the program upon pressing the exit button.
     */
    public static ControlledScreen getActiveScreen() {
        return activeScreen;
    }

    @FXML
    private void exitApp() {
        System.exit(0);
    }

    @Override
    public String toString() {
        return "MainController";
    }
}