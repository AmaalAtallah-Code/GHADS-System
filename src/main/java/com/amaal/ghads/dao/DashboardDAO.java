package com.amaal.ghads.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DashboardDAO {
    private Connection connection;

    public DashboardDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public Map<String, Integer> getSummaryStatistics() {
        Map<String, Integer> stats = new HashMap<>();

        // استعلامات جلب الأعداد المطلوبة في مستند المشروع بدقة
        String qOrg = "SELECT COUNT(*) FROM Organization";
        String qUsers = "SELECT COUNT(*) FROM User WHERE role = 'COORDINATOR'";
        String qFamilies = "SELECT COUNT(*) FROM Family";
        String qServed = "SELECT COUNT(DISTINCT family_id) FROM AidDistribution";

        try {
            // 1. حساب المؤسسات
            try (PreparedStatement ps = connection.prepareStatement(qOrg); ResultSet rs = ps.executeQuery()) {
                if (rs.next()) stats.put("totalOrganizations", rs.getInt(1));
            }
            // 2. حساب المنسقين
            try (PreparedStatement ps = connection.prepareStatement(qUsers); ResultSet rs = ps.executeQuery()) {
                if (rs.next()) stats.put("totalCoordinators", rs.getInt(1));
            }
            // 3. حساب العائلات الإجمالي
            try (PreparedStatement ps = connection.prepareStatement(qFamilies); ResultSet rs = ps.executeQuery()) {
                if (rs.next()) stats.put("totalFamilies", rs.getInt(1));
            }
            // 4. حساب العائلات التي استلمت مساعدات فعلياً
            try (PreparedStatement ps = connection.prepareStatement(qServed); ResultSet rs = ps.executeQuery()) {
                if (rs.next()) stats.put("familiesServed", rs.getInt(1));
            }

            // 5. حساب العائلات غير المخدومة (الإجمالي - المخدومة)
            int total = stats.getOrDefault("totalFamilies", 0);
            int served = stats.getOrDefault("familiesServed", 0);
            stats.put("familiesNotServed", total - served);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stats;
    }
}