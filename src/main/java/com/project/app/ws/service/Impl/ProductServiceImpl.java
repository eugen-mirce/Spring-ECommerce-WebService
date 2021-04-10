package com.project.app.ws.service.Impl;

import com.project.app.ws.exceptions.CategoryServiceException;
import com.project.app.ws.exceptions.ProductServiceException;
import com.project.app.ws.exceptions.UserServiceException;
import com.project.app.ws.io.entity.CategoryEntity;
import com.project.app.ws.io.entity.ProductEntity;
import com.project.app.ws.io.repositories.CategoryRepository;
import com.project.app.ws.io.repositories.ProductRepository;
import com.project.app.ws.service.CategoryService;
import com.project.app.ws.service.ProductService;
import com.project.app.ws.shared.Utils;
import com.project.app.ws.shared.dto.ItemIds;
import com.project.app.ws.shared.dto.ProductDTO;
import org.dom4j.rule.Mode;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    Utils utils;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        CategoryEntity categoryEntity = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new ProductServiceException("Category Not Found Exception"));

        ProductEntity productEntity = modelMapper.map(productDTO,ProductEntity.class);
        productEntity.setCategoryEntity(categoryEntity);
        productEntity.setAvailable(Boolean.TRUE);

        // Is Set?
        if(productDTO.getItemIds() != null && productDTO.getItemIds().size()>0) {
            Set<ProductEntity> items = new HashSet<>();
            for(ItemIds itemId: productDTO.getItemIds()) {
                ProductEntity productItem = productRepository.findById(itemId.getId())
                        .orElseThrow(() -> new ProductServiceException("Product Not Found Exception"));
                items.add(productItem);
            }
            productEntity.setItems(items);
        }
        ProductEntity savedProduct = productRepository.save(productEntity);

        return modelMapper.map(savedProduct,ProductDTO.class);
    }

    @Override
    public ProductDTO getProduct(long productId) {

        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductServiceException("Product Not Found"));

        return modelMapper.map(product,ProductDTO.class);
    }

    @Override
    public List<ProductDTO> getProducts(int page, int limit) {
        if(page > 0) page--;
        List<ProductDTO> returnValue = new ArrayList<>();

        PageRequest pageableRequest = PageRequest.of(page,limit);
        Page<ProductEntity> productsPage = productRepository.findAll(pageableRequest);
        List<ProductEntity> products = productsPage.getContent();

        for(ProductEntity productEntity : products) {
            ProductDTO productDTO = modelMapper.map(productEntity,ProductDTO.class);
            returnValue.add(productDTO);
        }

        return returnValue;
    }

    @Override
    public List<ProductDTO> getProductsByCategory(long categoryId, int page, int limit) {
        if(page > 0) page--;

        CategoryEntity categoryEntity = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryServiceException("Category Not Found Exception."));

        List<ProductDTO> returnValue = new ArrayList<>();
        Pageable pageableRequest = PageRequest.of(page,limit);
        Page<ProductEntity> productsPage = productRepository.findAllByCategoryEntity(categoryEntity,pageableRequest);
        List<ProductEntity> products = productsPage.getContent();

        for(ProductEntity productEntity : products) {
            ProductDTO productDTO = modelMapper.map(productEntity,ProductDTO.class);
            returnValue.add(productDTO);
        }

        return returnValue;
    }

    @Override
    public List<ProductDTO> getPromotedProducts(int page, int limit) {
        if(page > 0) page--;
        List<ProductDTO> returnValue = new ArrayList<>();

        Pageable pageableRequest = PageRequest.of(page,limit);
        Page<ProductEntity> productsPage = productRepository.findAllByPromotedTrue(pageableRequest);
        List<ProductEntity> products = productsPage.getContent();

        for(ProductEntity productEntity : products) {
            ProductDTO productDTO = modelMapper.map(productEntity,ProductDTO.class);
            returnValue.add(productDTO);
        }

        return returnValue;
    }

    @Override
    public Double getPrice(long id) {
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(()-> new ProductServiceException("Product Not Found Exception"));

        return productEntity.getPrice();
    }
}
