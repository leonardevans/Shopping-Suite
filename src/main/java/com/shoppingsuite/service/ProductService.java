package com.shoppingsuite.service;

import com.shoppingsuite.persistence.model.Product;

import java.util.Optional;
import java.util.Set;

public interface ProductService {
    Product save(Product product);
    Optional<Product> getById(Long id);
    boolean delete(Product product);
    boolean deleteById(Long id);
    Set<Product> getAll(int pageNo, int pageSize);
    Set<Product> getAllByCategoryId(Long categoryId, int pageNo, int pageSize);
}
