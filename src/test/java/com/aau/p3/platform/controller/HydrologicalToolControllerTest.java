package com.aau.p3.platform.controller;

import com.aau.p3.platform.model.property.Property;
import javafx.scene.control.Button;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class HydrologicalToolControllerTest {

    HydrologicalToolController controller;
    Property p;

    @BeforeAll
    static void initJavaFx() {
        javafx.application.Platform.startup(() -> {});
    }

    @BeforeEach
    void setup() throws NoSuchFieldException, IllegalAccessException {
        /* Arrange */
        controller = new HydrologicalToolController();
        p = Mockito.mock(Property.class);

        // Gets a reference to the private currentProperty field inside Hydrological controller.
        Field propertyField = HydrologicalToolController.class.getDeclaredField("currentProperty");

        // Allow accessing of private fields with setAccesible(true)
        propertyField.setAccessible(true);

        // Set the "currentProperty" field of the controller instance to the mocked Property object
        propertyField.set(controller, p);

        Field upField = HydrologicalToolController.class.getDeclaredField("scoreUpButton");
        upField.setAccessible(true);
        upField.set(controller, new Button());

        Field downField = HydrologicalToolController.class.getDeclaredField("scoreDownButton");
        downField.setAccessible(true);
        downField.set(controller, new Button());
    }

    @Test
    void testUpdateButtonsNoUp() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        /* Arrange */
        when(p.getClimateScore()).thenReturn(5);
        when(p.getSpecialistScore()).thenReturn(-1);

        Method updateMethod = HydrologicalToolController.class.getDeclaredMethod("updateScoreButtons");
        updateMethod.setAccessible(true);

        /* Act */
        // Execute the updateScoreButtons() method on this specific controller instance, which allows for the button visibility logic to run
        updateMethod.invoke(controller);

        // Read updated button state
        Field upField = HydrologicalToolController.class.getDeclaredField("scoreUpButton");
        upField.setAccessible(true);
        Button scoreUpButton = (Button) upField.get(controller);

        Field downField = HydrologicalToolController.class.getDeclaredField("scoreDownButton");
        downField.setAccessible(true);
        Button scoreDownButton = (Button) downField.get(controller);

        /* Assert */
        // A climate score of 5 is the maximum allowed score,
        // so the controller should hide the "increase" button.
        assertFalse(scoreUpButton.isVisible());

        // A specialist score of -1 represents a negative adjustment,
        // which should hide the "decrease" button.
        assertFalse(scoreDownButton.isVisible());
    }
}