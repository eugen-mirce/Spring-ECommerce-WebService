package com.project.app.ws.ui.model.response;

import java.util.Date;

public class OrderRest {
    private long id;
    private String orderId;
    private int quantity;
    private Date date;
    private boolean shipped;
    private boolean completed;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public boolean isShipped() {
        return shipped;
    }
    public void setShipped(boolean shipped) {
        this.shipped = shipped;
    }
    public boolean isCompleted() {
        return completed;
    }
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
