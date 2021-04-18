package com.project.app.ws.service;

import com.project.app.ws.shared.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    ProductDTO createProduct(ProductDTO productDTO);
    ProductDTO getProduct(long productId);
    ProductDTO updateProduct(long productId, ProductDTO productDTO);
    void deleteProduct(long productId);
    List<ProductDTO> getProducts(int page, int limit);
    List<ProductDTO> getProductsByCategory(long categoryId, int page, int limit);
    List<ProductDTO> getPromotedProducts(int page, int limit);
    Double getPrice(long id);

}
