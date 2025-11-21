package com.aau.p3.database;

import com.aau.p3.climatetool.utilities.RiskAssessment;
import com.aau.p3.platform.model.property.Property;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PropertyRepository {

    /**
     * Gson instance configured to ignore the "riskAssessment" field during serialization.
     * Property objects cannot serialize their List<RiskAssessment> directly, since the list contains
     * interface types. To avoid errors when saving to the database, this exclusion strategy removes
     * the field, allowing us to store a clean JSON version of the Property. The risk results are instead
     * saved separately as DTOs and restored manually when loading a property.
     */
    private static final Gson gson = new GsonBuilder().addSerializationExclusionStrategy(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    return f.getName().equals("riskAssessment");
                }
                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
            })
            .create();

    /**
     * loadProperty looks if a previously stored property matches the searched address.
     * IMPORTANT SIDE NOTE: since Property objects are stores as strings in the database we need to reconstruct it, once we find a matching address in the database. However we cant restore our List<RiskAssessment> since the reference type is an interface.
     * To work around this, We replace the List<RiskAssessments> with a new list where we only store the actual results from the risk objects.
     * @param address contains the address that is being looked up
     * @return the reconstructed property object found in the database. If no mathcing object exists it returns null
     */
    public static Property loadProperty(String address) {
        String sql = "SELECT propertyObject FROM properties WHERE Address = ?";

        /* Try-catch block that handles the connection to the database, and prepares a statement to be executed to the database.
         *  PreparedStatement also accepts parameters to be filled. In this case only one parameter needs to be filled in based on the address provided. */
        try (Connection conn = ConnectToDB.connect("climateTool.db");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, address);
            ResultSet rs = stmt.executeQuery();

            // If there isn't a row containing that address, return null (error handling)
            if (!rs.next()) return null;

            // Parses the TEXT field in the DB propertyObject to a JsonObject
            JsonObject json = JsonParser.parseString(rs.getString("propertyObject")).getAsJsonObject();

            /* Beginning of reconstruction : The idea of the reconstruction is to transplant our placeholder information in the form of a List<RiskToDTO> back to a List<RiskAssessment> */
            // Create a new list which will contain our saved results.
            List<RiskToDTO> dtos = new ArrayList<>();
            if (json.has("RiskToDTOs")) {
                // create a new list of type <RiskToDTO> from the json data
                dtos = gson.fromJson(json.get("RiskToDTOs"),
                        new TypeToken<List<RiskToDTO>>() {}.getType());
            }

            // Remove the placeholder information from the json object as we will replace it with a List<RiskAssessment>
            json.remove("RiskToDTOs");

            // Restore the json file back to a property object - This property object is still missing the RiskAssessment,s we will add that next
            Property property = gson.fromJson(json, Property.class);

            // Reconstruct the List<RiskAssessment> using the placeholder information stored in List<RisktoDTO>
            List<RiskAssessment> restoredRisks = new ArrayList<>();
            for (RiskToDTO dto : dtos) {
                restoredRisks.add(new RiskResult(dto));
            }

            // We add our reconstructed List<RiskAssessment> to the field "riskAssessment" defined in the Property class. This is possible via. java reflections
            var field = Property.class.getDeclaredField("riskAssessment");
            field.setAccessible(true);
            field.set(property, restoredRisks); // Takes the object "property" that needs to be modified, and puts restoredRisks (The List<RiskAssessment>)

            return property;

        } catch (SQLException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Method for saving a property into the database. This method utilizes the helper method {@code convertToDTO()} and the custom Gson instance {@code gson} for doing this appropriately.
     * @param property contains the property object that needs to be added into the database matching the address.
     */
    public static void saveProperty(Property property) {
        // The sql string that will be executed. The question marks will be filled in later, and is a placeholder at this point
        String sql = "INSERT OR REPLACE INTO properties (Address, propertyObject) VALUES (?, ?)";

        try (Connection conn = ConnectToDB.connect("climateTool.db");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Converting the property into a json object, which allows us to modify the object that will later be passed to the database
            JsonObject json = (JsonObject) gson.toJsonTree(property);

            /* For loop that iterates over the property's risks and converts the risks into a DTO object and appends it to the arraylist dtos.
             * This essentially becomes the new RiskAssessment list but now with only data values instead of geodata readers, thresholds repository readers and such. */
            List<RiskToDTO> dtos = new ArrayList<>();
            for (var risk : property.getRisks()) {
                dtos.add(convertToDTO(risk));
            }

            // Add DTO list to JSON
            json.add("RiskToDTOs", gson.toJsonTree(dtos));

            // Remove original riskAssessment from JSON
            json.remove("riskAssessment");

            // Fill in the ? in the SQL string
            stmt.setString(1, property.getAddress());
            stmt.setString(2, json.toString());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Method for updating the database with the newly changed specialist score
     * @param address contains the address of the property
     * @param specialistScore contains the specialist score that is in the interval [-1..1]
     */
    public static void updateSpecialistScore(String address, int specialistScore) {
        String sql = "UPDATE properties " +
                "SET propertyObject = json_set(propertyObject, '$.specialistScore', ?) " +
                "WHERE Address = ?;";

        try (Connection conn = ConnectToDB.connect("climateTool.db");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, specialistScore);
            stmt.setString(2, address);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Property getProperty(String address) {
        String sql = "SELECT propertyObject FROM properties WHERE Address = ?";
        try (Connection conn = ConnectToDB.connect("climateTool.db");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, address);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String json = rs.getString("propertyObject");
                Property p = new Gson().fromJson(json, Property.class);
                return p;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Method for converting from a Risk of interface reference type {@code RiskAssessment} to a {@code RiskToDTO} object that contains the data values from the object {@code risk}.
     * @param risk object containing risk type, normalization value, measurement value, RGB array, thresholds array and computeRiskMetrics()
     * @return data only object with the data values copied over into it from the risks
     */
    private static RiskToDTO convertToDTO(RiskAssessment risk) {
        RiskToDTO DTO = new RiskToDTO();
        DTO.riskType = risk.getRiskType();
        DTO.measurementValue = risk.getMeasurementValue();
        DTO.normalizedValue = risk.getNormalizedValue();
        DTO.thresholds = risk.getThresholds();
        DTO.RGB = risk.getRGB();
        return DTO;
    }
}