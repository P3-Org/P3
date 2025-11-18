package com.aau.p3.platform.model.property;

import java.util.HashMap;

public class PropertyManager {
    private static final HashMap<String, Property> propertyList = new HashMap<>();
    public Property currentProperty;

    public static boolean checkPropertyExists(String address) {
        return propertyList.containsKey(address);
    }

    public static void addProperty(Property property) {
        propertyList.put(property.getAddress(), property);
    }

    public static Property getProperty(String address) {
        return propertyList.get(address);
    }
    public void setCurrentProperty(Property currentProperty ){
        this.currentProperty = currentProperty;
    }
}