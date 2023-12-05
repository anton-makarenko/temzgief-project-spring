package com.shop.repository;

import com.shop.entity.Order;
import com.shop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllByOrdersIn(List<Order> orders, Pageable pageable);
}
