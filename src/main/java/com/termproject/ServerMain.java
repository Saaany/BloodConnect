package com.termproject;

import Utils.Information;
import com.termproject.Server.DatabaseData;
import com.termproject.Server.ServerThread;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

public class ServerMain extends Application {
    private static final String connection = "jdbc:oracle:thin:@localhost:1521:orcl";
    private static final String databaseUser = "PROJECT";
    private static final String databasePassword = "project";


    public static int activeUsersCount = 0;
    public static boolean isRefreshed = false;

    @Override
    public void start(Stage primaryStage) throws Exception{
        new StartServer();
    }

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection conn = DriverManager.getConnection(connection, databaseUser, databasePassword);
        return conn;
    }

    public static void main(String[] args) {

        launch(args);
    }
}

class StartServer implements Runnable{
    Thread t;
    StartServer() {
        System.out.println("start");
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        System.out.println("start");
        ServerSocket welcomeSocket = null;
        try {
            welcomeSocket = new ServerSocket(33333);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Socket userSocket = null;
        while (true) {
            try {
                System.out.println("now looping");
                //-------------------refreshing the database------------------------
                if((ServerMain.activeUsersCount == 0 || ServerMain.activeUsersCount==1) && !ServerMain.isRefreshed){
                    System.out.println("Active user from here: "+ ServerMain.activeUsersCount);
                    DatabaseData.refreshDatabase();
                    ServerMain.isRefreshed = true;
                }else{
                    System.out.println("Active user again: "+ServerMain.activeUsersCount);
                    ServerMain.isRefreshed = false;
                }
                //-------------------------------------------------------------------

                userSocket = welcomeSocket.accept();
                System.out.println("user accepted");

            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            ServerThread wt = new ServerThread(userSocket);
            ServerMain.activeUsersCount++;
            //ServerUI.btnActiveUsers.fire();
            //ServerUI.btnActiveBar.fire();
        }
    }
}