package id.dojo.akunku.controllers;
import id.dojo.akunku.models.Account;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AccountEditController {
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField signUrlField;

    private Stage dialogStage;
    private Account account;
    private boolean okClicked = false;

    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setAccount(Account account) {
        this.account = account;

        usernameField.setText(account.getUsername());
        passwordField.setText(account.getPassword());
        signUrlField.setText(account.getSignUrl());
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk() {
        account.setUsername(usernameField.getText());
        account.setPassword(passwordField.getText());
        account.setSignUrl(signUrlField.getText());

        okClicked = true;
        dialogStage.close();
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
}
