package com.project.app.ws.ui.controller;

import com.project.app.ws.service.CategoryService;
import com.project.app.ws.service.ProductService;
import com.project.app.ws.shared.dto.CategoryDTO;
import com.project.app.ws.shared.dto.ProductDTO;
import com.project.app.ws.ui.model.request.CategoryRequestModel;
import com.project.app.ws.ui.model.response.CategoryRest;
import com.project.app.ws.ui.model.response.OperationStatusModel;
import com.project.app.ws.ui.model.response.ProductRest;
import com.project.app.ws.ui.model.response.RequestOperationStatus;
import org.dom4j.rule.Mode;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @Autowired
    ModelMapper modelMapper;

    /**
     * [POST] Create Category
     * [Path] http://localhost:8080/app/category
     * //TODO Admin Access Only
     * @param categoryRequestModel
     * @return
     */
    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public CategoryRest createCategory(@RequestBody CategoryRequestModel categoryRequestModel) {

        CategoryDTO categoryDTO = modelMapper.map(categoryRequestModel,CategoryDTO.class);
        CategoryDTO savedCategory = categoryService.createCategory(categoryDTO);

        return modelMapper.map(savedCategory,CategoryRest.class);
    }

    /**
     * [GET] Get Categories
     * [Path] http://localhost:8080/app/category
     * No Access Needed
     * @return
     */
    @GetMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public List<CategoryRest> getCategories() {
        List<CategoryRest> returnValue = new ArrayList<>();
        List<CategoryDTO> categories = categoryService.getCategories();

        for(CategoryDTO categoryDTO : categories) {
            CategoryRest categoryRest = modelMapper.map(categoryDTO,CategoryRest.class);
            returnValue.add(categoryRest);
        }

        return returnValue;
    }

    /**
     * [GET] Get Products From A Category
     * [Path] http://localhost:8080/app/category/{categoryId}/products
     * @param categoryId
     * @param page
     * @param limit
     * @return
     */
    @GetMapping(
            path = {"/{categoryId}/products","/{categoryId}/products/"},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public List<ProductRest> getProductsByCategory(
            @PathVariable long categoryId,
            @RequestParam(value="page", defaultValue = "1") int page,
            @RequestParam(value="limit", defaultValue = "20") int limit
    ) {
        List<ProductRest> returnValue = new ArrayList<>();
        List<ProductDTO> products = productService.getProductsByCategory(categoryId,page,limit);

        for(ProductDTO productDTO: products) {
            ProductRest productRest = modelMapper.map(productDTO,ProductRest.class);
            returnValue.add(productRest);
        }
        return returnValue;
    }

    /**
     * [PUT] Update Category
     * [Path] http://localhost:8080/app/category/{categoryId}
     * //TODO Admin Access Only
     * @param categoryId
     * @param categoryRequestModel
     * @return
     */
    @PutMapping(
            path = { "/{categoryId}", "/{categoryId}/" },
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public CategoryRest updateCategory(@PathVariable long categoryId, @RequestBody CategoryRequestModel categoryRequestModel
    ) {
        CategoryDTO categoryDTO = modelMapper.map(categoryRequestModel,CategoryDTO.class);
        CategoryDTO savedChanges = categoryService.updateCategory(categoryId,categoryDTO);

        return modelMapper.map(savedChanges,CategoryRest.class);
    }

    /**
     * [DELETE] Delete Category
     * [Path] http://localhost:8080/app/category/{categoryId}
     * //TODO Admin Access Only
     * @param categoryId
     * @return
     */
    @DeleteMapping(
            path = { "/{categoryId}", "/{categoryId}/" },
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public OperationStatusModel deleteCategory(@PathVariable long categoryId) {
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name());
        categoryService.deleteUser(categoryId);
        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());

        return returnValue;
    }
}
