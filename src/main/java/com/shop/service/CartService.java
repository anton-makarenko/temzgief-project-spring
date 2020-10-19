package com.shop.service;

import com.shop.config.constant.Constants;
import com.shop.entity.Order;
import com.shop.entity.Product;
import com.shop.entity.User;
import com.shop.enumeration.Status;
import com.shop.repository.OrderRepository;
import com.shop.repository.ClothesRepository;
import com.shop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    private OrderRepository orderRepository;
    private ClothesRepository clothesRepository;
    private ProductRepository productRepository;

    @Autowired
    public CartService(OrderRepository orderRepository, ClothesRepository clothesRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.clothesRepository = clothesRepository;
        this.productRepository = productRepository;
    }

    public void addProductToCart(long productId) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Order order;
        Optional<Order> optionalOrder;
        if ((optionalOrder = orderRepository.findByUserIdAndStatus(currentUser.getId(), Status.CREATED)).isPresent())
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
        if ((optionalOrder = orderRepository.findByUserIdAndStatus(currentUser.getId(), Status.CREATED)).isPresent()) {
            order = optionalOrder.get();
            order.getProducts().remove(clothesRepository.findById(productId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, Constants.NO_PRODUCT)));
            orderRepository.save(order);
        }
    }

    public void submitOrdersInCart() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Order> orders = orderRepository.findAllByUserIdAndStatus(currentUser.getId(), Status.CREATED);
        orders.forEach((order) -> order.setStatus(Status.REGISTERED));
        orderRepository.saveAll(orders);
    }

    public Page<Product> getClothesInCart(int page, String sortField, boolean descending) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Order> orders = orderRepository.findAllByUserIdAndStatus(currentUser.getId(), Status.CREATED);
        return descending
                ? productRepository.findAllByOrders(orders, PageRequest.of(page, Constants.PRODUCTS_PER_PAGE, Sort.by(sortField).descending()))
                : productRepository.findAllByOrders(orders, PageRequest.of(page, Constants.PRODUCTS_PER_PAGE, Sort.by(sortField).ascending()));
    }
}
