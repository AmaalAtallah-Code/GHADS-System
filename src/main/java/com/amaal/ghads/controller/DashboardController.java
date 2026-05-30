package com.amaal.ghads.controller;

import com.amaal.ghads.dao.DashboardDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.util.Map;

public class DashboardController {

    @FXML
    private Label lblTotalOrgs;
    @FXML
    private Label lblTotalCoordinators;
    @FXML
    private Label lblTotalFamilies;
    @FXML
    private Label lblFamiliesServed;
    @FXML
    private Label lblFamiliesNotServed;

    private DashboardDAO dashboardDAO = new DashboardDAO();

    // ميثود initialize تعمل تلقائياً بمجرد فتح الشاشة لجلب الأرقام الحية من الداتا بيز
    @FXML
    public void initialize() {
        Map<String, Integer> statistics = dashboardDAO.getSummaryStatistics();

        lblTotalOrgs.setText(String.valueOf(statistics.getOrDefault("totalOrganizations", 0)));
        lblTotalCoordinators.setText(String.valueOf(statistics.getOrDefault("totalCoordinators", 0)));
        lblTotalFamilies.setText(String.valueOf(statistics.getOrDefault("totalFamilies", 0)));
        lblFamiliesServed.setText(String.valueOf(statistics.getOrDefault("familiesServed", 0)));
        lblFamiliesNotServed.setText(String.valueOf(statistics.getOrDefault("familiesNotServed", 0)));
    }
}