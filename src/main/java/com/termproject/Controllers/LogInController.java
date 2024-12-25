package com.termproject.Controllers;

import com.termproject.ClientMain;
import com.termproject.Clients.ServerData;
import com.termproject.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class LogInController {

    private ClientMain main;
    private User mainUser;


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
            User user = ServerData.checkUserAccount(userNameTextField.getText(),passwordTextField.getText());
            if(!user.getUserName().equals("none")){
                System.out.println("login successful");
                mainUser = user;
                main.showHomePage(mainUser);
            }
            //validateLogin();
        }
    }

    public void cancelButtonOnAction(ActionEvent e){

        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void signupButtonOnAction(ActionEvent e) throws IOException {
       /* ClientMain.removePane(0);
        ClientMain.addPane(1);*/
        main.showSignUpPage();
    }

    @FXML
    void bbBackButtonOnAction() throws IOException {
        main.showFirstLogInPage();
    }
}