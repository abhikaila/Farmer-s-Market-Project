package com.example.dell.farmersmarket;

public class Urls {

    String ServerUploadProductPath = "http://localhosts/FarmersMarket/UploadData.php";
    String getSellingProductPath = "http://localhost/FarmersMarket/getSellingProduct.php";
    String RegisterNewUserData = "http://localhost/FarmersMarket/UploadUserData.php";
    String loginUser = "http://localhost/FarmersMarket/login.php";

    public String getLoginUser() { return loginUser; }

    public String getRegisterNewUserData() { return RegisterNewUserData; }

    public String getGetSellingProductPath() {
        return getSellingProductPath;
    }

    public String getServerUploadProductPath() {
        return ServerUploadProductPath;
    }
}
