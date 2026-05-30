package com.amaal.ghads.dao;

import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FamilyDAO {

    // 1. جلب جميع العائلات النازحة من الداتا بيز
    public ObservableList<String[]> getAllFamilies() {
        ObservableList<String[]> familyList = FXCollections.observableArrayList();
        String query = "SELECT id, household_name, national_id, phone_number, current_location, family_size, vulnerability_level FROM family";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String[] family = new String[7];
                family[0] = String.valueOf(rs.getInt("id"));
                family[1] = rs.getString("household_name");
                family[2] = rs.getString("national_id");
                family[3] = rs.getString("phone_number");
                family[4] = rs.getString("current_location");
                family[5] = String.valueOf(rs.getInt("family_size"));
                family[6] = rs.getString("vulnerability_level");
                familyList.add(family);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return familyList;
    }

    // 2. إضافة عائلة نازحة جديدة إلى قاعدة البيانات
    public boolean addFamily(String name, String nationalId, String phone, String location, int size, String vulnerability) {
        String query = "INSERT INTO family (household_name, national_id, phone_number, current_location, family_size, vulnerability_level) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, name);
            ps.setString(2, nationalId);
            ps.setString(3, phone);
            ps.setString(4, location);
            ps.setInt(5, size);
            ps.setString(6, vulnerability);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}