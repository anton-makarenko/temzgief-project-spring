package com.shop.service;

import com.shop.entity.Category;
import com.shop.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getAllRoot() {
        return categoryRepository.findAllByParentCategoryIsNull();
    }

    public List<Category> getAllByParent(String name) {
        return categoryRepository.findAllByParentCategoryName(name);
    }
}
