package com.amaal.ghads.controller;

import com.amaal.ghads.dao.FamilyDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class FamilyManagementController {

    @FXML private TextField txtHouseholdName;
    @FXML private TextField txtNationalId;
    @FXML private TextField txtPhone;
    @FXML private TextField txtLocation;
    @FXML private TextField txtFamilySize;
    @FXML private TextField txtVulnerability; // تم تحويله لـ TextField ليطابق التصميم الحالي بسهولة

    @FXML private TableView<String[]> tblFamilies;
    @FXML private TableColumn<String[], String> colFamId;
    @FXML private TableColumn<String[], String> colFamName;
    @FXML private TableColumn<String[], String> colFamNational;
    @FXML private TableColumn<String[], String> colFamLocation;
    @FXML private TableColumn<String[], String> colFamSize;

    private final FamilyDAO familyDAO = new FamilyDAO();

    @FXML
    public void initialize() {
        // ربط أعمدة الجدول البرمجية بالمصفوفة القادمة من الـ DAO
        colFamId.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[0]));
        colFamName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[1]));
        colFamNational.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[2]));
        colFamLocation.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[4]));
        colFamSize.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[5]));

        // تحميل البيانات وعرضها فور فتح الشاشة
        loadTableData();
    }

    private void loadTableData() {
        ObservableList<String[]> data = familyDAO.getAllFamilies();
        tblFamilies.setItems(data);
    }

    // دالة يتم استدعاؤها عند الضغط على زر إضافة العائلة
    @FXML
    public void handleSaveFamily() {
        String name = txtHouseholdName.getText().trim();
        String nationalId = txtNationalId.getText().trim();
        String phone = txtPhone.getText().trim();
        String location = txtLocation.getText().trim();
        String sizeStr = txtFamilySize.getText().trim();
        String vulnerability = (txtVulnerability != null) ? txtVulnerability.getText().trim() : "متوسط";

        // التحقق من تعبئة الحقول الأساسية
        if (name.isEmpty() || nationalId.isEmpty() || location.isEmpty() || sizeStr.isEmpty()) {
            showAlert("خطأ في الإدخال", "الرجاء تعبئة الحقول الأساسية (الاسم، الهوية، الموقع، وعدد الأفراد)!", Alert.AlertType.ERROR);
            return;
        }

        // تحويل عدد الأفراد لرقم صلب لقاعدة البيانات
        int familySize;
        try {
            familySize = Integer.parseInt(sizeStr);
        } catch (NumberFormatException e) {
            showAlert("خطأ في البيانات", "الرجاء كتابة رقم صحيح في حقل عدد الأفراد!", Alert.AlertType.ERROR);
            return;
        }

        // استدعاء الـ DAO لحفظ البيانات في قاعدة البيانات
        boolean success = familyDAO.addFamily(name, nationalId, phone, location, familySize, vulnerability);
        if (success) {
            showAlert("تم بنجاح", "تم تسجيل بيانات العائلة بنجاح في المنظومة!", Alert.AlertType.INFORMATION);
            // تفريغ الحقول بعد الحفظ
            txtHouseholdName.clear();
            txtNationalId.clear();
            txtPhone.clear();
            txtLocation.clear();
            txtFamilySize.clear();
            if(txtVulnerability != null) txtVulnerability.clear();

            loadTableData(); // تحديث الجدول فوراً لعرض البيانات الجديدة
        } else {
            showAlert("فشل الحفظ", "فشل في حفظ البيانات، تأكدي من عدم تكرار رقم الهوية.", Alert.AlertType.ERROR);
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