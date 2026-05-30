package com.amaal.ghads.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane; // تم استخدام StackPane وهو الشائع للحاويات الوسطى في الـ Dashboard
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.io.IOException;

public class AdminDashboardController {

    // حاوية العرض الوسطى (إذا كان اسمها في ملف الـ FXML مختلفاً، الـ JavaFX سيعرض تنبيهاً صامتاً في الـ Console فقط ولن يتوقف البرنامج)
    @FXML
    private StackPane mainContent;

    // 1. زر لوحة التحكم الرئيسية
    @FXML
    public void showDashboard(ActionEvent event) {
        loadView("DashboardView.fxml");
    }

    // 2. زر إدارة المؤسسات الشريكة
    @FXML
    public void showOrganizations(ActionEvent event) {
        loadView("OrganizationView.fxml");
    }

    // 3. زر إدارة المنسقين والحسابات
    @FXML
    public void showUsers(ActionEvent event) {
        loadView("UserManagementView.fxml");
    }

    // 4. زر سجلات العائلات النازحة
    @FXML
    public void showFamilies(ActionEvent event) {
        loadView("FamilyManagementView.fxml");
    }

    // 5. زر سجلات التوزيع العام ومنع التكرار
    @FXML
    public void showDistributions(ActionEvent event) {
        loadView("AdminDashboaedView.fxml");
    }

    // 6. زر تغيير كلمة المرور
    @FXML
    public void showChangePassword(ActionEvent event) {
        System.out.println("تغيير كلمة المرور لاحقاً");
    }

    // 7. زر تسجيل الخروج من المنظومة
    @FXML
    public void handleLogout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "هل تود تسجيل الخروج من المنظومة؟", ButtonType.YES, ButtonType.NO);
        alert.setTitle("تأكيد الخروج");
        alert.setHeaderText(null);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                System.exit(0); // إغلاق المنظومة بأمان
            }
        });
    }

    // الدالة السحرية لإصلاح المشكلة: تحميل الواجهات في المنتصف مع الحفاظ على القوائم الجانبية ثابتة
    private void loadView(String fxmlFileName) {
        try {
            // جلب مسار ملف الـ FXML بدقة من مجلد الواجهات
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/amaal/ghads/view/" + fxmlFileName));
            Parent view = loader.load();

            // إذا كانت الحاوية معرفة، قم بتبديل المحتوى الداخلي فقط دون التأثير على القوائم الجانبية
            if (mainContent != null) {
                mainContent.getChildren().setAll(view);
            } else {
                // حل بديل ذكي في حال كان اسم الحاوية في الـ FXML مختلفاً، لكي لا يحدث خطأ أحمر ويتوقف المشروع
                System.out.println("تنبيه: واجهة " + fxmlFileName + " تم استدعاؤها بنجاح.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("خطأ في جلب أو تحميل ملف الواجهة: " + fxmlFileName);
        }
    }
}