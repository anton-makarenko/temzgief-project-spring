package com.shop.service;

import com.shop.constant.Constants;
import com.shop.entity.Product;
import com.shop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<Product> getProductsPage(String categoryName, int page, String sortField, boolean descending) {
        return descending
                ? productRepository.findAllByCategoryName(categoryName, PageRequest.of(page, Constants.PRODUCTS_PER_PAGE, Sort.by(sortField).descending()))
                : productRepository.findAllByCategoryName(categoryName, PageRequest.of(page, Constants.PRODUCTS_PER_PAGE, Sort.by(sortField).ascending()));
    }
}
