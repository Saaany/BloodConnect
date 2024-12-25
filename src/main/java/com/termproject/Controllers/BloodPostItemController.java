package com.termproject.Controllers;

import Utils.MyListener;
import com.termproject.Clients.ServerData;
import com.termproject.Model.BloodPost;
import com.termproject.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class BloodPostItemController {

    @FXML
    private Label userLabel;

    @FXML
    private Label bloodGroupLabel;

    @FXML
    private Label quantityLabel;

    @FXML
    private Label locationLable;

    @FXML
    private Label timeLable;

    @FXML
    private Label phoneLable;

    @FXML
    private Label patientInfoLable;

    @FXML
    private Button deletePostButton;
    @FXML
    private Button editPostButton;

    private MyListener listener;
    private int user_id;
    private BloodPost post;
    private PostWindowController pwc;
    public void setPwc(PostWindowController pwc) {
        this.pwc = pwc;
    }

    public void setData2(BloodPost post, MyListener listener){

        this.listener = listener;
        this.user_id = post.getUser_id();

        userLabel.setText(post.getUser());
        bloodGroupLabel.setText(post.getBloodGroup());
        locationLable.setText(post.getLocation());
        timeLable.setText(post.getDate() +" at " +post.getTime());
        phoneLable.setText(post.getPhone());
        patientInfoLable.setText(post.getPatientInfo());
        quantityLabel.setText(post.getAmount());

        deletePostButton.setVisible(false);
        editPostButton.setVisible(false);

    }

    public void setData(BloodPost post){

        this.post = post;

        userLabel.setText(post.getUser());
        bloodGroupLabel.setText(post.getBloodGroup());
        locationLable.setText(post.getLocation());
        timeLable.setText(post.getDate()+" at "+post.getTime());
        phoneLable.setText(post.getPhone());
        patientInfoLable.setText(post.getPatientInfo());
        quantityLabel.setText(post.getAmount());

        deletePostButton.setVisible(true);

        System.out.println("hello world");
        String pDate = post.getDate();
        System.out.println("biday world");
        System.out.println(pDate);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));
        String currentDate = dtf.format(now);

        long daysDiff = 1;
        if(!pDate.equals("")) {
            LocalDate dateBefore = LocalDate.parse(pDate);
            LocalDate dateAfter = LocalDate.parse(currentDate);
            // Approach 1
            daysDiff = ChronoUnit.DAYS.between(dateAfter, dateBefore);
            System.out.println("The number of days between dates: " + daysDiff);
        }

        if(daysDiff>0){
            editPostButton.setVisible(true);
        }else{
            editPostButton.setVisible(false);
        }

    }


    @FXML
    public void editButtonOnAction() throws IOException {

        BloodPost bloodPost = ServerData.getBloodPost(post.getPost_id());

        //main.showBloodPostWindow();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/termproject/FXMLS/bloodPostEditWindow.fxml"));
        Parent root = loader.load();

        BloodPostEditWindowController controller = loader.getController();
        controller.setStage(stage);
        controller.setBloodPost(bloodPost);
        controller.setPwc(this.pwc);

        // Set the primary stage
        stage.setTitle("BloodConnect!");
        stage.setScene(new Scene(root, 480, 480));
        stage.showAndWait();
    }
    @FXML
    public void deleteButtonOnAction(){

        ServerData.deleteYourBloodPost(post.getPost_id());
        this.pwc.seeBloodPostButtonOnAction();
    }

    public void gridOnClickAction(MouseEvent mouseEvent) {

        try {
            listener.onClickListener(user_id);
        }catch (NullPointerException e){
            System.out.println("don't panic it happens");
        }
    }
}
