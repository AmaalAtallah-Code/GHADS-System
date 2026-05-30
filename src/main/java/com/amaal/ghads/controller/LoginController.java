package com.amaal.ghads.controller;

import com.amaal.ghads.dao.UserDAO;
import com.amaal.ghads.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button resetButton;

    @FXML
    private Label errorLabel;

    private final UserDAO userDAO = new UserDAO();

    @FXML
    public void handleLogin(ActionEvent event) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("رجاءً، املأ جميع الحقول المطلوب!");
            errorLabel.setVisible(true);
            return;
        }

        User loggedInUser = userDAO.authenticate(username, password);

        if (loggedInUser != null) {
            errorLabel.setVisible(false);
            try {
                Stage stage = (Stage) loginButton.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource("/com/amaal/ghads/view/AdminDashboardView.fxml"));
                stage.setScene(new Scene(root, 1100, 700));
                stage.centerOnScreen();
            } catch (IOException e) {
                errorLabel.setText("خطأ في تحميل الواجهة الرئيسية!");
                errorLabel.setVisible(true);
                e.printStackTrace();
            }
        } else {
            errorLabel.setText("اسم المستخدم أو كلمة المرور غير صحيحة!");
            errorLabel.setVisible(true);
        }
    }

    @FXML
    void handleReset(ActionEvent event) {
        usernameField.clear();
        passwordField.clear();
        errorLabel.setVisible(false);
    }
}