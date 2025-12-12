package com.aau.p3.platform.controller;

import com.aau.p3.Main;
import com.aau.p3.platform.model.property.PropertySearch;
import com.aau.p3.platform.utilities.ControlledScreen;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

    @Test
    void HydrologicalToolIntergration() throws TimeoutException {
        Assertions.assertEquals("AddressLookupController", activeScreen.toString());

        // Wait max 10 seconds for program to start
        WaitForAsyncUtils.waitFor(10, TimeUnit.SECONDS, () ->
                        lookup("#addressSearchField").tryQuery().isPresent()
        );

        // Simulate a user
        // Change address search filed to test Address
        System.out.println("Søger adresse!");
        interact(() -> lookup("#addressSearchField").queryAs(TextField.class)
                .setText(testAddress));
        push(KeyCode.ENTER);
        System.out.println("Adresse søgt!");

        // Wait max 20 seconds for data to be gathered from API/DB
        WaitForAsyncUtils.waitFor(20, TimeUnit.SECONDS, () ->
                lookup("#scoreDownButton").tryQuery().isPresent()

        );
        System.out.println("Vi fandt et map anchor!");
        activeScreen = MainController.getActiveScreen();
        Assertions.assertEquals("HydrologicalToolController", activeScreen.toString());

    }

}