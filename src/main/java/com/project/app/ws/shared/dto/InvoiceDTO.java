package com.project.app.ws.shared.dto;

import org.hibernate.criterion.Order;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class InvoiceDTO implements Serializable {
    private static final long serialVersionId = 1L;

    private long id;
    private UserDto userDetails;
    private String userId;
    private List<OrderDTO> orders;
    private AddressDTO addressDetails;
    private String addressId;
    private Date date;
    private boolean shipped;
    private Double total;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
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
    public List<OrderDTO> getOrders() {
        return orders;
    }
    public void setOrders(List<OrderDTO> orders) {
        this.orders = orders;
    }
    public AddressDTO getAddressDetails() {
        return addressDetails;
    }
    public void setAddressDetails(AddressDTO addressDetails) {
        this.addressDetails = addressDetails;
    }
    public String getAddressId() {
        return addressId;
    }
    public void setAddressId(String addressId) {
        this.addressId = addressId;
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
    public Double getTotal() {
        return total;
    }
    public void setTotal(Double total) {
        this.total = total;
    }
}
