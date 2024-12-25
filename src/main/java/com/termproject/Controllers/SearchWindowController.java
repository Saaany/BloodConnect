package com.termproject.Controllers;

import Utils.MyListener;
import com.termproject.Clients.ServerData;
import com.termproject.Model.BloodBank;
import com.termproject.Model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SearchWindowController implements Initializable {

    @FXML
    private GridPane grid;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //setGridValues();
    }

    private List<BloodBank> bbList = new ArrayList<>();
    private MyListener listener;
    private String searchLocation;
    private User user;
    public void setUser(User user){
        this.user = user;
    }
    public void setSearchLocation(String inputLocation){
        this.searchLocation = inputLocation;
    }

    private List<BloodBank> getData(){

        List<BloodBank> list = new ArrayList<>();

        list = ServerData.getAllBloodBankByLocation(searchLocation);
        return list;
    }

    public void setGridValues(){

        bbList.clear();
        grid.getChildren().clear();

        bbList.addAll(getData());

        if(bbList.size()>0){

            listener = new MyListener() {
                @Override
                public void onClickListener(int user_id) {
                    //do nothing
                }

                @Override
                public void onClickListener(int bb_id, String bb_name, String man_phone, String man_email) {

                    try {
                        setChosenItem(bb_id,bb_name,man_phone,man_email);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
        }

        int col = 0;
        int row = 0;
        try{
            for (int i = 0; i < bbList.size(); i++) {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/com/termproject/FXMLS/bbSearchItem.fxml"));
                AnchorPane anchorPane = loader.load();

                BBSearchItemController controller = loader.getController();

                controller.setData2(bbList.get(i),listener);

                grid.add(anchorPane,col,row++);

//                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
//                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
//                grid.setMaxWidth(Region.USE_PREF_SIZE);
//
//                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
//                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
//                grid.setMinHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane,new Insets(10));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setChosenItem(int bb_id, String bb_name, String man_phone, String man_email) throws IOException {

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/termproject/FXMLS/bloodBankShowWindow.fxml"));
        Parent root = loader.load();

        BBShowWindowController controller = loader.getController();
        controller.setStage(stage);
        controller.setData(bb_name,man_phone,man_email);
        controller.setBloodCount(bb_id);
        controller.setUser(this.user);

        // Set the primary stage
        stage.setTitle("User Info!");
        stage.setScene(new Scene(root, 750, 265));
        stage.showAndWait();
    }

}
