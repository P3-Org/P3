package com.aau.p3.platform.controller;

import com.aau.p3.Main;
import com.aau.p3.platform.model.property.PropertySearch;
import com.aau.p3.platform.utilities.ControlledScreen;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import javafx.scene.control.TextField;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import static org.junit.jupiter.api.Assertions.*;

class AddressLookupControllerTest extends ApplicationTest {

    MainController mainController = new MainController();

    ControlledScreen activeScreen;
    String testAddress;

    @BeforeEach
    void setup() {
        activeScreen = MainController.getActiveScreen();
        testAddress = "Danmarksgade 88, 9000 Aalborg";
    }


    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/MainWindow.fxml"));
        Parent root = loader.load();
        mainController = loader.getController();

        stage.setScene(new javafx.scene.Scene(root));
        stage.show();
    }

    @Tag("exclude")
    @Test
    void HydrologicalToolIntergration() throws TimeoutException {

        // Wait max 10 seconds for program to start
        WaitForAsyncUtils.waitFor(10, TimeUnit.SECONDS, () ->
                lookup("#addressSearchField").tryQuery().isPresent()
        );

        // Assert if the active page is "AddressLookup"
        Assertions.assertEquals("AddressLookupController", activeScreen.toString());


        // Simulate a user
        // Change address search filed to test Address
        interact(() -> lookup("#addressSearchField").queryAs(TextField.class)
                .setText(testAddress));
        push(KeyCode.ENTER);


        // Wait max 30 seconds for data to be gathered from API/DB
        WaitForAsyncUtils.waitFor(30, TimeUnit.SECONDS, () ->
                lookup("#scoreDownButton").tryQuery().isPresent()
        );

        // Assert the screen has changed
        activeScreen = MainController.getActiveScreen();
        Assertions.assertEquals("HydrologicalToolController", activeScreen.toString());

    }

}