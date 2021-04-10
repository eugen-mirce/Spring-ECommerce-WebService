package com.project.app.ws.service.Impl;

import com.project.app.ws.exceptions.CategoryServiceException;
import com.project.app.ws.exceptions.UserServiceException;
import com.project.app.ws.io.entity.CategoryEntity;
import com.project.app.ws.io.entity.UserEntity;
import com.project.app.ws.io.repositories.CategoryRepository;
import com.project.app.ws.service.CategoryService;
import com.project.app.ws.shared.dto.CategoryDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<CategoryDTO> getCategories() {

        List<CategoryDTO> returnValue = new ArrayList<>();
        Iterable<CategoryEntity> categories = categoryRepository.findAll();

        for(CategoryEntity categoryEntity: categories) {
            returnValue.add(modelMapper.map(categoryEntity,CategoryDTO.class));
        }
        return returnValue;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        if(categoryRepository.findByName(categoryDTO.getName()) != null)
            throw new CategoryServiceException("Category Already Exists");

        CategoryEntity categoryEntity = modelMapper.map(categoryDTO,CategoryEntity.class);

        CategoryEntity savedDetails = categoryRepository.save(categoryEntity);

        return modelMapper.map(savedDetails,CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(long categoryId, CategoryDTO categoryDTO) {
        CategoryEntity categoryEntity = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new CategoryServiceException("Category Not Found Exception"));

        categoryEntity.setName(categoryDTO.getName());
        CategoryEntity updatedCategory = categoryRepository.save(categoryEntity);

        return modelMapper.map(updatedCategory,CategoryDTO.class);

    }

    @Override
    public void deleteUser(long categoryId) {

        CategoryEntity categoryEntity = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new CategoryServiceException("Category Not Found Exception"));

        categoryRepository.delete(categoryEntity);
    }
}
