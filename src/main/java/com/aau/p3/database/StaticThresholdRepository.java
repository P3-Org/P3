package com.aau.p3.database;

import com.aau.p3.climatetool.utilities.ThresholdRepository;

import java.sql.*;

public class StaticThresholdRepository implements ThresholdRepository {
    /**
     * Method for retrieving thresholds from database climateTool.db under the table: thresholds.
     * @param riskType is the parameter containing corresponding to the risk types like: "cloudburst", "stormsurge", etc.
     * @return double array containing the minThreshold and maxThreshold values. If error happens it will return null.
     */
    @Override
    public double[] getThreshold(String riskType) {
        // The string that will be sent to the database. Used to select the values corresponding to the columnds minThreshold and maxThreshold
        String sql = "SELECT minThreshold, maxThreshold FROM thresholds WHERE riskType = ?";

        /* Try-catch block that handles the connection to the database, and prepares a statement to be executed to the database.
        *  PreparedStatement also accepts parameters to be filled. In this case only one parameter needs to be filled in based on the risk type provided. */
        try (Connection conn = ConnectToDB.connect("climateTool.db");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // sets the string riskType at the first location it encounters a "?" and execute it (sent it to the database)
            stmt.setString(1, riskType);
            ResultSet rs = stmt.executeQuery();

            /* Move the cursor to the first row (which is always the correct row because we have filtered it using riskType).
            *  When the stmt.executeQuery() is ran the cursor is ALWAYS placed right before the first row. That is why we need to go to the next row.
            *  Gather the data values and return them */
            if (rs.next()) {
                double min = rs.getDouble("minThreshold");
                double max = rs.getDouble("maxThreshold");
                return new double[]{min, max};
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Method for updating the thresholds within the database when the users change the thresholds within the climate tool
     * @param riskType specifies which risk will be changed in the database
     * @param lower is the minimum threshold
     * @param upper is the maximum threshold (i.e. when the RGB values goes from
     */
    @Override
    public void updateThreshold(String riskType, double lower, double upper) {
        String sql = "UPDATE thresholds SET minThreshold=?, maxThreshold=? WHERE riskType=?";
        try (Connection conn = ConnectToDB.connect("climateTool.db");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, lower);
            stmt.setDouble(2, upper);
            stmt.setString(3, riskType);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}