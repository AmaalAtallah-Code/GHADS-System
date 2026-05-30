package com.amaal.ghads;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // فتح شاشة تسجيل الدخول أولاً كخطوة أمان أساسية
        Parent root = FXMLLoader.load(getClass().getResource("/com/amaal/ghads/view/LoginView.fxml"));
        primaryStage.setTitle("منظومة تنسيق المساعدات الإنسانية - تسجيل الدخول");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}