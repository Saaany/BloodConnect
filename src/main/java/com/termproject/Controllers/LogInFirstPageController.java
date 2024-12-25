package com.termproject.Controllers;

import com.termproject.ClientMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class LogInFirstPageController {

    private ClientMain main;
    @FXML
    private Button userButton;

    @FXML
    private Button managerButton;

    @FXML
    void managerButtonOnAction(ActionEvent event) throws IOException {
        main.showManagerLogInPage();
    }
    @FXML
    void userButtonOnAction(ActionEvent event) throws IOException {
        main.showLogInPage();
    }

    public void setMain(ClientMain main){
        this.main = main;
    }
}
