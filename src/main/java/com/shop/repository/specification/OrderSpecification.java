package com.shop.repository.specification;

import com.shop.entity.Order;
import com.shop.entity.User;
import com.shop.enumeration.Status;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;

public final class OrderSpecification {
    public static Specification<Order> hasStatus(Status status) {
        return (r, cq, cb) -> cb.equal(r.get("status"), status);
    }

    public static Specification<Order> withUserEmail(String email) {
        return (r, cq, cb) -> {
            Join<Order, User> user = r.join("user");
            return cb.equal(user.get("email"), email);
        };
    }

    private OrderSpecification() {

    }
}
