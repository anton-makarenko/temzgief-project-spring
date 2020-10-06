package com.shop.repository;

import com.shop.entity.Order;
import com.shop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllByCategoryName(String categoryName, Pageable pageable);
    Page<Product> findAllByOrders(List<Order> orders, Pageable pageable);
}
