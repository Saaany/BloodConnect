package com.termproject;

import com.termproject.Clients.ServerData;
import com.termproject.Controllers.*;
import com.termproject.Model.BBManager;
import com.termproject.Model.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientMain extends Application {

    private static final String connection = "jdbc:oracle:thin:@localhost:1521:orcl";
    public static BorderPane root;
    public static Stage primaryStage = null;
    public static int themeNo = 1;
    public static Pane window;
    String User = "";
    static List<BorderPane> grid = new ArrayList<>();//list of all fxmls
    private static int current_index = 0;
    public static Scene scene;


    @Override
    public void start(Stage stage) throws IOException {

        //loadFxml();
        ServerData.manualInitialize();
        primaryStage = stage;
        //showHomePage();
        showFirstLogInPage();

    }

    public void showLogInPage() throws IOException{

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("FXMLS/login.fxml"));
        Parent root = loader.load();

        // Loading the controller
        LogInController controller = loader.getController();
        controller.setMain(this);

        // Set the primary stage
        primaryStage.setTitle("BloodConnect!");
        primaryStage.setScene(new Scene(root, 840, 720));
        primaryStage.show();
    }
    public void showManagerLogInPage() throws IOException{

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("FXMLS/managerLogInPage.fxml"));
        Parent root = loader.load();

        // Loading the controller
        BBManagerLogInController controller = loader.getController();
        controller.setMain(this);

        // Set the primary stage
        primaryStage.setTitle("BloodConnect!");
        primaryStage.setScene(new Scene(root, 840, 720));
        primaryStage.show();
    }

    public void showFirstLogInPage() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("FXMLS/firstLogInPage.fxml"));
        Parent root = loader.load();

        // Loading the controller
        LogInFirstPageController controller = loader.getController();
        controller.setMain(this);

        // Set the primary stage
        primaryStage.setTitle("BloodConnect!");
        primaryStage.setScene(new Scene(root, 840, 720));
        primaryStage.show();
    }

    public void showSignUpPage() throws IOException{

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("FXMLS/registration.fxml"));
        Parent root = loader.load();

        // Loading the controller
        SignUpController controller = loader.getController();
        controller.setMain(this);

        // Set the primary stage
        primaryStage.setTitle("BloodConnect!");
        primaryStage.setScene(new Scene(root, 800, 700));
        primaryStage.show();
    }
    public void showManagerRegistrationPage() throws IOException{

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("FXMLS/managerRegistrationPage.fxml"));
        Parent root = loader.load();

        // Loading the controller
        BBRegistrationController controller = loader.getController();
        controller.setMain(this);

        // Set the primary stage
        primaryStage.setTitle("BloodConnect!");
        primaryStage.setScene(new Scene(root, 800, 700));
        primaryStage.show();
    }

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection conn = DriverManager.getConnection(connection, "PROJECT", "project");
        return conn;
    }

    public void showHomePage(User user) throws IOException{

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("FXMLS/homepage.fxml"));
        Parent root = loader.load();

        //Loading the controller
        HomeController controller = loader.getController();
        controller.setUser(user);
        controller.setMain(this);

        // Set the primary stage
        primaryStage.setTitle("BloodConnect!");
        primaryStage.setScene(new Scene(root, 845, 720));
        primaryStage.show();
    }

    public void showBloodPostWindow() throws IOException{

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("FXMLS/bloodPostWindow.fxml"));
        Parent root = loader.load();

        // Loading the controller
        HomeController controller = loader.getController();
        //controller.setMain(this);

        // Set the primary stage
        primaryStage.setTitle("BloodPost!");
        primaryStage.setScene(new Scene(root, 500, 450));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public void showBBHomePage(BBManager manager) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("FXMLS/managerHomePage.fxml"));
        Parent root = loader.load();

        //Loading the controller
        BBManagerHomeController controller = loader.getController();
        controller.setMain(this);
        controller.setManager(manager);
        controller.setAllData();

        // Set the primary stage
        primaryStage.setTitle("BloodConnect!");
        primaryStage.setScene(new Scene(root, 910, 720));
        primaryStage.show();
    }

}