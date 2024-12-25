package com.termproject.Controllers;

import Utils.MyListener;
import com.termproject.Clients.ServerData;
import com.termproject.Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class UserInfoController {

    @FXML
    private Text userNameText;

    @FXML
    private Text bloodGroupText;

    @FXML
    private Text phoneNoText;

    @FXML
    private Text emailText;

    @FXML
    private Button messageButton;

    public void setUser(int user_id){

        this.user = ServerData.getUser(user_id);

        if(this.user.getUserName().equals("none")){
            return;
        }

        userNameText.setText(user.getUserName());
        bloodGroupText.setText(user.getBloodGroup());
        phoneNoText.setText(user.getPhone());
        emailText.setText(user.getEmail());

    }

    private User user;

}
