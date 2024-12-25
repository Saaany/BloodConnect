package com.termproject.Controllers;

import com.termproject.ClientMain;
import com.termproject.Clients.ServerData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BBRegistrationController{

    @FXML
    private TextField bloodBankNameTextField;

    @FXML
    private TextField cityTextField;

    @FXML
    private TextField thanaTextField;

    @FXML
    private TextField upazillaTextField;

    @FXML
    private TextField districtTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField mailTextField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField rePasswordField;

    @FXML
    private Label signupMessageLabel;

    private ClientMain main;

    public void setMain(ClientMain main){
        this.main = main;
    }


    @FXML
    public void signupButtonOnAction(){

        String pt = "";

        if(bloodBankNameTextField.getText().equals("")) {
            if(pt.length()>0)
                pt+=", ";
            pt += "Blood Bank Name ";
        }
        if(cityTextField.getText().equals("")){
            if(pt.length()>0)
                pt+=", ";
            pt +="City ";
        }
        if(thanaTextField.getText().equals("")) {
            if(pt.length()>0)
                pt+=", ";
            pt += "Thana ";
        }
        if(upazillaTextField.getText().equals("")) {
            if(pt.length()>0)
                pt+=", ";
            pt += "Upazilla ";
        }
        if(districtTextField.getText().equals("")) {
            if(pt.length()>0)
                pt+=", ";
            pt += "District ";
        }
        if(nameTextField.getText().equals("")) {
            if(pt.length()>0)
                pt+=", ";
            pt += "Name ";
        }
        if(phoneTextField.getText().equals("")){
            if(pt.length()>0)
                pt+=", ";
            pt +="Phone No ";
        }
        if(mailTextField.getText().equals("")) {
            if(pt.length()>0)
                pt+=", ";
            pt += "Email ";
        }
        if(passwordField.getText().equals("")) {
            if(pt.length()>0)
                pt+=", ";
            pt += "Password ";
        }

        int l = pt.length();
        if(l>0){
            pt += ("Required!");
            signupMessageLabel.setText(pt);
            return;
        }

        if(!rePasswordField.getText().equals(passwordField.getText())){
            signupMessageLabel.setText("Password doesn't match! Try Again");
            return;
        }

        if(!ServerData.checkManagerMail(mailTextField.getText())){
            signupMessageLabel.setText("Email already Exist");
            return;
        }

        //System.out.println("hush");
        ServerData.createManagers(nameTextField.getText(),phoneTextField.getText(), mailTextField.getText(),passwordField.getText(),bloodBankNameTextField.getText(),cityTextField.getText(),thanaTextField.getText(),upazillaTextField.getText(),districtTextField.getText());

        showSignUpConfirmation();
    }

    public void loginButtonOnAction() throws IOException {

        main.showManagerLogInPage();
    }

    public void showSignUpConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Registration Complete");
        alert.setHeaderText("Successful Registration!");
        alert.setContentText("You are successfully registered!");
        alert.showAndWait();
    }

}
