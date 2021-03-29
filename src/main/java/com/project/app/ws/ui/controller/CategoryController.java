package com.project.app.ws.ui.controller;

import com.project.app.ws.service.CategoryService;
import com.project.app.ws.shared.dto.CategoryDTO;
import com.project.app.ws.ui.model.request.CategoryRequestModel;
import com.project.app.ws.ui.model.response.CategoryRest;
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
    ModelMapper modelMapper;

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public CategoryRest createCategory(@RequestBody CategoryRequestModel categoryRequestModel) {

        CategoryDTO categoryDTO = modelMapper.map(categoryRequestModel,CategoryDTO.class);
        CategoryDTO savedCategory = categoryService.createCategory(categoryDTO);

        return modelMapper.map(savedCategory,CategoryRest.class);
    }

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
}
