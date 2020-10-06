package com.shop.service;

import com.shop.entity.User;
import com.shop.repository.OrderRepository;
import com.shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    private UserRepository userRepository;
    private OrderRepository orderRepository;

    @Autowired
    public CartService(UserRepository userRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    public void addToCart() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
