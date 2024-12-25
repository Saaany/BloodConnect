package com.termproject.Controllers;

import Utils.MyListener;
import com.termproject.Model.BloodBank;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class BBSearchItemController {
    @FXML
    Text bbName;
    @FXML
    Text bbLocation;

    private MyListener listener;
    private int bb_id;
    private String bb_name;
    private String man_phone;
    private String man_email;

    public void setData2(BloodBank bloodBank, MyListener listener) {

        this.listener = listener;
        this.bb_id = bloodBank.getBb_id();
        this.bb_name = bloodBank.getBbName();
        this.man_phone = bloodBank.getPhone();
        this.man_email = bloodBank.getEmail();

        bbName.setText(bloodBank.getBbName());
        bbLocation.setText(bloodBank.getLocation());
    }

    public void gridOnClickAction(MouseEvent mouseEvent) {
        try {
            listener.onClickListener(bb_id,bb_name,man_phone,man_email);
        }catch (NullPointerException e){
            System.out.println("don't panic it happens");
        }
    }
}
