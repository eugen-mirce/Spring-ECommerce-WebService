package com.project.app.ws.service.Impl;

import com.project.app.ws.exceptions.ProductServiceException;
import com.project.app.ws.exceptions.UserServiceException;
import com.project.app.ws.io.entity.CategoryEntity;
import com.project.app.ws.io.entity.ProductEntity;
import com.project.app.ws.io.repositories.CategoryRepository;
import com.project.app.ws.io.repositories.ProductRepository;
import com.project.app.ws.service.CategoryService;
import com.project.app.ws.service.ProductService;
import com.project.app.ws.shared.Utils;
import com.project.app.ws.shared.dto.ProductDTO;
import org.dom4j.rule.Mode;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        productEntity.setProductId(utils.generateProductId(30));
        productEntity.setCategoryEntity(categoryEntity);
        productEntity.setAvailable(Boolean.TRUE);
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
        if(page > 0) page++;
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
    public List<ProductDTO> getProducts(long categoryId, int page, int limit) {
        if(page > 0) page++;

        List<ProductDTO> returnValue = new ArrayList<>();
        PageRequest pageableRequest = PageRequest.of(page,limit);
        Page<ProductEntity> productsPage = productRepository.findAllByCategoryEntity(categoryId,pageableRequest);
        List<ProductEntity> products = productsPage.getContent();

        for(ProductEntity productEntity : products) {
            ProductDTO productDTO = modelMapper.map(productEntity,ProductDTO.class);
            returnValue.add(productDTO);
        }

        return returnValue;
    }
}
