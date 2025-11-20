package com.aau.p3.platform.model.property;

import java.util.HashMap;

/**
 * Class that creates a hashmap of properties, identifiable by their address in string format.
 * Allows to save information on specific properties, to easily access again later
 * @Author Batman
 */
public class PropertyManager {
    private static final HashMap<String, Property> propertyList = new HashMap<>();

    // Current property holds the last searched property, allowing for it to be easily accessible until a new one is searched
    public Property currentProperty;

    // If the property exists, we do not have to perform new valuations
    public static boolean checkPropertyExists(String address) {
        return propertyList.containsKey(address);
    }

    // New property searched, add it to list
    public static void addProperty(Property property) {
        propertyList.put(property.getAddress(), property);
    }

    // Getter and setter
    public static Property getProperty(String address) {
        return propertyList.get(address);
    }

    public void setCurrentProperty(Property currentProperty ){
        this.currentProperty = currentProperty;
    }
}