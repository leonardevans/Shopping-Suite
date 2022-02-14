package com.shoppingsuite.service;

import com.shoppingsuite.persistence.model.Product;
import com.shoppingsuite.persistence.model.ProductCategory;

import java.util.Optional;
import java.util.Set;

public interface ProductService {
    Product save(Product product);
    Optional<Product> getById(Long id);
    boolean delete(Product product);
    boolean deleteById(Long id);
    Set<Product> getByProductCategory(ProductCategory productCategory);
    Set<Product> getByProductCategoryId(Long id);
}
