package com.aau.p3.platform.model.property;

import com.aau.p3.database.PropertyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;

class PropertyManagerTest {
    static String testAddress;
    static Property property;

    @BeforeEach
    void setup() {
        testAddress = "Heimdalsgade 12, 9000 Aalborg";

        // Mock the property class
        property = Mockito.mock(Property.class);

        // Since only property.getAddress() is used within PropertyManager, we mock the return.
        Mockito.when(property.getAddress()).thenReturn(testAddress);

        // Empty the HashMap inside PropertyManager
        PropertyManager.emptyMemory();
    }

    @Test
    void checkPropertyExistsTrueInCache() throws IllegalAccessException, NoSuchFieldException {
        try (MockedStatic<PropertyRepository> mockRepo = Mockito.mockStatic(PropertyRepository.class)) {

            // Start by making sure there is no property inside the DB. This makes it such that the method won't return true unless it is inside the hashmap
            mockRepo.when(() -> PropertyRepository.loadProperty(testAddress)).thenReturn(null);

            // Insert directly into the internal PropertyManager map using Java reflections
            var field = PropertyManager.class.getDeclaredField("propertyList");

            // Sets the private field to accessible
            field.setAccessible(true);

            // Get the value of the static propertyList field (no instance needed so we sent null)
            HashMap<String, Property> internalMap = (HashMap<String, Property>) field.get(null);

            // Place the address and property into the propertyList in PropertyManager
            internalMap.put(testAddress, property);

            boolean returnBool = PropertyManager.checkPropertyExists(testAddress);
            assertTrue(returnBool);
        }
    }

    @Test
    void checkPropertyExistsFalseInCacheAndDB() {
        // Try-resource block used because MockedStatic opens a static mock that would never be closed once it's done
        try (MockedStatic<PropertyRepository> mockRepo = Mockito.mockStatic(PropertyRepository.class)) {

            mockRepo.when(() -> PropertyRepository.loadProperty(testAddress)).thenReturn(null);
            boolean returnBool = PropertyManager.checkPropertyExists(testAddress);
            assertFalse(returnBool);
        }
    }

    @Test
    void checkPropertyExistsInDB() {
        // Try-resource block used because MockedStatic opens a static mock that would never be closed once it's done
        try (MockedStatic<PropertyRepository> mockRepo = Mockito.mockStatic(PropertyRepository.class)) {

            // Loads a property using the database, but instead it always returns the property - which is the same that we just saved.
            mockRepo.when(() -> PropertyRepository.loadProperty(testAddress))
                    .thenReturn(property);

            // Checks the boolean condition of checkPropertyExists, which should return true, as it is found in the DB
            boolean returnBool = PropertyManager.checkPropertyExists(testAddress);
            assertTrue(returnBool);
        }
    }

    @Test
    void addPropertyToHashMap() {
        // Try-resource block used because MockedStatic opens a static mock that would never be closed once it's done
        try (MockedStatic<PropertyRepository> mockRepo = Mockito.mockStatic(PropertyRepository.class)) {
            mockRepo.when(() -> PropertyRepository.saveProperty(property)).thenAnswer((Answer<Void>) invocation -> null);

            PropertyManager.addProperty(property);
            assertSame(property, PropertyManager.getProperty(property.getAddress()));
        }
    }
}