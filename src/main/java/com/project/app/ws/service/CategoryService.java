package com.project.app.ws.service;

import com.project.app.ws.shared.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getCategories();
    CategoryDTO createCategory(CategoryDTO categoryDTO);
}
