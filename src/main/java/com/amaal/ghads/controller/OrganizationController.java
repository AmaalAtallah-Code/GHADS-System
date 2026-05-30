package com.amaal.ghads.controller;

import com.amaal.ghads.dao.OrganizationDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class OrganizationController {

    @FXML private TextField txtOrgName;
    @FXML private TextField txtOrgHQ;
    @FXML private TableView<String[]> tblOrganizations;
    @FXML private TableColumn<String[], String> colOrgId;
    @FXML private TableColumn<String[], String> colOrgName;
    @FXML private TableColumn<String[], String> colOrgHQ;

    private final OrganizationDAO orgDAO = new OrganizationDAO();

    @FXML
    public void initialize() {
        // ربط أعمدة الجدول بالبيانات
        colOrgId.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[0]));
        colOrgName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[1]));
        colOrgHQ.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[2]));

        // جلب وعرض البيانات عند فتح الشاشة
        loadTableData();
    }

    private void loadTableData() {
        ObservableList<String[]> data = orgDAO.getAllOrganizations();
        tblOrganizations.setItems(data);
    }

    // هذه الدالة سيتم استدعاؤها عند الضغط على زر الحفظ في الـ FXML
    @FXML
    public void handleSave() {
        String name = txtOrgName.getText().trim();
        String location = txtOrgHQ.getText().trim();

        if (name.isEmpty() || location.isEmpty()) {
            showAlert("خطأ", "الرجاء تعبئة جميع الحقول!", Alert.AlertType.ERROR);
            return;
        }

        boolean success = orgDAO.addOrganization(name, location);
        if (success) {
            showAlert("نجاح", "تم إضافة المؤسسة بنجاح!", Alert.AlertType.INFORMATION);
            txtOrgName.clear();
            txtOrgHQ.clear();
            loadTableData(); // تحديث الجدول فوراً لترى النتيجة
        } else {
            showAlert("خطأ", "فشل إضافة المؤسسة، تأكدي من قاعدة البيانات.", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}