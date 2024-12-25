package com.termproject.Controllers;

import com.termproject.Model.BloodPost;
import com.termproject.Model.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class EventItemController {

    @FXML
    private Label bbNameLabel;

    @FXML
    private Text startTimeLabel;

    @FXML
    private Label locationLable;

    @FXML
    private Label dateLabel;

    @FXML
    private Label eventInfoLabel;

    @FXML
    private Text endTimeLabel;

    @FXML
    private Text phoneLabel;

    @FXML
    private Label emailLabel;

    public void setData(Event event){

        bbNameLabel.setText(event.getBloodBankName());
        dateLabel.setText(event.getEventDate());
        locationLable.setText(event.getLocation());
        startTimeLabel.setText(event.getStartTime());
        endTimeLabel.setText(event.getEndTime());
        phoneLabel.setText(event.getPhone());
        emailLabel.setText(event.getEmail());
        eventInfoLabel.setText(event.getEventInfo());

    }

}
