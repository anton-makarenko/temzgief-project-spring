package com.shop.service;

import com.shop.entity.Category;
import com.shop.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class CategoryService {
    CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Flux<Category> getAllRoot() {
        return categoryRepository.findAllByParentCategoryIsNull();
    }

    public Flux<Category> getAllByParent(String name) {
        return categoryRepository.findAllByParentCategoryName(name);
    }
}
