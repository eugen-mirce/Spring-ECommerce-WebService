package com.project.app.ws.io.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name="orders")
public class OrderEntity implements Serializable {
    private static final long serialVersionID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 30)
    private String orderId;

    @Column(nullable = false, length = 2)
    private int quantity;

    @ManyToOne
    @JoinColumn(name="users_id")
    private UserEntity userDetails;

    @ManyToOne
    @JoinColumn(name="products_id")
    private ProductEntity productDetails;

    @ManyToOne
    @JoinColumn(name="address_id")
    private AddressEntity addressDetails;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = true)
    private boolean shipped;

    @Column(nullable = false)
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
    public UserEntity getUserDetails() {
        return userDetails;
    }
    public void setUserDetails(UserEntity userDetails) {
        this.userDetails = userDetails;
    }
    public ProductEntity getProductDetails() {
        return productDetails;
    }
    public void setProductDetails(ProductEntity productDetails) {
        this.productDetails = productDetails;
    }
    public AddressEntity getAddressDetails() {
        return addressDetails;
    }
    public void setAddressDetails(AddressEntity addressDetails) {
        this.addressDetails = addressDetails;
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
