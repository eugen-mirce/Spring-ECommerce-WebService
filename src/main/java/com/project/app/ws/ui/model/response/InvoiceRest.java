package com.project.app.ws.ui.model.response;

import com.project.app.ws.shared.dto.AddressDTO;
import com.project.app.ws.shared.dto.OrderDTO;

import java.util.Date;
import java.util.List;

public class InvoiceRest {

    private long id;
    private List<OrderRest> orders;
    private AddressesRest addressDetails;
    private Date date;
    private Double total;
    private boolean shipped;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public List<OrderRest> getOrders() {
        return orders;
    }
    public void setOrders(List<OrderRest> orders) {
        this.orders = orders;
    }
    public AddressesRest getAddressDetails() {
        return addressDetails;
    }
    public void setAddressDetails(AddressesRest addressDetails) {
        this.addressDetails = addressDetails;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public Double getTotal() {
        return total;
    }
    public void setTotal(Double total) {
        this.total = total;
    }
    public boolean isShipped() {
        return shipped;
    }
    public void setShipped(boolean shipped) {
        this.shipped = shipped;
    }
}
