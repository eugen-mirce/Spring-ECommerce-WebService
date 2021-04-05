package com.project.app.ws.service;

import com.project.app.ws.shared.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    ProductDTO createProduct(ProductDTO productDTO);
    ProductDTO getProduct(long productId);
    List<ProductDTO> getProducts(int page, int limit);
//    List<ProductDTO> getProducts(long categoryId,int page, int limit);
    List<ProductDTO> getProductsByCategory(long categoryId, int page, int limit);
}
