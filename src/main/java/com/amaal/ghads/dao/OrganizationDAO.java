package com.amaal.ghads.dao;

import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OrganizationDAO {

    // 1. ميثود لجلب جميع المؤسسات من قاعدة البيانات (تم تحديث اسم العمود إلى headquarters)
    public ObservableList<String[]> getAllOrganizations() {
        ObservableList<String[]> orgList = FXCollections.observableArrayList();
        String query = "SELECT id, name, headquarters FROM organization";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String[] org = new String[3];
                org[0] = String.valueOf(rs.getInt("id"));
                org[1] = rs.getString("name");
                org[2] = rs.getString("headquarters"); // مطابقة لقاعدة البيانات
                orgList.add(org);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orgList;
    }

    // 2. ميثود لإضافة مؤسسة جديدة (تم تحديث اسم العمود إلى headquarters)
    public boolean addOrganization(String name, String headquarters) {
        String query = "INSERT INTO organization (name, headquarters) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, name);
            ps.setString(2, headquarters);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}