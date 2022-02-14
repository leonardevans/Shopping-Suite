package com.shoppingsuite.service;

import com.shoppingsuite.persistence.model.ProductCategory;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.Set;

public interface ProductCategoryService {
    ProductCategory save(ProductCategory productCategory);
    Optional<ProductCategory> getById(Long id);
    boolean delete(ProductCategory productCategory);
    boolean deleteById(Long id);
    Set<ProductCategory> getAll();
    Page<ProductCategory> getAll(int pageNo, int pageSize);
}
