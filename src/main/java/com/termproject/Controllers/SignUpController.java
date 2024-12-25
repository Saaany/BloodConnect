package com.termproject.Controllers;

import com.termproject.ClientMain;
import com.termproject.Clients.ServerData;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField mailTextField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private Button regsignupButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField rePasswordField;

    private String gender = "";
    private String blood = "";

    @FXML
    private ChoiceBox<String> bloodGroupChoiceBox;
    private String[] bloodArr = {"A+","B+","A-","B-","O+","O-","AB+","AB-"};

    @FXML
    private Button loginRegButton;

    @FXML
    private ChoiceBox<String> genderChoiceBox;
    private String[] genderArr = {"Male","Female","Others"};

    @FXML
    private Label signupMessageLabel;

    private ClientMain main;


    public void setMain(ClientMain main){
        this.main = main;
    }

    @FXML
    public void signupButtonOnAction(){

        String pt = "";
        if(firstNameTextField.getText().equals("")) {
            if(pt.length()>0)
                pt+=", ";
            pt += "First name ";
        }
        if(lastNameTextField.getText().equals("")) {
            if(pt.length()>0)
                pt+=", ";
            pt += "Last name ";
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
        if(gender.equals("")){
            if(pt.length()>0)
                pt+=", ";
            pt += "Gender ";
        }
        if(blood.equals("")){
            if(pt.length()>0)
                pt+=", ";
            pt+= "Blood Group ";
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

        if(!ServerData.checkUsername(mailTextField.getText())){
            signupMessageLabel.setText("Email already Exist");
            return;
        }

        //System.out.println("hush");
        ServerData.createUsers(firstNameTextField.getText(), lastNameTextField.getText(),phoneTextField.getText(), mailTextField.getText(),gender,blood, passwordField.getText());

        showSignUpConfirmation();
    }

    public void loginButtonOnAction() throws IOException {

        main.showLogInPage();
    }

    public void showSignUpConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Registration Complete");
        alert.setHeaderText("Successful Sign Up!");
        alert.setContentText("You are successfully registered!");
        alert.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        genderChoiceBox.getItems().addAll(genderArr);
        bloodGroupChoiceBox.getItems().addAll(bloodArr);

        genderChoiceBox.setOnAction(this::getGender);
        bloodGroupChoiceBox.setOnAction(this::getBlood);

    }

    public void getBlood(ActionEvent event){
        blood = bloodGroupChoiceBox.getValue();
        System.out.println(blood);
    }
    public void getGender(ActionEvent event){

        gender = genderChoiceBox.getValue();
        System.out.println(gender);
    }
}
