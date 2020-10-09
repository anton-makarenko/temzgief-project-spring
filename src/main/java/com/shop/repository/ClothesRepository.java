package com.shop.repository;

import com.shop.entity.Clothes;
import com.shop.entity.Order;
import com.shop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClothesRepository extends JpaRepository<Clothes, Long>, JpaSpecificationExecutor<Clothes> {
    Page<Clothes> findAllByCategoryName(String categoryName, Pageable pageable);
    Page<Clothes> findAllByOrders(List<Order> orders, Pageable pageable);
}
