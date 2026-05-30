package com.amaal.ghads;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // توجيه المنظومة لتفتح لوحة التحكم الرئيسية (Dashboard) عند التشغيل فوراً
        Parent root = FXMLLoader.load(getClass().getResource("/com/amaal/ghads/view/AdminDashboardView.fxml"));
        primaryStage.setTitle("منظومة تنسيق المساعدات الإنسانية - GHADS");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        // تشغيل الكلاس الحالي المحدث
        launch(args);
    }
}