package com.shoppingsuite.persistence.dao;

import com.shoppingsuite.persistence.model.Product;
import com.shoppingsuite.persistence.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    List<Product> findAllByProductCategory(ProductCategory productCategory);
    List<Product> findAllByNameContains(String name);
}
