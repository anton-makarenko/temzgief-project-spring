package com.shop.repository.specification;

import com.shop.entity.Category;
import com.shop.entity.Clothes;
import com.shop.entity.Product;
import com.shop.enumeration.Color;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public final class ClothesSpecification {
    public static Specification<Clothes> hasColor(Color color) {
        return (r, cq, cb) -> cb.equal(r.get("color"), color);
    }

    public static Specification<Clothes> priceBetween(double min, double max) {
        return (r, cq, cb) -> cb.between(r.get("price"), min, max);
    }

    public static Specification<Clothes> inCategory(String categoryName) {
        return (r, cq, cb) -> {
            Join<Product, Category> category = r.join("category");
            return cb.equal(category.get("name"), categoryName);
        };
    }

    private ClothesSpecification() {

    }
}
