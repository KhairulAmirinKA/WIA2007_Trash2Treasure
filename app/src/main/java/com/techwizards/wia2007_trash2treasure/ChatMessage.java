package com.techwizards.wia2007_trash2treasure;

public class ChatMessage {

    private String userName;
    private String messageContent;
    private String timestamp;

    public ChatMessage(String userName, String messageContent) {
        this.userName = userName;
        this.messageContent = messageContent;
        this.timestamp = timestamp;
    }

    public String getUserName() {
        return userName;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
