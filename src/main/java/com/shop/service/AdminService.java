package com.shop.service;

import com.shop.config.constant.Constants;
import com.shop.entity.Order;
import com.shop.enumeration.Status;
import com.shop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import static com.shop.repository.specification.OrderSpecification.hasStatus;
import static com.shop.repository.specification.OrderSpecification.withUserEmail;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminService {
    private final OrderRepository orderRepository;

    public Page<Order> getOrdersPage(int page, Status... statuses) {
        if (statuses == null || statuses.length == 0 || statuses.length == Status.values().length)
            return orderRepository.findAll(PageRequest.of(page, Constants.ORDERS_PER_PAGE));
        Specification<Order> haveStatuses = hasStatus(statuses[0]);
        for (int i = 1; i < statuses.length; i++)
            haveStatuses = haveStatuses.or(hasStatus(statuses[i]));
        return orderRepository.findAll(haveStatuses, PageRequest.of(page, Constants.ORDERS_PER_PAGE));
    }

    public Page<Order> getOrdersPage(String email, int page, Status... statuses) {
        if (statuses == null || statuses.length == 0 || statuses.length == Status.values().length)
            return orderRepository.findAllByUserEmail(email, PageRequest.of(page, Constants.ORDERS_PER_PAGE));
        Specification<Order> haveStatuses = hasStatus(statuses[0]);
        for (int i = 1; i < statuses.length; i++)
            haveStatuses = haveStatuses.or(hasStatus(statuses[i]));
        haveStatuses = haveStatuses.and(withUserEmail(email));
        return orderRepository.findAll(haveStatuses, PageRequest.of(page, Constants.ORDERS_PER_PAGE));
    }

    public void changeStatus(long orderId, Status newStatus) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Order cannot be null"));
        if (!order.getStatus().allowedTransactions().contains(newStatus)) {
            log.error("New status {} is not allowed for {}", newStatus, order.getStatus());
            throw new IllegalArgumentException("New status " + newStatus + " is not allowed for " + order.getStatus());
        }
        order.setStatus(newStatus);
        orderRepository.save(order);
    }
}
