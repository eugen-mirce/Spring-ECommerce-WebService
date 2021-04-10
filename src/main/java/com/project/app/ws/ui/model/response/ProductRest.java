package com.project.app.ws.ui.model.response;

import java.util.Collection;
import java.util.Set;

public class ProductRest {
    private long id;
    private String name;
    private String description;
    private String pictureUrl;
    private Double price;
    private boolean available;
    private boolean promoted;
    private long categoryId;
    private Set<ProductRest> items;

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
    public String getPictureUrl() {
        return pictureUrl;
    }
    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
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
    public long getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }
    public Set<ProductRest> getItems() {
        return items;
    }
    public void setItems(Set<ProductRest> items) {
        this.items = items;
    }
}
