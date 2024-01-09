package com.techwizards.wia2007_trash2treasure;

import java.util.List;

public class RecyIPItem {
    String date;
    List<String> type;

    public RecyIPItem(String date, List<String> type) {
        this.date = date;
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public List<String> getType() {
        return type;
    }
}
