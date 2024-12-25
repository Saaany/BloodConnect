package com.termproject.Model;

public class BloodPost {

    private String user;
    private int post_id;
    private String bloodGroup;
    private String amount;
    private String location;
    private String date;
    private String time;
    private String phone;

    private int user_id;

    public BloodPost(){

    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public BloodPost(int user_id, String user, String bloodGroup, String amount, String location, String date, String time, String phone, String patientInfo) {
        this.user = user;
        this.bloodGroup = bloodGroup;
        this.amount = amount;
        this.location = location;
        this.date = date;
        this.time = time;
        this.phone = phone;
        this.patientInfo = patientInfo;
        this.user_id = user_id;
    }

    private String patientInfo;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPatientInfo() {
        return patientInfo;
    }

    public void setPatientInfo(String patientInfo) {
        this.patientInfo = patientInfo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }
}
