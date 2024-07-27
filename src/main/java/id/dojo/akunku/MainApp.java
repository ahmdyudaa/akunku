package id.dojo.akunku;

import id.dojo.akunku.controllers.AccountController;
import id.dojo.akunku.controllers.AccountEditController;
import id.dojo.akunku.controllers.LoginController;
import id.dojo.akunku.models.Account;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private ObservableList<Account> accountData = FXCollections.observableArrayList();

    public MainApp(){
        accountData.add(new Account("user1", "password1", "http://example.com/sign1"));
        accountData.add(new Account("user2", "password2", "http://example.com/sign2"));
        accountData.add(new Account("user3", "password3", "http://example.com/sign3"));
        accountData.add(new Account("user1", "password1", "http://example.com/sign1"));
        accountData.add(new Account("user2", "password2", "http://example.com/sign2"));
        accountData.add(new Account("user3", "password3", "http://example.com/sign3"));
        accountData.add(new Account("user1", "password1", "http://example.com/sign1"));
        accountData.add(new Account("user2", "password2", "http://example.com/sign2"));
        accountData.add(new Account("user3", "password3", "http://example.com/sign3"));
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Account Manager");

        showLoginView();
//        showPersonOverview();
    }

    private void showLoginView() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("views/LoginView.fxml"));
            AnchorPane loginPage = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Login");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(loginPage);
            dialogStage.setScene(scene);

            LoginController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMainApp(this);

            dialogStage.showAndWait();

            if (controller.isLoginSuccessful()) {
                initRootLayout();
                accountDisplay();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("views/RootLayout.fxml"));
            rootLayout = loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void accountDisplay() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("views/AccountManagement.fxml"));
//            loader.setLocation(;
            AnchorPane accountDisplay = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(accountDisplay);
            AccountController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showAccountEditDialog(Account account) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("views/EditAccount.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Account");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            AccountEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setAccount(account);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ObservableList<Account> getAccountData() {
        return accountData;
    }


//    @Override
//    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("views/AccountManagement.fxml"));
//        Scene scene = new Scene(fxmlLoader.load());
//        stage.setTitle("Account Manager");
//        stage.setScene(scene);
//        stage.show();
//    }
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch();
    }
}