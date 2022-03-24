package com.shoppingsuite.persistence.dao;

import com.shoppingsuite.persistence.model.Product;
import com.shoppingsuite.persistence.model.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    Page<Product> findAllByProductCategoryId(Long productCategoryId, Pageable pageable);
    Page<Product> findAllByNameContainsOrProductCategoryContains(String search,String search1, Pageable pageable);
    Page<Product> findAll(Pageable pageable);
    List<Product> findAllByNameContains(String search);

    @Query("SELECT COUNT (p) FROM Product p")
    Object getTotalProducts();
}
