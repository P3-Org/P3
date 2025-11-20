package com.aau.p3.platform.model.property;

import com.aau.p3.database.PropertyRepository;

import java.util.HashMap;

/**
 * Class that creates a hashmap of properties, identifiable by their address in string format.
 * Allows to save information on specific properties both in the hash map but also the database, to easily access again later
 */
public class PropertyManager {
    private static final HashMap<String, Property> propertyList = new HashMap<>(); // read from DB instead of making new hashmap

    // Current property holds the last searched property, allowing for it to be easily accessible until a new one is searched
    public Property currentProperty;

    // If the property exists, we do not have to perform new valuations
    public static boolean checkPropertyExists(String address) {
        // Check if the property is in the local cache
        if (propertyList.containsKey(address)) {
            return true;
        }

        // If the property doesn't exit in the local cache we check if it exists in the Database
        Property loaded = PropertyRepository.loadProperty(address);
        if (loaded != null) {
            addProperty(loaded);
            return true;
        }

        // If the property doesn't exist in either memory or the database we return false
        return false;
    }

    // New property searched, add it to list as well as the database
    public static void addProperty(Property property) {
        propertyList.put(property.getAddress(), property); // Adds the property to property which is stored in memory
        PropertyRepository.saveProperty(property); // Saves to property to the database
    }

    public static Property getProperty(String address) {
        return propertyList.get(address);
    }

    public void setCurrentProperty(Property currentProperty ){
        this.currentProperty = currentProperty;
    }
}