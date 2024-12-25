package com.termproject.Controllers;

import com.termproject.Clients.ServerData;
import com.termproject.Model.BloodPost;
import com.termproject.Model.DonationPost;
import com.termproject.Model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PostWindowController implements Initializable {

    private User user;
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
    private GridPane grid;

    private List<BloodPost> list = new ArrayList<>();
    private List<DonationPost> list2 = new ArrayList<>();


    private List<BloodPost> getData(){

        List<BloodPost> list = new ArrayList<>();

        list = ServerData.getYourBloodPosts(user.getUserId());

        return list;
    }

    private List<DonationPost> getDonationData(){

        List<DonationPost> list = new ArrayList<>();

        list = ServerData.getYourDonationPosts(user.getUserId());

        return list;
    }

    @FXML
    public void seeBloodPostButtonOnAction(){

        list.clear();
        grid.getChildren().clear();

        list.addAll(getData());

        int col = 0;
        int row = 0;
        try{
            for (int i = 0; i < list.size(); i++) {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/com/termproject/FXMLS/bloodPostItem.fxml"));
                AnchorPane anchorPane = loader.load();

                BloodPostItemController controller = loader.getController();
                controller.setData(list.get(i));
                controller.setPwc(this);

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

    @FXML
    private void seeDonationPostButtonOnAction(){

        list2.clear();
        grid.getChildren().clear();

        list2.addAll(getDonationData());

        int col = 0;
        int row = 0;
        try{
            for (int i = 0; i < list2.size(); i++) {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/com/termproject/FXMLS/donationItem.fxml"));
                AnchorPane anchorPane = loader.load();

                DonationItemController controller = loader.getController();

                controller.setData(list2.get(i),user.getBloodGroup());

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

       try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/termproject/FXMLS/transperent.fxml"));

            AnchorPane anchorPane = fxmlLoader.load();
            grid.add(anchorPane,0,0);

            GridPane.setMargin(anchorPane,new Insets(10));

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
