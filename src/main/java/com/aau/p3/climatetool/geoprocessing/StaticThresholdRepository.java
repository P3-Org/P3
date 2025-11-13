package com.aau.p3.climatetool.geoprocessing;

import com.aau.p3.climatetool.utilities.ThresholdRepository;
import com.aau.p3.database.ConnectToDB;

import java.sql.*;

public class StaticThresholdRepository implements ThresholdRepository {
    @Override
    public double[] getThreshold(String riskType) {
        // Switch that finds which risk that was sent in the parameters

        String sql = "SELECT minThreshold, maxThreshold FROM thresholds WHERE riskType = ?";

        try (Connection conn = ConnectToDB.connect("climateTool.db");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, riskType);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                double min = rs.getDouble("minThreshold");
                double max = rs.getDouble("maxThreshold");
                System.out.println("min:" + min + "max: " + max);
                return new double[]{min, max};
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}