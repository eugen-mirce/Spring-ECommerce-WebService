package com.project.app.ws.io.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity(name="invoices")
public class InvoiceEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "invoiceDetails", cascade = CascadeType.ALL)
    private List<OrderEntity> orders;

    @ManyToOne
    @JoinColumn(name="users_id")
    private UserEntity userDetails;

    @ManyToOne
    @JoinColumn(name="address_id")
    private AddressEntity addressDetails;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private boolean shipped;

    @Column(nullable = false)
    private Double total;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public List<OrderEntity> getOrders() {
        return orders;
    }
    public void setOrders(List<OrderEntity> orders) {
        this.orders = orders;
    }
    public UserEntity getUserDetails() {
        return userDetails;
    }
    public void setUserDetails(UserEntity userDetails) {
        this.userDetails = userDetails;
    }
    public AddressEntity getAddressDetails() {
        return addressDetails;
    }
    public void setAddressDetails(AddressEntity address) {
        this.addressDetails = address;
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
