package com.amaal.ghads.dao;

import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AidDistributionDAO {

    // فحص هل استلمت العائلة مساعدة من نفس المؤسسة اليوم؟ (منع التكرار)
    public boolean isAlreadyDistributedToday(int familyId, int orgId) {
        String query = "SELECT COUNT(*) FROM aid_distribution WHERE family_id = ? AND org_id = ? AND DATE(distribution_date) = CURDATE()";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, familyId);
            ps.setInt(2, orgId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // إذا كان العدد أكبر من 0 يعني استلمت اليوم
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // تسجيل عملية توزيع جديدة
    public boolean addDistribution(int familyId, int orgId, String aidType, double quantity) {
        String query = "INSERT INTO aid_distribution (family_id, org_id, aid_type, quantity, distribution_date) VALUES (?, ?, ?, ?, NOW())";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, familyId);
            ps.setInt(2, orgId);
            ps.setString(3, aidType);
            ps.setDouble(4, quantity);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}