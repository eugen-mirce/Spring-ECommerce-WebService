package com.project.app.ws.shared.dto;

import com.project.app.ws.io.entity.CategoryEntity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public class ProductDTO implements Serializable {
    private static final long serialVersionId = 1L;

    private long id;
    private String productId;
    private String name;
    private String description;
    private String pictureUrl;
    private Double price;
    private boolean available;
    private CategoryEntity categoryEntity;
    private long categoryId;
    private Collection<ProductDTO> items;
    private List<ItemIds> itemIds;

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
    public CategoryEntity getCategoryEntity() {
        return categoryEntity;
    }
    public void setCategoryEntity(CategoryEntity categoryEntity) {
        this.categoryEntity = categoryEntity;
    }
    public long getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }
    public Collection<ProductDTO> getItems() {
        return items;
    }
    public void setItems(Collection<ProductDTO> items) {
        this.items = items;
    }
    public List<ItemIds> getItemIds() {
        return itemIds;
    }
    public void setItemIds(List<ItemIds> itemIds) {
        this.itemIds = itemIds;
    }
}
