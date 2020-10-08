package com.shop.service;

import com.shop.config.constant.Constants;
import com.shop.entity.Order;
import com.shop.entity.Product;
import com.shop.entity.User;
import com.shop.enumeration.Status;
import com.shop.repository.OrderRepository;
import com.shop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private ProductRepository productRepository;
    private OrderRepository orderRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public Page<Product> getProductsPage(String categoryName, int page, String sortField, boolean descending) {
        return descending
                ? productRepository.findAllByCategoryName(categoryName, PageRequest.of(page, Constants.PRODUCTS_PER_PAGE, Sort.by(sortField).descending()))
                : productRepository.findAllByCategoryName(categoryName, PageRequest.of(page, Constants.PRODUCTS_PER_PAGE, Sort.by(sortField).ascending()));
    }

    public Page<Product> getProductsInCart(int page, String sortField, boolean descending) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Order> orders = orderRepository.getAllByUserIdAndStatus(currentUser.getId(), Status.CREATED);
        return descending
                ? productRepository.findAllByOrders(orders, PageRequest.of(page, Constants.PRODUCTS_PER_PAGE, Sort.by(sortField).descending()))
                : productRepository.findAllByOrders(orders, PageRequest.of(page, Constants.PRODUCTS_PER_PAGE, Sort.by(sortField).ascending()));
    }

    public Page<Product> getProductsFiltered(String categoryName, int page, boolean descending) {
        return null;
    }
}
