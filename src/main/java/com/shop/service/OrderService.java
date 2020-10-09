package com.shop.service;

import com.shop.config.constant.Constants;
import com.shop.entity.Order;
import com.shop.entity.User;
import com.shop.enumeration.Status;
import com.shop.repository.OrderRepository;
import com.shop.repository.ClothesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private ClothesRepository clothesRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, ClothesRepository clothesRepository) {
        this.orderRepository = orderRepository;
        this.clothesRepository = clothesRepository;
    }

    public void addProductToCart(long productId) {
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
        order.getProducts().add(clothesRepository.findById(productId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, Constants.NO_PRODUCT)));
        orderRepository.save(order);
    }

    public void removeProductFromCart(long productId) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Order order;
        Optional<Order> optionalOrder;
        if ((optionalOrder = orderRepository.getByUserId(currentUser.getId())).isPresent()) {
            order = optionalOrder.get();
            order.getProducts().remove(clothesRepository.findById(productId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, Constants.NO_PRODUCT)));
            orderRepository.save(order);
        }
    }

    public void submitOrdersInCart() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Order> orders = orderRepository.getAllByUserIdAndStatus(currentUser.getId(), Status.CREATED);
        orders.forEach((order) -> order.setStatus(Status.REGISTERED));
        orderRepository.saveAll(orders);
    }
}
