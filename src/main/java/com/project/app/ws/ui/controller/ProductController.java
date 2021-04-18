package com.project.app.ws.ui.controller;

import com.project.app.ws.service.ProductService;
import com.project.app.ws.shared.dto.ProductDTO;
import com.project.app.ws.ui.model.request.ProductRequestModel;
import com.project.app.ws.ui.model.response.OperationStatusModel;
import com.project.app.ws.ui.model.response.ProductRest;
import com.project.app.ws.ui.model.response.RequestOperationStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("products")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    ModelMapper modelMapper;

    /**
     * [GET] Get Product Details
     * [Path] http://localhost:8080/app/product/{productId}
     * No Role Needed
     * @param productId
     * @return
     */
    @GetMapping(
            path = {"/{productId}", "/{productId}/"},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ProductRest getProduct(@PathVariable Long productId) {

        ProductDTO productDTO = productService.getProduct(productId);
        ProductRest returnValue = modelMapper.map(productDTO,ProductRest.class);
        returnValue.setCategoryId(productDTO.getCategoryEntity().getId());
        return returnValue;
    }

    /**
     * [POST] Create Product
     * [Path] http://localhost:8080/app/product
     * //TODO Add Admin Access Only
     * @param productRequestModel
     * @return
     */
    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ProductRest createProduct(@RequestBody ProductRequestModel productRequestModel) {
        ProductDTO productDTO = modelMapper.map(productRequestModel,ProductDTO.class);
        ProductDTO savedProduct = productService.createProduct(productDTO);

        ProductRest returnValue = modelMapper.map(savedProduct,ProductRest.class);
        returnValue.setCategoryId(savedProduct.getCategoryEntity().getId());
        return returnValue;
    }

    /**
     * [PUT] Update Product Details
     * [Path] http://localhost:8080/app/product/{productId}
     * //TODO Add Admin Role Only
     * @param productId
     * @param productRequestModel
     * @return
     */
    @PutMapping(
            path = { "/{productId}", "/{productId}"},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ProductRest updateProduct(
            @PathVariable long productId,
            @RequestBody ProductRequestModel productRequestModel
    ) {
        ProductDTO productDTO = modelMapper.map(productRequestModel,ProductDTO.class);
        ProductDTO product = productService.updateProduct(productId,productDTO);

        return modelMapper.map(product,ProductRest.class);
    }

    /**
     * [DELETE] Delete Product
     * [Path] http://localhost:8080/app/product/{productId}
     * //TODO Add Admin Role Only
     * @param productId
     * @return
     */
    public OperationStatusModel deleteProduct(@PathVariable long productId) {
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name());
        productService.deleteProduct(productId);
        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        return returnValue;
    }

    /**
     * [GET] Get Promoted Products
     * [Path] http://localhost:8080/app/products
     * No Role Needed
     * @param page
     * @param limit
     * @return
     */
    @GetMapping(path = {"/promoted", "/promoted/"},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public List<ProductRest> getPromotedProducts(
            @RequestParam(value="page", defaultValue = "1") int page,
            @RequestParam(value="limit", defaultValue = "20") int limit)
    {
        List<ProductRest> returnValue = new ArrayList<>();
        List<ProductDTO> products = productService.getPromotedProducts(page,limit);

        for(ProductDTO productDTO: products) {
            ProductRest productRest = modelMapper.map(productDTO,ProductRest.class);
            productRest.setCategoryId(productDTO.getCategoryEntity().getId());
            returnValue.add(productRest);
        }
        return returnValue;
    }

    /**
     * [GET] Get All Products
     * [Path] http://localhost:8080/app/products
     * No Role Needed
     * @param page
     * @param limit
     * @return
     */
    @GetMapping(
            path = { "", "/"},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public List<ProductRest> getProducts(
            @RequestParam(value="page", defaultValue = "1") int page,
            @RequestParam(value="limit", defaultValue = "20") int limit)
    {
        List<ProductRest> returnValue = new ArrayList<>();
        List<ProductDTO> products = productService.getProducts(page,limit);

        for(ProductDTO productDTO: products) {
            ProductRest productRest = modelMapper.map(productDTO,ProductRest.class);
            productRest.setCategoryId(productDTO.getCategoryEntity().getId());
            returnValue.add(productRest);
        }
        return returnValue;
    }
}