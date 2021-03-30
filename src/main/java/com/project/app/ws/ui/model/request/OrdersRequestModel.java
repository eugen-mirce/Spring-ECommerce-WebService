package com.project.app.ws.ui.model.request;

import java.util.List;

public class OrdersRequestModel {
    private String userId;
    private List<OrderRequestModel> orders;

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public List<OrderRequestModel> getOrders() {
        return orders;
    }
    public void setOrders(List<OrderRequestModel> orders) {
        this.orders = orders;
    }
}
