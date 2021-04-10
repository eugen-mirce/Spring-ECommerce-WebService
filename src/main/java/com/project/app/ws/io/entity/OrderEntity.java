package com.project.app.ws.io.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name="orders")
public class OrderEntity implements Serializable {
    private static final long serialVersionID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name="products_id")
    private ProductEntity productDetails;

    @Column(nullable = false, length = 2)
    private int quantity;

    @ManyToOne
    @JoinColumn(name="invoice_id")
    private InvoiceEntity invoiceDetails;

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
    public ProductEntity getProductDetails() {
        return productDetails;
    }
    public void setProductDetails(ProductEntity productDetails) {
        this.productDetails = productDetails;
    }
    public InvoiceEntity getInvoiceDetails() {
        return invoiceDetails;
    }
    public void setInvoiceDetails(InvoiceEntity invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }
}
