package com.aau.p3.platform.model.property;

import com.aau.p3.database.PropertyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class PropertyManagerTest {
    static String testAddress;
    static Property property;

    @BeforeEach
    void setup() throws NoSuchFieldException, IllegalAccessException {
        testAddress = "Heimdalsgade 12, 9000 Aalborg";

        // Mock the property class
        property = Mockito.mock(Property.class);

        // Since only property.getAddress() is used within PropertyManager, we mock the return.
        Mockito.when(property.getAddress()).thenReturn(testAddress);

        // Empty the HashMap inside PropertyManager
        PropertyManager.emptyMemory();

        // Insert directly into the internal PropertyManager map using Java reflections
        var field = PropertyManager.class.getDeclaredField("propertyList");

        // Sets the private field to accessible
        field.setAccessible(true);

        // Get the value of the static propertyList field (no instance needed so we sent null)
        HashMap<String, Property> internalMap = (HashMap<String, Property>) field.get(null);

        // Place the address and property into the propertyList in PropertyManager
        internalMap.put(testAddress, property);
    }

    @Test
    void checkPropertyExistsTrueInCache() {
        boolean returnBool = PropertyManager.checkPropertyExists(testAddress);
        assertTrue(returnBool);
    }

    @Test
    void checkPropertyExistsFalseInCache() {
        PropertyManager.emptyMemory();
        boolean returnBool = PropertyManager.checkPropertyExists(testAddress);
        assertFalse(returnBool);
    }

//    @Test
//    void checkPropertyExistsInDBNotCache() {
//        try (MockedStatic<PropertyRepository> mockRepo = Mockito.mockStatic(PropertyRepository.class)) {
//
//            // Saves a property
//            mockRepo.when(() -> PropertyRepository.saveProperty(property)).thenReturn("Property saved.");
//
//            //
//            mockRepo.when(() -> PropertyRepository.loadProperty(testAddress))
//                    .thenReturn(property);
//
//            boolean returnBool = PropertyManager.checkPropertyExists(testAddress);
//            assertFalse(returnBool);
//
//        }
//    }

    @Test
    void addProperty() {
    }

    @Test
    void updateDBSpecialistScore() {
    }

    @Test
    void addCommentToDB() {
    }

    @Test
    void getProperty() {
    }

    @Test
    void setCurrentProperty() {
    }
}