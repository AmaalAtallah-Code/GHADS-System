package com.amaal.ghads.controller;

import com.amaal.ghads.dao.AidDistributionDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class AidDistributionController {

    @FXML private TextField txtFamilyId;
    @FXML private TextField txtOrgId;
    @FXML private TextField txtAidType;
    @FXML private TextField txtQuantity;

    private final AidDistributionDAO distDAO = new AidDistributionDAO();

    @FXML
    public void handleDistribution() {
        String famStr = txtFamilyId.getText().trim();
        String orgStr = txtOrgId.getText().trim();
        String aidType = txtAidType.getText().trim();
        String qtyStr = txtQuantity.getText().trim();

        if (famStr.isEmpty() || orgStr.isEmpty() || aidType.isEmpty() || qtyStr.isEmpty()) {
            showAlert("خطأ", "الرجاء ملء جميع الحقول لتسجيل العملية!", Alert.AlertType.ERROR);
            return;
        }

        try {
            int familyId = Integer.parseInt(famStr);
            int orgId = Integer.parseInt(orgStr);
            double qty = Double.parseDouble(qtyStr);

            // خطوة الحماية الذكية ضد التكرار المطلوب في المشروع
            if (distDAO.isAlreadyDistributedToday(familyId, orgId)) {
                showAlert("تحذير - تكرار استلام!", "⚠️ خطأ أمني: هذه العائلة استلمت بالفعل مساعدة من نفس هذه المؤسسة اليوم! يمنع التكرار.", Alert.AlertType.WARNING);
                return;
            }

            // إذا لم تستلم اليوم، قم بالحفظ فوراً
            boolean success = distDAO.addDistribution(familyId, orgId, aidType, qty);
            if (success) {
                showAlert("تم بنجاح", "تم تسجيل حركة التوزيع بنجاح ومنع التكرار وآمن تماماً!", Alert.AlertType.INFORMATION);
                txtFamilyId.clear();
                txtOrgId.clear();
                txtAidType.clear();
                txtQuantity.clear();
            }
        } catch (NumberFormatException e) {
            showAlert("خطأ", "الأرقام والكميات يجب أن تُكتب بشكل صحيح!", Alert.AlertType.ERROR);
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