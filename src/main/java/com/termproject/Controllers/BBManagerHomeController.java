package com.termproject.Controllers;

import com.termproject.ClientMain;
import com.termproject.Clients.ServerData;
import com.termproject.Model.BBManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BBManagerHomeController implements Initializable {
    @FXML
    private ImageView logOutIcon;

    @FXML
    private ImageView userImageView;

    @FXML
    private Label bbNameLabel;
    @FXML
    private Text locationTextField;

    @FXML
    private Text managerNameText;

    @FXML
    private Label aplusCountLabel;

    @FXML
    private Label aminusCountLabel;

    @FXML
    private Label bplusCountLabel;

    @FXML
    private Label bminusCountLabel;

    @FXML
    private Label oplusCountLabel;

    @FXML
    private Label ominusCountLabel;

    @FXML
    private Label abplusCountLabel;

    @FXML
    private Label abminusCountLabel;

    private BBManager manager;
    private String[] cntList;
    private String[] bloodBankData = new String[3];

    private ClientMain main;
    public void setMain(ClientMain clientMain) {
        this.main = clientMain;
    }
    public void setManager(BBManager manager) {
        this.manager = manager;
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //setAllData();
    }

    public void setAllData() {

        setBBData();
        setBloodCount();
    }

    private void setBBData() {

        bloodBankData = ServerData.getBloodBankData(this.manager.getManagerId());
        //System.out.println(bloodBankData);
        System.out.println("all is well");
        bbNameLabel.setText(bloodBankData[0]);
        System.out.println(bbNameLabel.getText());
        managerNameText.setText(bloodBankData[1]);
        System.out.println(managerNameText.getText());
        locationTextField.setText(bloodBankData[2]);
        System.out.println(locationTextField.getText());
    }

    public void setBloodCount() {

        System.out.println("now in blood count");
        cntList = ServerData.getBloodCountData(this.manager.getBloodBankId());

        System.out.println("all data retrieved");
        aplusCountLabel.setText(cntList[0]);
        aminusCountLabel.setText(cntList[1]);
        bplusCountLabel.setText(cntList[2]);
        bminusCountLabel.setText(cntList[3]);
        oplusCountLabel.setText(cntList[4]);
        ominusCountLabel.setText(cntList[5]);
        abplusCountLabel.setText(cntList[6]);
        abminusCountLabel.setText(cntList[7]);
    }

    @FXML
    void logOutButtonOnAction() throws IOException {
        main.showFirstLogInPage();
    }
    @FXML
    public void createNewEventButtonOnAction() throws IOException {

        //main.showBloodPostWindow();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/termproject/FXMLS/eventWindow.fxml"));
        Parent root = loader.load();

        EventController controller = loader.getController();
        controller.setManager(this.manager);
        controller.setStage(stage);

        // Set the primary stage
        stage.setTitle("BloodConnect!");
        stage.setScene(new Scene(root, 506, 414));
        stage.showAndWait();
    }

    @FXML
    void eventsButtonOnAction(ActionEvent event) throws IOException {

        //main.showBloodPostWindow();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/termproject/FXMLS/eventShowWindow.fxml"));
        Parent root = loader.load();

        EventShowWindowController controller = loader.getController();
        controller.setManager(this.manager);
        controller.setGridValues();
        // Set the primary stage
        stage.setTitle("BloodConnect!");
        stage.setScene(new Scene(root, 640, 650));
        stage.showAndWait();
    }

    @FXML
    void profileButtonOnAction(ActionEvent event) {
        setBloodCount();
    }

    @FXML
    void updateCountButtonOnAction(ActionEvent event) throws IOException {
        //main.showBloodPostWindow();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/termproject/FXMLS/updateBloodCountWindow.fxml"));
        Parent root = loader.load();

        BloodCountController controller = loader.getController();
        //controller.setUserId(user.getUserId());
        controller.setManager(this.manager);
        controller.setStage(stage);
        controller.setBBHomeController(this);
        controller.setInitValue();

        // Set the primary stage
        stage.setTitle("BloodConnect!");
        stage.setScene(new Scene(root, 670, 250));
        stage.showAndWait();
    }

    @FXML
    void showReqButtonOnAction() throws IOException {

        //main.showBloodPostWindow();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/termproject/FXMLS/reqShowWindow.fxml"));
        Parent root = loader.load();

        RecShowWindowController controller = loader.getController();
        controller.setHome(this);
        controller.setManager(this.manager);
        controller.setGridValues();
        // Set the primary stage
        stage.setTitle("BloodConnect!");
        stage.setScene(new Scene(root, 655, 650));
        stage.showAndWait();
    }

    @FXML
    void aplusIncrementOnAction(){
        cntList = ServerData.getBloodCountData(this.manager.getBloodBankId());
        cntList[0] = (Integer.parseInt(cntList[0]) + 1)+"";

        String updateData = manager.getBloodBankId()+"#";
        updateData+=cntList[0]+"#"+
                cntList[1]+"#"+
                cntList[2]+"#"+
                cntList[3]+"#"+
                cntList[4]+"#"+
                cntList[5]+"#"+
                cntList[6]+"#"+
                cntList[7]+"#";

        ServerData.updateBloodCountData(updateData);

        this.setBloodCount();
    }
    @FXML
    void aplusDecrementOnAction(){
        cntList = ServerData.getBloodCountData(this.manager.getBloodBankId());
        cntList[0] = (Integer.parseInt(cntList[0]) - 1)+"";

        String updateData = manager.getBloodBankId()+"#";
        updateData+=cntList[0]+"#"+
                cntList[1]+"#"+
                cntList[2]+"#"+
                cntList[3]+"#"+
                cntList[4]+"#"+
                cntList[5]+"#"+
                cntList[6]+"#"+
                cntList[7]+"#";

        ServerData.updateBloodCountData(updateData);

        this.setBloodCount();
    }

    @FXML
    void aminusIncrementOnAction(){
        cntList = ServerData.getBloodCountData(this.manager.getBloodBankId());
        cntList[1] = (Integer.parseInt(cntList[1]) + 1)+"";

        String updateData = manager.getBloodBankId()+"#";
        updateData+=cntList[0]+"#"+
                cntList[1]+"#"+
                cntList[2]+"#"+
                cntList[3]+"#"+
                cntList[4]+"#"+
                cntList[5]+"#"+
                cntList[6]+"#"+
                cntList[7]+"#";

        ServerData.updateBloodCountData(updateData);

        this.setBloodCount();
    }
    @FXML
    void aminusDecrementOnAction(){
        cntList = ServerData.getBloodCountData(this.manager.getBloodBankId());
        cntList[1] = (Integer.parseInt(cntList[1]) - 1)+"";

        String updateData = manager.getBloodBankId()+"#";
        updateData+=cntList[0]+"#"+
                cntList[1]+"#"+
                cntList[2]+"#"+
                cntList[3]+"#"+
                cntList[4]+"#"+
                cntList[5]+"#"+
                cntList[6]+"#"+
                cntList[7]+"#";

        ServerData.updateBloodCountData(updateData);

        this.setBloodCount();
    }

    @FXML
    void bplusIncrementOnAction(){
        cntList = ServerData.getBloodCountData(this.manager.getBloodBankId());
        cntList[2] = (Integer.parseInt(cntList[2]) + 1)+"";

        String updateData = manager.getBloodBankId()+"#";
        updateData+=cntList[0]+"#"+
                cntList[1]+"#"+
                cntList[2]+"#"+
                cntList[3]+"#"+
                cntList[4]+"#"+
                cntList[5]+"#"+
                cntList[6]+"#"+
                cntList[7]+"#";

        ServerData.updateBloodCountData(updateData);

        this.setBloodCount();
    }
    @FXML
    void bplusDecrementOnAction(){
        cntList = ServerData.getBloodCountData(this.manager.getBloodBankId());
        cntList[2] = (Integer.parseInt(cntList[2]) - 1)+"";

        String updateData = manager.getBloodBankId()+"#";
        updateData+=cntList[0]+"#"+
                cntList[1]+"#"+
                cntList[2]+"#"+
                cntList[3]+"#"+
                cntList[4]+"#"+
                cntList[5]+"#"+
                cntList[6]+"#"+
                cntList[7]+"#";

        ServerData.updateBloodCountData(updateData);

        this.setBloodCount();
    }

    @FXML
    void bminusIncrementOnAction(){
        cntList = ServerData.getBloodCountData(this.manager.getBloodBankId());
        cntList[3] = (Integer.parseInt(cntList[3]) + 1)+"";

        String updateData = manager.getBloodBankId()+"#";
        updateData+=cntList[0]+"#"+
                cntList[1]+"#"+
                cntList[2]+"#"+
                cntList[3]+"#"+
                cntList[4]+"#"+
                cntList[5]+"#"+
                cntList[6]+"#"+
                cntList[7]+"#";

        ServerData.updateBloodCountData(updateData);

        this.setBloodCount();
    }
    @FXML
    void bminusDecrementOnAction(){
        cntList = ServerData.getBloodCountData(this.manager.getBloodBankId());
        cntList[3] = (Integer.parseInt(cntList[3]) - 1)+"";

        String updateData = manager.getBloodBankId()+"#";
        updateData+=cntList[0]+"#"+
                cntList[1]+"#"+
                cntList[2]+"#"+
                cntList[3]+"#"+
                cntList[4]+"#"+
                cntList[5]+"#"+
                cntList[6]+"#"+
                cntList[7]+"#";

        ServerData.updateBloodCountData(updateData);

        this.setBloodCount();
    }

    @FXML
    void oplusIncrementOnAction(){
        cntList = ServerData.getBloodCountData(this.manager.getBloodBankId());
        cntList[4] = (Integer.parseInt(cntList[4]) + 1)+"";

        String updateData = manager.getBloodBankId()+"#";
        updateData+=cntList[0]+"#"+
                cntList[1]+"#"+
                cntList[2]+"#"+
                cntList[3]+"#"+
                cntList[4]+"#"+
                cntList[5]+"#"+
                cntList[6]+"#"+
                cntList[7]+"#";

        ServerData.updateBloodCountData(updateData);

        this.setBloodCount();
    }
    @FXML
    void oplusDecrementOnAction(){
        cntList = ServerData.getBloodCountData(this.manager.getBloodBankId());
        cntList[4] = (Integer.parseInt(cntList[4]) - 1)+"";

        String updateData = manager.getBloodBankId()+"#";
        updateData+=cntList[0]+"#"+
                cntList[1]+"#"+
                cntList[2]+"#"+
                cntList[3]+"#"+
                cntList[4]+"#"+
                cntList[5]+"#"+
                cntList[6]+"#"+
                cntList[7]+"#";

        ServerData.updateBloodCountData(updateData);

        this.setBloodCount();
    }
    @FXML
    void ominusIncrementOnAction(){
        cntList = ServerData.getBloodCountData(this.manager.getBloodBankId());
        cntList[5] = (Integer.parseInt(cntList[5]) + 1)+"";

        String updateData = manager.getBloodBankId()+"#";
        updateData+=cntList[0]+"#"+
                cntList[1]+"#"+
                cntList[2]+"#"+
                cntList[3]+"#"+
                cntList[4]+"#"+
                cntList[5]+"#"+
                cntList[6]+"#"+
                cntList[7]+"#";

        ServerData.updateBloodCountData(updateData);

        this.setBloodCount();
    }
    @FXML
    void ominusDecrementOnAction(){
        cntList = ServerData.getBloodCountData(this.manager.getBloodBankId());
        cntList[5] = (Integer.parseInt(cntList[5]) -1)+"";

        String updateData = manager.getBloodBankId()+"#";
        updateData+=cntList[0]+"#"+
                cntList[1]+"#"+
                cntList[2]+"#"+
                cntList[3]+"#"+
                cntList[4]+"#"+
                cntList[5]+"#"+
                cntList[6]+"#"+
                cntList[7]+"#";

        ServerData.updateBloodCountData(updateData);

        this.setBloodCount();
    }
    @FXML
    void abplusIncrementOnAction(){
        cntList = ServerData.getBloodCountData(this.manager.getBloodBankId());
        cntList[6] = (Integer.parseInt(cntList[6]) + 1)+"";

        String updateData = manager.getBloodBankId()+"#";
        updateData+=cntList[0]+"#"+
                cntList[1]+"#"+
                cntList[2]+"#"+
                cntList[3]+"#"+
                cntList[4]+"#"+
                cntList[5]+"#"+
                cntList[6]+"#"+
                cntList[7]+"#";

        ServerData.updateBloodCountData(updateData);

        this.setBloodCount();
    }
    @FXML
    void abplusDecrementOnAction(){
        cntList = ServerData.getBloodCountData(this.manager.getBloodBankId());
        cntList[6] = (Integer.parseInt(cntList[6]) - 1)+"";

        String updateData = manager.getBloodBankId()+"#";
        updateData+=cntList[0]+"#"+
                cntList[1]+"#"+
                cntList[2]+"#"+
                cntList[3]+"#"+
                cntList[4]+"#"+
                cntList[5]+"#"+
                cntList[6]+"#"+
                cntList[7]+"#";

        ServerData.updateBloodCountData(updateData);

        this.setBloodCount();
    }
    @FXML
    void abminusIncrementOnAction(){
        cntList = ServerData.getBloodCountData(this.manager.getBloodBankId());
        cntList[7] = (Integer.parseInt(cntList[7]) + 1)+"";

        String updateData = manager.getBloodBankId()+"#";
        updateData+=cntList[0]+"#"+
                cntList[1]+"#"+
                cntList[2]+"#"+
                cntList[3]+"#"+
                cntList[4]+"#"+
                cntList[5]+"#"+
                cntList[6]+"#"+
                cntList[7]+"#";

        ServerData.updateBloodCountData(updateData);

        this.setBloodCount();
    }
    @FXML
    void abminusDecrementOnAction(){
        cntList = ServerData.getBloodCountData(this.manager.getBloodBankId());
        cntList[7] = (Integer.parseInt(cntList[7]) - 1)+"";

        String updateData = manager.getBloodBankId()+"#";
        updateData+=cntList[0]+"#"+
                cntList[1]+"#"+
                cntList[2]+"#"+
                cntList[3]+"#"+
                cntList[4]+"#"+
                cntList[5]+"#"+
                cntList[6]+"#"+
                cntList[7]+"#";

        ServerData.updateBloodCountData(updateData);

        this.setBloodCount();
    }
}
