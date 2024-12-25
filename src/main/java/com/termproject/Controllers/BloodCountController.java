package com.termproject.Controllers;

import com.termproject.Clients.ServerData;
import com.termproject.Model.BBManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class BloodCountController implements Initializable {

    @FXML
    private TextField aplusTextField;

    @FXML
    private TextField bplusTextField;

    @FXML
    private TextField oplusTextField;

    @FXML
    private TextField abplusTextField;

    @FXML
    private TextField aminusTextField;

    @FXML
    private TextField bminusTextField;

    @FXML
    private TextField ominusTextField;

    @FXML
    private TextField abminusTextField;

    private BBManager manager;
    private Stage stage;
    private BBManagerHomeController bbHomeController;

    public void setManager(BBManager manager){
        this.manager = manager;
    }
    public void setBBHomeController(BBManagerHomeController bbHomeController){
        this.bbHomeController = bbHomeController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //setInitValue();
    }

    public void setInitValue() {

        String[] cntList = ServerData.getBloodCountData(this.manager.getBloodBankId());
        aplusTextField.setText(cntList[0]);
        aminusTextField.setText(cntList[1]);
        bplusTextField.setText(cntList[2]);
        bminusTextField.setText(cntList[3]);
        oplusTextField.setText(cntList[4]);
        ominusTextField.setText(cntList[5]);
        abplusTextField.setText(cntList[6]);
        abminusTextField.setText(cntList[7]);
    }

    @FXML
    void submitButtonOnAction(ActionEvent event) {

        String updateData = manager.getBloodBankId()+"#";
        updateData+=aplusTextField.getText()+"#"+
                    aminusTextField.getText()+"#"+
                    bplusTextField.getText()+"#"+
                    bminusTextField.getText()+"#"+
                    oplusTextField.getText()+"#"+
                    ominusTextField.getText()+"#"+
                    abplusTextField.getText()+"#"+
                    abminusTextField.getText()+"#";

        ServerData.updateBloodCountData(updateData);

        this.bbHomeController.setBloodCount();
        this.stage.close();

    }

    public void setStage(Stage stage) {

        this.stage = stage;
    }
}
