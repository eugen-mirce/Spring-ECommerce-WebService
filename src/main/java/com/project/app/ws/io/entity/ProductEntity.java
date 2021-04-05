package com.project.app.ws.io.entity;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name="products")
public class ProductEntity implements Serializable {
    private static final long serialVersionID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ElementCollection
    private List<ProductEntity> items;

    public List<ProductEntity> getItems() {
        return items;
    }

    public void setItems(List<ProductEntity> items) {
        this.items = items;
    }

    @Column(nullable = false, length = 30)
    private String productId;

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

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
//    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name="category_id", referencedColumnName="id", unique = false)
    private CategoryEntity categoryEntity;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
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

}
