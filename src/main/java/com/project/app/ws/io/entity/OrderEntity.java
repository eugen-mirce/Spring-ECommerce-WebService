package com.project.app.ws.io.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name="orders")
public class OrderEntity implements Serializable {
    private static final long serialVersionID = 1L;

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String orderId;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne
    @JoinColumn(name="users_id")
    private UserEntity userDetails;

    @ManyToOne
    @JoinColumn(name="product_id")
    private ProductEntity productDetails;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private boolean completed;
}
