package com.example.dell.farmersmarket;

public class User {
//    int id;
    String mobileNo, password;

    public User(String mobileNo, String password) {
        this.mobileNo = mobileNo;
        this.password = password;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
