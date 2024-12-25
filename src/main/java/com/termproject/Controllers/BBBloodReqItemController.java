package com.termproject.Controllers;

import com.termproject.Clients.ServerData;
import com.termproject.Model.BBManager;
import com.termproject.Model.BBRequestModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class BBBloodReqItemController {

    @FXML
    private Label userLabel;

    @FXML
    private Label bloodGroupLabel;

    @FXML
    private Label quantityLabel;

    @FXML
    private Label timeLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Button approveButton;
    @FXML
    private Button rejectButton;

    @FXML
    private Text statusText;

    private BBManager manager;
    private BBRequestModel bbreq;
    private BBManagerHomeController home;
    void setHome(BBManagerHomeController home){
        this.home = home;
    }
    void setManager(BBManager manager){
        this.manager = manager;
    }

    void setData(BBRequestModel bbrm){

        this.bbreq = bbrm;
        userLabel.setText(bbrm.getUser_name());
        bloodGroupLabel.setText(bbrm.getBlood_group());
        quantityLabel.setText(bbrm.getAmount());
        timeLabel.setText(bbrm.getDate());
        phoneLabel.setText(bbrm.getPhone());
        statusText.setText(bbrm.getStatus());

        if(bbrm.getStatus().equalsIgnoreCase("pending") || statusText.getText().contains("stock")){
            approveButton.setVisible(true);
            rejectButton.setVisible(true);
        }else{
            approveButton.setVisible(false);
            rejectButton.setVisible(false);
        }
    }

    @FXML
    void approveButtonOnAction(){

        showWarning();
        boolean value = ServerData.updateBloodCountAfterApproval(bbreq.getBb_id(),bbreq.getReq_id(),bbreq.getBlood_group(),bbreq.getAmount(),"Approved");

        if(value){
            statusText.setText("Approved");
            approveButton.setVisible(false);
            rejectButton.setVisible(false);
            home.setBloodCount();
        }
        else{
            statusText.setText("Not enough blood in the stock");
        }
    }
    @FXML
    void rejectButtonOnAction(){

        showConfirmation();
        boolean value = ServerData.updateBloodCountAfterApproval(bbreq.getBb_id(),bbreq.getReq_id(),bbreq.getBlood_group(),bbreq.getAmount(),"Rejected");

        if(value){
            statusText.setText("Rejected");
            approveButton.setVisible(false);
            rejectButton.setVisible(false);
        }
    }

    public void showConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Reject Request");
        alert.setHeaderText("Are you sure to reject this request !");
        alert.setContentText("Get all the information before rejecting !");

        alert.showAndWait();
    }

    public void showWarning() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Approve Request");
        alert.setHeaderText("Are you sure to approve this request !");
        alert.setContentText("Get all the information before approving !");
        alert.showAndWait();
    }
}
