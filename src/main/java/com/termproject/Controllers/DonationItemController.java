package com.termproject.Controllers;

import com.termproject.Model.DonationPost;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DonationItemController {

    @FXML
    private Label bloodGroupLabel;
    @FXML
    private Label locationLabel;
    @FXML
    private Label dateLabel;

    void setData(DonationPost donationPost, String bloodGroup){
        bloodGroupLabel.setText(bloodGroup);
        locationLabel.setText(donationPost.getLocation());
        dateLabel.setText(donationPost.getDate());
    }

}
