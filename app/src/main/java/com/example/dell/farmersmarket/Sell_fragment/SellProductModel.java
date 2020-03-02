package com.example.dell.farmersmarket.Sell_fragment;

public class SellProductModel {
    String Name, Category, City, Image, Quantity;

    public SellProductModel(String name, String category, String city, String image, String quantity) {
        Name = name;
        Category = category;
        Image = image;
        Quantity = quantity;
        City = city;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getCity() {
        return City;
    }
}
