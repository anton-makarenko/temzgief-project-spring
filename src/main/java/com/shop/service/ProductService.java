package com.shop.service;

import com.shop.constant.Constants;
import com.shop.entity.Product;
import com.shop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<Product> getProductsPage(String categoryName, int page) {
        return productRepository.findAllByCategoryName(categoryName, PageRequest.of(page, Constants.PRODUCTS_PER_PAGE));
    }
}
