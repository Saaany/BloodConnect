package com.termproject.Model;

public class BBManager {

    private int managerId;
    private String managerName;
    private String phone;
    private String email;
    private int bloodBankId;

    public int getBloodBankId() {
        return bloodBankId;
    }

    public void setBloodBankId(int bloodBankId) {
        this.bloodBankId = bloodBankId;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String manageName) {
        this.managerName = manageName;
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

    @Override
    public String toString() {
        return "BBManager{" +
                "managerId=" + managerId +
                ", managerName='" + managerName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", bloodBankId=" + bloodBankId +
                '}';
    }
}
