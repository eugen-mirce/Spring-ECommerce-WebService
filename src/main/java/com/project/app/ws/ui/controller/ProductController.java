package com.project.app.ws.ui.controller;

import com.project.app.ws.service.ProductService;
import com.project.app.ws.shared.dto.ProductDTO;
import com.project.app.ws.ui.model.request.ProductRequestModel;
import com.project.app.ws.ui.model.response.ProductRest;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public List<ProductRest> getProducts(
            @RequestParam(value="page", defaultValue = "1") int page,
            @RequestParam(value="limit", defaultValue = "20") int limit
    ) {
        List<ProductRest> returnValue = new ArrayList<>();
        List<ProductDTO> products = productService.getProducts(page,limit);

        for(ProductDTO productDTO: products) {
            ProductRest productRest = modelMapper.map(productDTO,ProductRest.class);
            returnValue.add(productRest);
        }
        return returnValue;
    }
    @GetMapping(
            path = {"/category/{categoryId}","/category/{categoryId}/"},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public List<ProductRest> getProductsByCategory(
            @PathVariable long categoryId,
            @RequestParam(value="page", defaultValue = "1") int page,
            @RequestParam(value="limit", defaultValue = "20") int limit
    ) {
        List<ProductRest> returnValue = new ArrayList<>();
        List<ProductDTO> products = productService.getProducts(categoryId,page,limit);

        for(ProductDTO productDTO: products) {
            ProductRest productRest = modelMapper.map(productDTO,ProductRest.class);
            returnValue.add(productRest);
        }
        return returnValue;
    }
    @GetMapping(
            path = {"/{productId}", "/{productId}/"},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ProductRest getProduct(@PathVariable Long productId) {

        ProductDTO productDTO = productService.getProduct(productId);

        return modelMapper.map(productDTO,ProductRest.class);
    }
    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ProductRest createProduct(@RequestBody ProductRequestModel productRequestModel) {

        ProductDTO productDTO = modelMapper.map(productRequestModel,ProductDTO.class);
        ProductDTO savedProduct = productService.createProduct(productDTO);
        return modelMapper.map(savedProduct,ProductRest.class);

    }
}
