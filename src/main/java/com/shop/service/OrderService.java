package com.shop.service;

import com.shop.config.constant.Constants;
import com.shop.entity.Order;
import com.shop.entity.User;
import com.shop.repository.OrderRepository;
import com.shop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private ProductRepository productRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public void addProductToCart(Long productId) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Order order;
        Optional<Order> optionalOrder;
        if ((optionalOrder = orderRepository.getByUserId(currentUser.getId())).isPresent())
            order = optionalOrder.get();
        else {
            order = new Order();
            order.setUser(currentUser);
            orderRepository.save(order);
        }
        order.getProducts().add(productRepository.findById(productId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, Constants.NO_PRODUCT)));
        orderRepository.save(order);
    }
}
