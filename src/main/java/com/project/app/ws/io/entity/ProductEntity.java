package com.project.app.ws.io.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity(name="products")
public class ProductEntity implements Serializable {
    private static final long serialVersionID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = true, length = 200)
    private String pictureUrl;

    @Column(nullable = false)
    private boolean available;

    @Column(nullable = false)
    private boolean promoted;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name="category_id", referencedColumnName="id", unique = false)
    private CategoryEntity categoryEntity;

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable( name="set_products",
                joinColumns = @JoinColumn(name = "sets_id", referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name = "items_id", referencedColumnName = "id")
    )
    private Set<ProductEntity> items;

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
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public boolean isAvailable() {
        return available;
    }
    public void setAvailable(boolean available) {
        this.available = available;
    }
    public boolean isPromoted() {
        return promoted;
    }
    public void setPromoted(boolean promoted) {
        this.promoted = promoted;
    }
    public CategoryEntity getCategoryEntity() {
        return categoryEntity;
    }
    public void setCategoryEntity(CategoryEntity categoryEntity) {
        this.categoryEntity = categoryEntity;
    }
    public String getPictureUrl() {
        return pictureUrl;
    }
    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
    public Set<ProductEntity> getItems() {
        return items;
    }
    public void setItems(Set<ProductEntity> items) {
        this.items = items;
    }
}
