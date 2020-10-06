package com.shop.repository;

import com.shop.entity.Order;
import com.shop.enumeration.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> getByUserId(long id);
    List<Order> getAllByUserIdAndStatus(long id, Status status);
}
