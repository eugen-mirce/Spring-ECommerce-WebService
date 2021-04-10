package com.project.app.ws.shared.dto;

import com.project.app.ws.io.entity.CategoryEntity;
import com.project.app.ws.io.entity.InvoiceEntity;

import java.io.Serializable;
import java.util.Date;

public class OrderDTO implements Serializable {
    private static final long serialVersionId = 1L;

    private long id;
    private ProductDTO productDetails;
    private Long productId;
    private int quantity;
    private InvoiceDTO invoiceDetails;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public ProductDTO getProductDetails() {
        return productDetails;
    }
    public void setProductDetails(ProductDTO productDetails) {
        this.productDetails = productDetails;
    }
    public Long getProductId() {
        return productId;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public InvoiceDTO getInvoiceDetails() {
        return invoiceDetails;
    }
    public void setInvoiceDetails(InvoiceDTO invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }
}
