package com.project.app.ws.io.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name="products")
public class ProductEntity implements Serializable {
    private static final long serialVersionID = 1L;

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String productId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private float price;

    @Column(nullable = false)
    private boolean available;

    @ManyToOne
    @JoinColumn(name="category_id")
    private CategoryEntity categoryId;
}
