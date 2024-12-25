package com.termproject.Controllers;

import com.termproject.Clients.ServerData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class BloodPostController implements Initializable {

    @FXML
    private TextArea patientInfoTextBox;

    @FXML
    private TextField phoneTextField;

    @FXML
    private DatePicker dateField;

    @FXML
    private TextField locationTextField;

    @FXML
    private Button postButton;

    @FXML
    private Label bloodPostMessageLabel;

    @FXML
    private ChoiceBox<String> bloodGroupChoiceBox;
    private String[] bloodArr = {"A+","B+","A-","B-","O+","O-","AB+","AB-"};

    @FXML
    private ChoiceBox<String> amountChoiceBox;
    private String[] amountArr = {"1","2","3","4","5","6","7","8","9","10"};

    @FXML
    private ChoiceBox<String> timeChoiceBox;
    private String[] timeArr = {"1","2","3","4","5","6","7","8","9","10","11","12"};

    @FXML
    private ChoiceBox<String> timeChoiceBox2;
    private String[] timeArr2={"AM","PM"};

    @FXML
    private Text bloodGroupDemo;
    @FXML
    private Text amountDemo;
    @FXML
    private Text timeDemo;
    @FXML
    private Text ampmDemo;



    private int user_id;
    private String date = "";
    private String blood = "";
    private String amount = "";
    private String time1 = "";
    private String time2 = "";
    private Stage stage;
    private HomeController home;

    public void setHome(HomeController home){
        this.home = home;
    }

    public void setUserId(int id){
        user_id = id;
    }

    public void getLastDate(){
        LocalDate lastDate = dateField.getValue();
        date = lastDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

    }

    public void postButtonOnAction(){

        String pt = "";
        if(blood.equals("")) {
            if(pt.length()>0)
                pt+=", ";
            pt += "Blood Group ";
        }
        if(amount.equals("")) {
            if(pt.length()>0)
                pt+=", ";
            pt += "Amount ";
        }
        if(locationTextField.getText().equals("")){
            if(pt.length()>0)
                pt+=", ";
            pt +="Location ";
        }
        if(date.equals("")){
            if(pt.length()>0){
                pt+=", ";
            }
            pt+= "Date ";
        }
        if(time1.equals("")){
            if(pt.length()>0){
                pt+=", ";
            }
            pt+= "Time ";
        }
        if(time2.equals("")){
            if(pt.length()>0){
                pt+=", ";
            }
            pt+= "AM/PM ";
        }

        if(phoneTextField.getText().equals("")){
            if(pt.length()>0)
                pt+=", ";
            pt += "Phone No ";
        }
        if(patientInfoTextBox.getText().equals("")){
            if(pt.length()>0)
                pt+=", ";
            pt += "Patient Info ";
        }

        int l = pt.length();
        if(l>0){
            pt += ("Required!");
            bloodPostMessageLabel.setText(pt);
            return;
        }

        ServerData.createBloodPost(user_id,blood,amount,locationTextField.getText(),date,time1+" "+time2,phoneTextField.getText(),patientInfoTextBox.getText());
        showBloodPostConfirmation();

        stage.close();
        home.homeButtonOnAction();

    }

    public void showBloodPostConfirmation(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.APPLICATION_MODAL);

        alert.setTitle("Blood Post Created");
        alert.setHeaderText("Blood Post Created Successfully!");
        alert.setContentText("");
        alert.showAndWait();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bloodGroupChoiceBox.getItems().addAll(bloodArr);
        amountChoiceBox.getItems().addAll(amountArr);
        timeChoiceBox.getItems().addAll(timeArr);
        timeChoiceBox2.getItems().addAll(timeArr2);

        bloodGroupChoiceBox.setOnAction(this::getBlood);
        amountChoiceBox.setOnAction(this::getAmount);
        timeChoiceBox.setOnAction(this::getTime1);
        timeChoiceBox2.setOnAction(this::getTime2);
    }

    private void getTime1(ActionEvent event) {
        time1 = timeChoiceBox.getValue();
        timeDemo.setText("");
    }

    private void getTime2(ActionEvent event) {
        time2 = timeChoiceBox2.getValue();
        ampmDemo.setText("");
    }

    public void getBlood(ActionEvent event){
        blood =  bloodGroupChoiceBox.getValue();
        bloodGroupDemo.setText("");
        System.out.println(blood);
    }
    public void getAmount(ActionEvent event){
        amount = amountChoiceBox.getValue();
        amountDemo.setText("");
        System.out.println(amount);
    }
}
