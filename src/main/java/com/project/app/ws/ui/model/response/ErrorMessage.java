package com.project.app.ws.ui.model.response;

import java.util.Date;

public class ErrorMessage {
    private int status;
    private Date date;
    private String message;

    public ErrorMessage(int status, Date date, String message) {
        this.status = status;
        this.date = date;
        this.message = message;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date timestamp) {
        this.date = timestamp;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
