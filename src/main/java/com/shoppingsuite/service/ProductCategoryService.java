package com.shoppingsuite.service;

import com.shoppingsuite.persistence.model.Product;
import com.shoppingsuite.persistence.model.ProductCategory;

import java.util.Optional;
import java.util.Set;

public interface ProductCategoryService {
    ProductCategory save(ProductCategory productCategory);
    Optional<ProductCategory> getById(Long id);
    boolean delete(ProductCategory ProductCategory);
    boolean deleteById(Long id);
    Set<ProductCategory> getAll();
}
