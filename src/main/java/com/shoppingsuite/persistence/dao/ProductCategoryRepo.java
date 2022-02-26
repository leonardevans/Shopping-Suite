package com.shoppingsuite.persistence.dao;

import com.shoppingsuite.persistence.model.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductCategoryRepo extends JpaRepository<ProductCategory, Long> {
    List<ProductCategory> findAllByNameContains(String name);
    Page<ProductCategory> findAll(Pageable pageable);
    boolean existsByName(String name);
    Optional<ProductCategory> findByName(String name);

    @Query("SELECT COUNT (pc) FROM ProductCategory pc")
    Object getTotalProductCategories();
}
