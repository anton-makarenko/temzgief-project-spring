package com.shop.service;

import com.shop.config.constant.Constants;
import com.shop.entity.Category;
import com.shop.entity.Clothes;
import com.shop.enumeration.Color;
import com.shop.repository.CategoryRepository;
import com.shop.repository.ClothesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.shop.repository.specification.ClothesSpecification.*;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ClothesRepository clothesRepository;
    private final CategoryRepository categoryRepository;

    public Page<Clothes> getClothesPage(String categoryName, String sortField, boolean descending, int page) {
        return descending
                ? clothesRepository.findAllByCategoryName(categoryName, PageRequest.of(page, Constants.PRODUCTS_PER_PAGE, Sort.by(sortField).descending()))
                : clothesRepository.findAllByCategoryName(categoryName, PageRequest.of(page, Constants.PRODUCTS_PER_PAGE, Sort.by(sortField).ascending()));
    }

    public Page<Clothes> getClothesPage(String categoryName, double min, double max, String sortField, boolean descending, int page, Color... colors) {
        if (colors == null || colors.length == 0 || colors.length == Color.values().length)
            return descending
                    ? clothesRepository.findAll(PageRequest.of(page, Constants.PRODUCTS_PER_PAGE, Sort.by(sortField).descending()))
                    : clothesRepository.findAll(PageRequest.of(page, Constants.PRODUCTS_PER_PAGE, Sort.by(sortField).ascending()));
        Specification<Clothes> haveColors = hasColor(colors[0]);
        for (int i = 1; i < colors.length; i++)
            haveColors = haveColors.or(hasColor(colors[i]));
        Specification<Clothes> specification = inCategory(categoryName).and(priceBetween(min, max)).and(haveColors);
        return descending
                ? clothesRepository.findAll(specification, PageRequest.of(page, Constants.PRODUCTS_PER_PAGE, Sort.by(sortField).descending()))
                : clothesRepository.findAll(specification, PageRequest.of(page, Constants.PRODUCTS_PER_PAGE, Sort.by(sortField).ascending()));
    }

    public void addClothes(Clothes clothes) {
        List<Category> categories = categoryRepository.findAllByParentCategoryName("clothes");
        if (!categories.contains(clothes.getCategory()))
            throw new IllegalArgumentException("No such category for clothes");
        clothes.setCategory(categoryRepository.findByName(clothes.getCategory().getName()).orElse(clothes.getCategory()));
        clothesRepository.save(clothes);
    }
}
