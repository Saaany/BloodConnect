package com.termproject.Controllers;

import Utils.MyListener;
import com.termproject.ClientMain;
import com.termproject.Clients.ServerData;
import com.termproject.Model.BloodPost;
import com.termproject.Model.Event;
import com.termproject.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class HomeController implements Initializable {

    private User user;
    private ClientMain main;
    private MyListener listener;

    public void setMain(ClientMain clientMain) {
        this.main = clientMain;
    }
    public void setUser(User user){
        this.user = user;
        userNameText.setText(user.getUserName());
        bloodGroupText.setText(user.getBloodGroup());
    }

    @FXML
    private Text userNameText;
    @FXML
    private Text bloodGroupText;
    @FXML
    private TextField searchTextField;

    @FXML
    private GridPane grid;

    @FXML
    public void searchButtonOnAction() throws IOException {

        String searchText = searchTextField.getText();
        System.out.println(searchText);

        //main.showBloodPostWindow();
        if(!searchText.equals("")){
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/termproject/FXMLS/bloodBankSearchWindow.fxml"));
            Parent root = loader.load();

            SearchWindowController controller = loader.getController();
            controller.setUser(this.user);
            controller.setSearchLocation(searchText.toUpperCase());
            controller.setGridValues();
            // Set the primary stage
            stage.setTitle("BloodConnect!");
            stage.setScene(new Scene(root, 630, 650));
            stage.showAndWait();
        }
    }

    @FXML
    public void bloodPostButtonOnAction() throws IOException {

        //main.showBloodPostWindow();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/termproject/FXMLS/bloodPostWindow.fxml"));
        Parent root = loader.load();

        BloodPostController controller = loader.getController();
        controller.setHome(this);
        controller.setUserId(user.getUserId());
        controller.setStage(stage);

        // Set the primary stage
        stage.setTitle("BloodConnect!");
        stage.setScene(new Scene(root, 480, 480));
        stage.showAndWait();
    }

    @FXML
    public void donationButtonOnAction() throws IOException {
        System.out.println("hello world");
        String lastDonationDate = ServerData.getLastDonationDate(user.getUserId());
        System.out.println("biday world");
        System.out.println(lastDonationDate);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));
        String currentDate = dtf.format(now);

        long daysDiff = 60;
        if(!lastDonationDate.equals("")) {
            LocalDate dateBefore = LocalDate.parse(lastDonationDate);
            LocalDate dateAfter = LocalDate.parse(currentDate);
            // Approach 1
            daysDiff = ChronoUnit.DAYS.between(dateBefore, dateAfter);
            System.out.println("The number of days between dates: " + daysDiff);
        }

        if(daysDiff>56){

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/termproject/FXMLS/donationWindow.fxml"));
            Parent root = loader.load();

            DonationController controller = loader.getController();
            controller.setUserId(user.getUserId());
            controller.setStage(stage);
            // Set the primary stage
            stage.setTitle("BloodConnect!");
            stage.setScene(new Scene(root, 480, 250));
            stage.showAndWait();
        }else{
            showBloodDonationWarning(lastDonationDate,56-daysDiff);
        }
    }

    private List<BloodPost> list = new ArrayList<>();
    private List<Event> eventList = new ArrayList<>();

    private List<BloodPost> getData(){

        List<BloodPost> list = new ArrayList<>();

        list = ServerData.getBloodPostData();
        return list;
    }

    @FXML
    public void notificationButtonOnAction(ActionEvent event){

        list.clear();
        grid.getChildren().clear();

        list.addAll(getNotificationData());

        if(list.size()>0){

            listener = new MyListener() {
                @Override
                public void onClickListener(int user_id) {

                    try {
                        setChosenItem(user_id);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onClickListener(int bb_id, String bb_name, String man_phone, String man_email) {
                    //do nothing
                }
            };
        }

        int col = 0;
        int row = 0;
        try{
            for (int i = 0; i < list.size(); i++) {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/com/termproject/FXMLS/notification.fxml"));
                AnchorPane anchorPane = loader.load();

                NotificationController controller = loader.getController();

                controller.setData(list.get(i),listener);

                grid.add(anchorPane,col,row++);

                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMinHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane,new Insets(10));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private List<BloodPost> getNotificationData() {

        List<BloodPost> list = new ArrayList<>();

        list = ServerData.getNotificationData(String.valueOf(user.getUserId()),user.getBloodGroup());

        return list;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        homeButtonOnAction();

    }

    @FXML
    void homeButtonOnAction() {

        list.clear();
        grid.getChildren().clear();

        list.addAll(getData());

        if(list.size()>0){

            listener = new MyListener() {
                @Override
                public void onClickListener(int user_id) {

                    try {
                        setChosenItem(user_id);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onClickListener(int bb_id, String bb_name, String man_phone, String man_email) {
                    //do nothing
                }
            };
        }

        int col = 0;
        int row = 0;
        try{
            for (int i = 0; i < list.size(); i++) {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/com/termproject/FXMLS/bloodPostItem.fxml"));
                AnchorPane anchorPane = loader.load();

                BloodPostItemController controller = loader.getController();

                controller.setData2(list.get(i),listener);

                grid.add(anchorPane,col,row++);

                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMinHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane,new Insets(10));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setChosenItem(int user_id) throws IOException {

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/termproject/FXMLS/userInfoWindow.fxml"));
        Parent root = loader.load();

        UserInfoController controller = loader.getController();
        controller.setUser(user_id);

        // Set the primary stage
        stage.setTitle("User Info!");
        stage.setScene(new Scene(root, 500, 500));
        stage.showAndWait();
    }

    @FXML
    private void bloodButtonOnAction() throws IOException {
        //main.showBloodPostWindow();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/termproject/FXMLS/postWindow.fxml"));
        Parent root = loader.load();

        PostWindowController controller = loader.getController();
        controller.setUser(user);

        // Set the primary stage
        stage.setTitle("BloodConnect!");
        stage.setScene(new Scene(root, 800, 640));
        stage.showAndWait();
    }

    @FXML
    void eventsButtonOnAction(){

        eventList.clear();
        grid.getChildren().clear();

        eventList.addAll(getEventsData());

        int col = 0;
        int row = 0;
        try{
            for (int i = 0; i < eventList.size(); i++) {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/com/termproject/FXMLS/eventItem.fxml"));
                AnchorPane anchorPane = loader.load();

                EventItemController controller = loader.getController();

                controller.setData(eventList.get(i));

                grid.add(anchorPane,col,row++);

                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMinHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane,new Insets(10));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private List<Event> getEventsData() {

        List<Event> list = new ArrayList<>();

        list = ServerData.getEventData();

        return list;
    }

    @FXML
    void logOutButtonOnAction() throws IOException {
        main.showFirstLogInPage();
    }

    public void showBloodDonationWarning(String lastDate, long remDays){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.APPLICATION_MODAL);

        alert.setTitle("Important");
        alert.setHeaderText("You can donate blood again after "+ remDays+" days!");
        alert.setContentText("Your last donation was on "+ lastDate +" !");
        alert.showAndWait();
    }

}
