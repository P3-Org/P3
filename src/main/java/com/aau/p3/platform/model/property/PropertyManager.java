package com.aau.p3.platform.model.property;

import com.aau.p3.database.PropertyRepository;
import java.util.HashMap;

/**
 * Class that handles the storing of properties.
 * Allows to save information on specific properties both in the hash map (local cache) but also in the database, to be easily accessible later on
 */
public class PropertyManager {
    // The hash map creation (local cache)
    private static HashMap<String, Property> propertyList = new HashMap<>();

    // Current property holds the last searched property, allowing for it to be easily accessible until a new one is searched
    public Property currentProperty;

    /**
     * Method for checking if a property exists in either the local cache or database.
     * @param address contains the address of the property being checked
     * @return true or false
     */
    public static boolean checkPropertyExists(String address) {
        // Check if the property is in the local cache
        if (propertyList.containsKey(address)) {
            return true;
        }

        /* If the property doesn't exist in the local cache, we check if it exists in the database.
        *  If it exists in the DB we add it to the local cache and return true. */
        Property loaded = PropertyRepository.loadProperty(address);
        if (loaded != null) {
            propertyList.put(loaded.getAddress(), loaded); // Adds the property to property which is stored in memory
            return true;
        }

        // If the property does not exist in either memory or the database we return false
        return false;
    }

    /**
     * When a new property is created this method will add it to the hash map and the database.
     * @param property is the property object to be added
     */
    public static void addProperty(Property property) {
        propertyList.put(property.getAddress(), property); // Adds the property to property which is stored in memory
        PropertyRepository.saveProperty(property); // Saves to property to the database
    }

    /**
     * Method for updating the specialist adjustment inside the database
     * @param property is the corresponding property object
     */
    public static void updateDBSpecialistAdjustment(Property property) {
        String address = property.getAddress();
        PropertyRepository.updateSpecialistAdjustment(address, property.getSpecialistAdjustment());
    }

    /**
     * Method that adds a new comment to the database.
     * @param property the property, that the comment is to be added to
     * @param newComment the comment to be added
     */
    public static void addCommentToDB(Property property, String newComment) {
        String address = property.getAddress();
        PropertyRepository.addComment(address, newComment);
    }

    /**
     * Getter method, provides the property object for the given address inside the hash map
     * @param address for the property
     * @return property object with the given address
     */
    public static Property getProperty(String address) {
        return propertyList.get(address);
    }

    /**
     * Setter method, sets the current property to the one given
     * @param newCurrentProperty property to be set as currentProperty
     */
    public void setCurrentProperty(Property newCurrentProperty ){
        this.currentProperty = newCurrentProperty;
    }

    /**
     * Method for emptying out the hash map. Does this by replacing it with a new empty hash map
     */
    public static void emptyMemory() {
        propertyList = new HashMap<>();
    }
}