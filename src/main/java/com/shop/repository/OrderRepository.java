package com.shop.repository;

import com.shop.entity.Order;
import com.shop.enumeration.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    Optional<Order> findByUserIdAndStatus(long id, Status status);
    List<Order> findAllByUserIdAndStatus(long id, Status status);
    Page<Order> findAllByUserEmail(String email, Pageable pageable);
}
