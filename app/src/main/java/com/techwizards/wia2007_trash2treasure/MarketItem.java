package com.techwizards.wia2007_trash2treasure;

public class MarketItem {
    String image;
    String name;
    String description;
    int stock;

    public MarketItem(String image, String name, String description, int stock) {
        this.image = image;
        this.name = name;
        this.description = description;
        this.stock = stock;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getStock() {
        return stock;
    }
}
