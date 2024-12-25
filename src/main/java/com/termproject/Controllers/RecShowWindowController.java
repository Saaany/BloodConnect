package com.termproject.Controllers;

import Utils.MyListener;
import com.termproject.Clients.ServerData;
import com.termproject.Model.BBManager;
import com.termproject.Model.BBRequestModel;
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

public class RecShowWindowController implements Initializable {

    @FXML
    private GridPane grid;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //setGridValues();
    }

    private List<BBRequestModel> bbList = new ArrayList<>();
    private BBManager manager;
    private BBManagerHomeController home;
    public void setHome(BBManagerHomeController home){
        this.home = home;
    }

    private List<BBRequestModel> getData(){

        List<BBRequestModel> list = new ArrayList<>();

        list = ServerData.getAllRequests(manager.getBloodBankId());
        return list;
    }

    public void setGridValues(){

        bbList.clear();
        grid.getChildren().clear();

        bbList.addAll(getData());

        if(bbList.size()>0){

        int col = 0;
        int row = 0;
        try{
            for (int i = 0; i < bbList.size(); i++) {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/com/termproject/FXMLS/bbBloodReqItem.fxml"));
                AnchorPane anchorPane = loader.load();

                BBBloodReqItemController controller = loader.getController();
                controller.setHome(this.home);
                controller.setManager(this.manager);
                controller.setData(bbList.get(i));

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

}

    public void setManager(BBManager manager) {
        this.manager = manager;
    }
}
