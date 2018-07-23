package com.jones.mars.model;

import lombok.Data;

@Data
public class SocketMessage {
    private Object message;
    private String date;

    public SocketMessage(Object message, String date) {
        this.message = message;
        this.date = date;
    }

}
