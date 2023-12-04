package com.shop.repository;

import com.shop.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends ReactiveCrudRepository<Category, Long> {
    Flux<Category> findAllByParentCategoryIsNull();
    Flux<Category> findAllByParentCategoryName(String name);
    Mono<Category> findByName(String name);
}
