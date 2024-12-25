package com.termproject.Model;

public class BloodBank {

    private int manager_id;
    private int bb_id;
    private String bbName;
    private String location;
    private String phone;
    private String email;

    public BloodBank(String bb_name, String bb_id, String man_id, String location, String man_phone, String man_email) {

        this.bbName = bb_name;
        this.bb_id = Integer.parseInt(bb_id);
        this.manager_id = Integer.parseInt(man_id);
        this.location = location;
        this.phone = man_phone;
        this.email = man_email;
    }

    public int getManager_id() {
        return manager_id;
    }

    public void setManager_id(int manager_id) {
        this.manager_id = manager_id;
    }

    public int getBb_id() {
        return bb_id;
    }

    public void setBb_id(int bb_id) {
        this.bb_id = bb_id;
    }

    public String getBbName() {
        return bbName;
    }

    public void setBbName(String bbName) {
        this.bbName = bbName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
}
