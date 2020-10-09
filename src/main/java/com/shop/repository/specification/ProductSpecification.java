package com.shop.repository.specification;

import com.shop.entity.Category;
import com.shop.entity.Product;
import com.shop.enumeration.Color;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public final class ProductSpecification {
    public static Specification<Product> hasColor(Color color) {
        return (r, cq, cb) -> cb.equal(r.get("color"), color);
    }

    public static Specification<Product> priceBetween(double from, double to) {
        return (r, cq, cb) -> cb.between(r.get("price"), from, to);
    }

    public static Specification<Product> inCategory(String categoryName) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Join<Product, Category> category = root.join("category");
            return criteriaBuilder.equal(category.get("name"), categoryName);
        };
    }

    private ProductSpecification() {

    }
}
