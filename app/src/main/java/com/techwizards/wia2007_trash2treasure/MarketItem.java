package com.techwizards.wia2007_trash2treasure;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MarketItem {
    UUID uuid = UUID.randomUUID();
    String image;
    String name;
    double price;
    String detail;
    String description;
    int stock;
    String email;

    public MarketItem() {} //to prevent error fetching data from firebase

    public MarketItem(String image, String name, double price, String detail, String description, int stock, String email) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.detail = detail;
        this.description = description;
        this.stock = stock;
        this.email = email;
    }

    public Map<String, Object> toMap() {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        System.out.println(json);
        Type type = new TypeToken<HashMap<String, Object>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDetail() {
        return detail;
    }

    public String getDescription() {
        return description;
    }

    public int getStock() {
        return stock;
    }

    public String getEmail() {
        return email;
    }
}
