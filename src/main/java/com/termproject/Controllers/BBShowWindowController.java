package com.termproject.Controllers;

import com.termproject.Clients.ServerData;
import com.termproject.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class BBShowWindowController implements Initializable {

    @FXML
    private Label aPlusCount;

    @FXML
    private Label aMinusCount;

    @FXML
    private Label bPlusCount;

    @FXML
    private Label bMinusCount;

    @FXML
    private Label oPlusCount;

    @FXML
    private Label oMinusCount;

    @FXML
    private Label abPlusCount;

    @FXML
    private Label abMinusCount;

    @FXML
    private ChoiceBox<String> bloodGroupChoiceBox;
    private String[] bloodArr = {"A+","B+","A-","B-","O+","O-","AB+","AB-"};

    @FXML
    private ChoiceBox<String> amountChoiceBox;
    private String[] amountArr = {"1","2","3","4","5","6","7","8","9","10"};

    @FXML
    private TextField phoneTextField;

    @FXML
    private DatePicker dateField;

    @FXML
    private Button reqButton;

    @FXML
    private Text bloodGroupDemo;
    @FXML
    private Text amountDemo;

    @FXML
    private Text bbNameText;

    @FXML
    private Text phone;
    @FXML
    private Text email;
    @FXML
    private Text messageLabel;

    private String blood = "";
    private String amount = "";
    private String date = "";
    private int bb_id;
    private User user;
    private String[] cntList;
    private Stage stage;

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void setBloodCount(int bb_id) {

        this.bb_id = bb_id;
        setAllData();
    }

    public void getLastDate(){
        LocalDate lastDate = dateField.getValue();
        date = lastDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

    }


    public void setAllData(){

        System.out.println("now in blood count");
        cntList = ServerData.getBloodCountData(bb_id);

        System.out.println("all data retrieved");
        aPlusCount.setText(cntList[0]);
        aMinusCount.setText(cntList[1]);
        bPlusCount.setText(cntList[2]);
        bMinusCount.setText(cntList[3]);
        oPlusCount.setText(cntList[4]);
        oMinusCount.setText(cntList[5]);
        abPlusCount.setText(cntList[6]);
        abMinusCount.setText(cntList[7]);
    }

    public void setData(String bb_name, String man_phone, String man_email) {
        bbNameText.setText(bb_name);
        phone.setText(man_phone);
        email.setText(man_email);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bloodGroupChoiceBox.getItems().addAll(bloodArr);
        amountChoiceBox.getItems().addAll(amountArr);

        bloodGroupChoiceBox.setOnAction(this::getBlood);
        amountChoiceBox.setOnAction(this::getAmount);

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

    @FXML
    void reqButtonOnAction(){

        String pt = "";
        if(blood.equals("")) {
            if(pt.length()>0)
                pt+=", ";
            pt += "Blood ";
        }
        if(amount.equals("")) {
            if(pt.length()>0)
                pt+=", ";
            pt += "Amount ";
        }
        if(date.equals("")){
            if(pt.length()>0){
                pt+=", ";
            }
            pt+= "Date ";
        }
        if(phoneTextField.getText().equals("")){
            if(pt.length()>0)
                pt+=", ";
            pt +="Phone No ";
        }

        int l = pt.length();
        if(l>0){
            pt += ("Required!");
            messageLabel.setText(pt);
            return;
        }

        System.out.println("hush");
        ServerData.CreateBloodRequestToBB(this.user.getUserId(),bb_id,blood,amount,date,phoneTextField.getText());
        System.out.println("OKAY");
        showReqConfirmation();

        stage.close();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void showReqConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Request Complete");
        alert.setHeaderText("Blood Request Successfully Placed !");
        alert.setContentText("");
        alert.showAndWait();
    }
}
