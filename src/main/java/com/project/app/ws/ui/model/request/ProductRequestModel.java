package com.project.app.ws.ui.model.request;

import com.project.app.ws.shared.dto.ItemIds;

import java.util.List;

public class ProductRequestModel {
    private String name;
    private String description;
    private String pictureUrl;
    private Double price;
    private long categoryId;
    private List<ItemIds> items;

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
    public long getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }
    public List<ItemIds> getItems() {
        return items;
    }
    public void setItems(List<ItemIds> items) {
        this.items = items;
    }
}
