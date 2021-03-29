package com.project.app.ws.shared.dto;

import com.project.app.ws.io.entity.CategoryEntity;

import java.io.Serializable;
import java.util.Date;

public class OrderDTO implements Serializable {
    private static final long serialVersionId = 1L;

    private long id;
    private String orderId;
    private UserDto userDetails;
    private String userId;
    private ProductDTO productDetails;
    private Long productId;
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
    public UserDto getUserDetails() {
        return userDetails;
    }
    public void setUserDetails(UserDto userDetails) {
        this.userDetails = userDetails;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public ProductDTO getProductDetails() {
        return productDetails;
    }
    public void setProductDetails(ProductDTO productDetails) {
        this.productDetails = productDetails;
    }
    public Long getProductId() {
        return productId;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
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
