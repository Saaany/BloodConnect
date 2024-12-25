package com.termproject.Controllers;

import com.termproject.Clients.ServerData;
import com.termproject.Model.BBManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class EventController implements Initializable {

    @FXML
    private TextField Location;

    @FXML
    private DatePicker eventDate;

    @FXML
    private TextArea eventInfo;

    @FXML
    private Button eventButton;

    @FXML
    private Label eventLabel;


    @FXML
    private ChoiceBox<String> startTimeChoiceBox;
    private String[] timeArr = {"1","2","3","4","5","6","7","8","9","10","11","12"};
    @FXML
    private ChoiceBox<String> endTimeChoiceBox;

    @FXML
    private ChoiceBox<String> ampmBox1;
    private String[] ampm ={"AM","PM"};
    @FXML
    private ChoiceBox<String> ampmBox2;

    @FXML
    private Text startTimeLabel;

    @FXML
    private Text endTimelabel;

    @FXML
    private Text ampmLabel1;

    @FXML
    private Text ampmLabel2;

    private BBManager manager;
    private Stage stage;
    private String date = "";
    private String sTime = "";
    private String sAmPm = "";
    private String eTime = "";
    private String eAmPm = "";

    @FXML
    public void getEventDate(){
        LocalDate lastDate = eventDate.getValue();
        date = lastDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

    }

    @FXML
    void createEventButtonOnAction(ActionEvent event) {
        String pt = "";
        if(date.equals("")) {
            if(pt.length()>0)
                pt+=", ";
            pt += "Event date ";
        }
        if(Location.getText().equals("")){
            if(pt.length()>0)
                pt+=", ";
            pt +="Location ";
        }
        if(sTime.equals("")){
            if(pt.length()>0){
                pt+=", ";
            }
            pt+= "start time ";
        }
        if(sAmPm.equals("")){
            if(pt.length()>0){
                pt+=", ";
            }
            pt+= "AM/PM ";
        }
        if(eTime.equals("")){
            if(pt.length()>0)
                pt+=", ";
            pt += "end time";
        }
        if(eAmPm.equals("")){
            if(pt.length()>0)
                pt+=", ";
            pt += "AM/PM ";
        }
        if(eventInfo.getText().equals("")){
            if(pt.length()>0)
                pt+=", ";
            pt += "Event Info ";
        }

        int l = pt.length();
        if(l>0){
            pt += ("Required!");
            eventLabel.setText(pt);
            return;
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));
        String currentDate = dtf.format(now);

        ServerData.createEvent(manager.getManagerId(),date,Location.getText(),sTime+" "+sAmPm,eTime+" "+eAmPm,eventInfo.getText(),currentDate);
        showEventConfirmation();

        stage.close();

    }

    public void showEventConfirmation(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.APPLICATION_MODAL);

        alert.setTitle("Event Created");
        alert.setHeaderText("Event Created Successfully!");
        alert.setContentText("");
        alert.showAndWait();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public void setManager(BBManager manager){this.manager = manager;}

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        startTimeChoiceBox.getItems().addAll(timeArr);
        endTimeChoiceBox.getItems().addAll(timeArr);
        ampmBox1.getItems().addAll(ampm);
        ampmBox2.getItems().addAll(ampm);

        startTimeChoiceBox.setOnAction(this::getTime1);
        endTimeChoiceBox.setOnAction(this::getTime2);
        ampmBox1.setOnAction(this::getAmPm1);
        ampmBox2.setOnAction(this::getAmPm2);
    }

    private void getAmPm2(ActionEvent event) {
        sAmPm = ampmBox2.getValue();
        ampmLabel2.setText("");
    }
    private void getAmPm1(ActionEvent event) {
        eAmPm = ampmBox1.getValue();
        ampmLabel1.setText("");
    }

    private void getTime1(ActionEvent event) {
        sTime = startTimeChoiceBox.getValue();
        startTimeLabel.setText("");
    }

    private void getTime2(ActionEvent event) {
        eTime = endTimeChoiceBox.getValue();
        endTimelabel.setText("");
    }
}
