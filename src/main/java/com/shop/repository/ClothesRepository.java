package com.shop.repository;

import com.shop.entity.Clothes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ClothesRepository extends JpaRepository<Clothes, Long>, JpaSpecificationExecutor<Clothes> {
    Page<Clothes> findAllByCategoryName(String categoryName, Pageable pageable);
    Page<Clothes> findAll(Pageable pageable);
}
