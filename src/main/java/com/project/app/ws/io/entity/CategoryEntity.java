package com.project.app.ws.io.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity(name="category")
public class CategoryEntity implements Serializable {
    private static final long serialVersionID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false,length = 50)
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "categoryEntity", cascade = CascadeType.ALL)
    private Set<ProductEntity> products = new HashSet<>();

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Set<ProductEntity> getProducts() {
        return products;
    }
    public void setProducts(Set<ProductEntity> products) {
        this.products = products;
    }
    public void addProduct(ProductEntity product) {
        product.setCategoryEntity(this);
        products.add(product);
    }
}
