package com.termproject.Controllers;

import Utils.MyListener;
import com.termproject.Model.BloodPost;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class NotificationController {

    @FXML
    private Text userLabel;

    @FXML
    private Label bloodGroupLabel;

    @FXML
    private Label quantityLabel;

    @FXML
    private Label locationLabel;

    @FXML
    private Label timeLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label patientInfoLabel;

    private MyListener listener;
    private int user_id;

    public void setData(BloodPost post,MyListener listener){

        this.listener = listener;
        this.user_id = post.getUser_id();

        userLabel.setText(post.getUser());
        bloodGroupLabel.setText(post.getBloodGroup());
        locationLabel.setText(post.getLocation());
        timeLabel.setText(post.getDate()+" at "+post.getTime());
        phoneLabel.setText(post.getPhone());
        patientInfoLabel.setText(post.getPatientInfo());
        quantityLabel.setText(post.getAmount());

    }

    public void gridOnClickAction(MouseEvent mouseEvent) {

        listener.onClickListener(user_id);
    }
}
