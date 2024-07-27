package id.dojo.akunku.controllers;

import id.dojo.akunku.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private Stage dialogStage;
    private boolean loginSuccessful = false;
    private MainApp mainApp;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void handleLogin() {
        if (isValidCredentials()) {
            loginSuccessful = true;
            dialogStage.close();
            mainApp.getPrimaryStage();
        } else {

            System.out.println("Invalid credentials");
        }
    }

    private boolean isValidCredentials() {

        return usernameField.getText().equals("user") && passwordField.getText().equals("pass");
    }

    public boolean isLoginSuccessful() {
        return loginSuccessful;
    }
}
