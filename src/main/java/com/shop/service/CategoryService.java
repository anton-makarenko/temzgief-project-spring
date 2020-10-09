package com.shop.service;

import com.shop.entity.Category;
import com.shop.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllRoot() {
        return categoryRepository.findAllByParentCategoryIsNull();
    }

    public List<Category> getAllByParent(String name) {
        return categoryRepository.findAllByParentCategoryName(name);
    }
}
