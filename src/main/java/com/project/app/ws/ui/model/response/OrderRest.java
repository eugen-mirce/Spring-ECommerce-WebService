package com.project.app.ws.ui.model.response;

public class OrderRest {
    private long id;
    private ProductRest productDetails;
    private int quantity;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ProductRest getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(ProductRest productDetails) {
        this.productDetails = productDetails;
    }
}
