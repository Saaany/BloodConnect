package com.termproject.Controllers;

import com.termproject.Clients.ServerData;
import com.termproject.Model.BBManager;
import com.termproject.Model.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class EventShowWindowController {

    @FXML
    private GridPane grid;

    private List<Event> events = new ArrayList<>();
    private BBManager manager;

    private List<Event> getData(){

        List<Event> list = new ArrayList<>();

        list = ServerData.getYourEvents(manager.getManagerId());
        return list;
    }

    public void setGridValues(){

        events.clear();
        grid.getChildren().clear();

        events.addAll(getData());

        if(events.size()>0){

            int col = 0;
            int row = 0;
            try{
                for (int i = 0; i < events.size(); i++) {

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/com/termproject/FXMLS/bbEventItem.fxml"));
                    AnchorPane anchorPane = loader.load();

                    BBEventItemController controller = loader.getController();
                    controller.setManager(this.manager);
                    controller.setData(events.get(i));

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
