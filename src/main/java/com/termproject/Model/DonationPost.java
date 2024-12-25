package com.termproject.Model;

public class DonationPost {

    private String user;
    private String location;
    private String date;

    public DonationPost(String user_name, String location, String date) {
        this.user =user_name;
        this.location = location;
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
