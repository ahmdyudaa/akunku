package id.dojo.akunku.controllers;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import id.dojo.akunku.MainApp;
import id.dojo.akunku.models.Account;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.util.Callback;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class AccountController {
    @FXML
    private TableView<Account> accountTable;
    @FXML
    private TableColumn<Account, String> usernameColumn;
    @FXML
    private TableColumn<Account, String> passwordColumn;
    @FXML
    private TableColumn<Account, String> signUrlColumn;
    @FXML
    private TableColumn<Account, Void> actionColumn;
    @FXML
    private TextField searchField;
    @FXML
    private Label totalAccountLabel;


    private ObservableList<Account> accountData = FXCollections.observableArrayList();
    private FilteredList<Account> filteredData;

    private MainApp mainApp;

    @FXML
    private void initialize() {
        usernameColumn.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        passwordColumn.setCellValueFactory(cellData -> cellData.getValue().passwordProperty());
        signUrlColumn.setCellValueFactory(cellData -> cellData.getValue().signUrlProperty());

        filteredData = new FilteredList<>(accountData, p -> true);
        accountTable.setItems(filteredData);
        addButtonToTable();

        searchField.textProperty().addListener((observable, oldValue, newValue) -> handleSearchKeyReleased());
        updateTotalAccountLabel();
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        accountData.setAll(mainApp.getAccountData());
        accountTable.setItems(filteredData);

        updateTotalAccountLabel();
    }

    private void addButtonToTable() {
        Callback<TableColumn<Account, Void>, TableCell<Account, Void>> cellFactory = new Callback<TableColumn<Account, Void>, TableCell<Account, Void>>() {
            @Override
            public TableCell<Account, Void> call(final TableColumn<Account, Void> param) {
                return new TableCell<Account, Void>() {

                    private final FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL);
                    private final FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);

                    {
                        // Set icon sizes
                        editIcon.setGlyphSize(20);
                        deleteIcon.setGlyphSize(20);

                        // Set icon colors
                        editIcon.setStyle("-fx-fill: green;");
                        deleteIcon.setStyle("-fx-fill: red;");

                        // Set mouse click event handlers
                        editIcon.setOnMouseClicked((MouseEvent event) -> {
                            Account account = getTableView().getItems().get(getIndex());
                            handleEditAccount(account);
                        });
                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                            Account account = getTableView().getItems().get(getIndex());
                            handleDeleteAccount(account);
                        });
                    }


                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox managebtn = new HBox(editIcon, deleteIcon);
                            managebtn.setSpacing(10);
                            setGraphic(managebtn);
                        }
                    }
                };
            }
        };

        actionColumn.setCellFactory(cellFactory);
    }
    @FXML
    private void handleNewAccount() {
        Account tempAccount = new Account();
        boolean okClicked = mainApp.showAccountEditDialog(tempAccount);
        if (okClicked) {
            accountData.add(tempAccount);
            updateFilteredData();
        }
        updateTotalAccountLabel();
    }

    @FXML
    private void handleEditAccount(Account selectedAccount) {
        if (selectedAccount != null) {
            boolean okClicked = mainApp.showAccountEditDialog(selectedAccount);
            if (okClicked) {
                accountTable.refresh();
            }
        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Account Selected");
            alert.setContentText("Please select an account in the table.");
            alert.showAndWait();
        }
    }

    private void handleDeleteAccount(Account account) {
        accountData.remove(account);
        updateFilteredData();
        updateTotalAccountLabel();
    }

    @FXML
    private void handleSearchKeyReleased() {
        filteredData.setPredicate(account -> {
            String lowerCaseFilter = searchField.getText().toLowerCase();
            if (lowerCaseFilter.isEmpty()) {
                return true;
            }
            if (account.getUsername().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            }
            if (account.getPassword().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            }
            if (account.getSignUrl().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            }
            return false;
        });
    }

    @FXML
    private void handleExportData() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Data");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                for (Account account : accountData) {
                    writer.printf("Username: %s\nPassword: %s\nSign URL: %s\n\n",
                            account.getUsername(),
                            account.getPassword(),
                            account.getSignUrl());
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initOwner(mainApp.getPrimaryStage());
                alert.setTitle("Export Successful");
                alert.setHeaderText("Data Exported Successfully");
                alert.setContentText("The data has been exported to " + file.getAbsolutePath());
                alert.showAndWait();
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(mainApp.getPrimaryStage());
                alert.setTitle("Error");
                alert.setHeaderText("Could Not Export Data");
                alert.setContentText("An error occurred while exporting the data.");
                alert.showAndWait();
            }
        }
    }


    private void updateFilteredData() {
        filteredData.setPredicate(account -> {
            String lowerCaseFilter = searchField.getText().toLowerCase();
            if (lowerCaseFilter.isEmpty()) {
                return true;
            }
            if (account.getUsername().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            }
            if (account.getPassword().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            }
            if (account.getSignUrl().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            }
            return false;
        });
        accountTable.refresh();
    }

    private void updateTotalAccountLabel() {
        totalAccountLabel.setText( String.valueOf(filteredData.size()));
    }
}