package com.techwizards.wia2007_trash2treasure;

public class NotificationsItem {
    String type;
    String time;
    String date;
    String body;
    boolean isRead;

    public NotificationsItem(String type, String time, String date, String body) {
        this.type = type;
        this.time = time;
        this.date = date;
        this.body = body;
        this.isRead = false;
    }

    public String getType() {
        return type;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getBody() {
        return body;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
