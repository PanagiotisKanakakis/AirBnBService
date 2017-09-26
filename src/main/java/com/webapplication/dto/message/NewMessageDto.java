package com.webapplication.dto.message;


import java.util.Date;

/**
 * Created by panagiotis on 7/8/2017.
 */
public class NewMessageDto {

    private String fromUser;

    private String toUser;

    private String messageText;

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
}
