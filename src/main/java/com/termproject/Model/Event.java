package com.termproject.Model;

public class Event {

    private int event_id;
    private int manager_id;
    private String BloodBankName;
    private String eventDate;
    private String location;
    private String startTime;
    private String endTime;
    private String phone;
    private String email;
    private String eventInfo;
    private String creation_date;

    public Event(){}
    public Event(String event_id, String man_id, String date, String location, String start_time, String end_time, String event_info, String phone, String email, String bb_name) {

        this.event_id = Integer.parseInt(event_id);
        this.manager_id= Integer.parseInt(man_id);
        this.BloodBankName = bb_name;
        this.location = location;
        this.eventDate = date;
        this.startTime = start_time;
        this.endTime = end_time;
        this.eventInfo = event_info;
        this.phone = phone;
        this.email = email;
    }

    public int getManager_id() {
        return manager_id;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public void setManager_id(int manager_id) {
        this.manager_id = manager_id;
    }

    public String getBloodBankName() {
        return BloodBankName;
    }

    public void setBloodBankName(String bloodBankName) {
        BloodBankName = bloodBankName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEventInfo() {
        return eventInfo;
    }

    public void setEventInfo(String eventInfo) {
        this.eventInfo = eventInfo;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }
}
