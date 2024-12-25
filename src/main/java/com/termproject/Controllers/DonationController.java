package com.termproject.Controllers;

import com.termproject.Clients.ServerData;
import com.termproject.Model.BloodPost;
import com.termproject.Model.DonationPost;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DonationController {

    @FXML
    private TextField donationLocation;
    @FXML
    private DatePicker donationDatePicker;

    @FXML
    private Button addButton;
    @FXML
    private Label donationMessageLabel;

    private String date = "";
    private int user_id;
    private Stage stage;

    @FXML
    public void getDate(){
        LocalDate lastDate = donationDatePicker.getValue();
        date = lastDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @FXML
    public void addButtonOnAction(){

        String pt = "";
        if(donationLocation.getText().equals("")) {
            if(pt.length()>0)
                pt+=", ";
            pt += "Location ";
        }
        if(date.equals("")){
            if(pt.length()>0){
                pt+=", ";
            }
            pt+= "Date ";
        }

        int l = pt.length();
        if(l>0){
            pt += ("Required!");
            donationMessageLabel.setText(pt);
            return;
        }

        ServerData.createDonationPost(user_id,donationLocation.getText(),date);
        showDonationConfirmation();
        stage.close();

    }

    private void showDonationConfirmation(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Donation Post Created");
        alert.setHeaderText("Donation Post Created Successfully!");
        alert.setContentText("");
        alert.showAndWait();
    }

    public void setUserId(int userId) {
        user_id = userId;
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
