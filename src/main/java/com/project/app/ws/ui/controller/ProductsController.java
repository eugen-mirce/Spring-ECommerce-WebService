package com.project.app.ws.ui.controller;

import com.project.app.ws.service.ProductService;
import com.project.app.ws.shared.dto.ProductDTO;
import com.project.app.ws.ui.model.response.ProductRest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

//@RestController
//@RequestMapping("products")
//public class ProductsController {
//    @Autowired
//    ProductService productService;
//
//    @Autowired
//    ModelMapper modelMapper;
//
//    /**
//     * [GET] Get All Products
//     * [Path] http://localhost:8080/app/products
//     * No Role Needed
//     * @param page
//     * @param limit
//     * @return
//     */
//    @GetMapping(
//            path = { "", "/"},
//            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
//    )
//    public List<ProductRest> getProducts(
//            @RequestParam(value="page", defaultValue = "1") int page,
//            @RequestParam(value="limit", defaultValue = "20") int limit
//    ) {
//        List<ProductRest> returnValue = new ArrayList<>();
//        List<ProductDTO> products = productService.getProducts(page,limit);
//
//        for(ProductDTO productDTO: products) {
//            ProductRest productRest = modelMapper.map(productDTO,ProductRest.class);
//            returnValue.add(productRest);
//        }
//        return returnValue;
//    }
//
//    /**
//     * [GET] Get Promoted Products
//     * [Path] http://localhost:8080/app/products
//     * No Role Needed
//     * @param page
//     * @param limit
//     * @return
//     */
//    @GetMapping(path = {"/promoted", "/promoted/"},
//            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
//    )
//    public List<ProductRest> getPromotedProducts(
//            @RequestParam(value="page", defaultValue = "1") int page,
//            @RequestParam(value="limit", defaultValue = "20") int limit
//    ) {
//        List<ProductRest> returnValue = new ArrayList<>();
//        List<ProductDTO> products = productService.getPromotedProducts(page,limit);
//
//        for(ProductDTO productDTO: products) {
//            ProductRest productRest = modelMapper.map(productDTO,ProductRest.class);
//            returnValue.add(productRest);
//        }
//        return returnValue;
//    }
//}
