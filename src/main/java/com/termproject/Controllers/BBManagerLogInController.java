package com.termproject.Controllers;

import com.termproject.ClientMain;
import com.termproject.Clients.ServerData;
import com.termproject.Model.BBManager;
import com.termproject.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class BBManagerLogInController {

    private ClientMain main;
    private BBManager manager;


    @FXML
    private Button cancelButton;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private Button loginButton;
    @FXML
    private TextField userNameTextField;
    @FXML
    private PasswordField passwordTextField;

    public void setMain(ClientMain main){
        this.main = main;
    }

    public void loginButtonOnAction(ActionEvent e) throws IOException {

        if(userNameTextField.getText().isBlank()){
            loginMessageLabel.setText("Please enter email!");
        }else if(passwordTextField.getText().isBlank()){
            loginMessageLabel.setText("Please enter password!");
        }
        else{
            loginMessageLabel.setText("You try to login!");
            BBManager manager = ServerData.checkManagerAccount(userNameTextField.getText(),passwordTextField.getText());
            System.out.println(manager);
            if(!manager.getManagerName().equals("none")){
                System.out.println("login successful");
                this.manager = manager;
                main.showBBHomePage(this.manager);
            }
            //validateLogin();
        }
    }

    public void cancelButtonOnAction(ActionEvent e){

        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void signupButtonOnAction(ActionEvent e) throws IOException {

        main.showManagerRegistrationPage();
    }

    @FXML
    void bbBackButtonOnAction() throws IOException {
        main.showFirstLogInPage();
    }
}
