package com.termproject.Model;

public class BBRequestModel {

    private int req_id;
    private int bb_id;
    private String user_name;
    private String blood_group;
    private String amount;
    private String date;
    private String phone;
    private String status;


    public int getReq_id() {
        return req_id;
    }

    public void setReq_id(int req_id) {
        this.req_id = req_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getBb_id() {
        return bb_id;
    }

    public void setBb_id(int bb_id) {
        this.bb_id = bb_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getBlood_group() {
        return blood_group;
    }

    public void setBlood_group(String blood_group) {
        this.blood_group = blood_group;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BBRequestModel(String req_id, int bb_id, String user_name, String blood_group, String amount, String date, String phone, String status) {
        this.req_id = Integer.parseInt(req_id);
        this.bb_id = bb_id;
        this.user_name = user_name;
        this.blood_group = blood_group;
        this.amount = amount;
        this.date = date;
        this.phone = phone;
        this.status = status;
    }
}
