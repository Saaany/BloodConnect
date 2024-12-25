package com.termproject.Controllers;

import com.termproject.Model.BBManager;
import com.termproject.Model.BBRequestModel;
import com.termproject.Model.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class BBEventItemController {

    @FXML
    private Label startTimeLabel;

    @FXML
    private Label locationLabel;

    @FXML
    private Label eventDate;

    @FXML
    private Label eventInfoLabel;

    @FXML
    private Label endTimeLabel;

    @FXML
    private Text creationDate;

    private BBManager manager;
    private Event event;

    public void setManager(BBManager manager) {
        this.manager = manager;
    }

    public void setData(Event event) {
        this.event = event;
        locationLabel.setText(event.getLocation());
        eventDate.setText(event.getEventDate());
        startTimeLabel.setText(event.getStartTime());
        endTimeLabel.setText(event.getEndTime());
        eventInfoLabel.setText(event.getEventInfo());
        creationDate.setText(event.getCreation_date());
    }
}
