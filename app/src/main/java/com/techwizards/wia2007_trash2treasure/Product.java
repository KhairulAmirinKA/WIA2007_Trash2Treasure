package com.techwizards.wia2007_trash2treasure;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class Product {
    String productName;
    String productPrice;
    String productDetails;
    String productQuantity;
    String productDescription;
    String email;

    public Product(String productName, String productPrice, String productDetails, String productQuantity, String productDescription, String email) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDetails = productDetails;
        this.productQuantity = productQuantity;
        this.productDescription = productDescription;
        this.email = email;
    }
    public Map<String, Object> toMap() {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        System.out.println(json);
        Type type = new TypeToken<HashMap<String, Object>>() {}.getType();
        return gson.fromJson(json, type);
    }
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(String productDetails) {
        this.productDetails = productDetails;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
