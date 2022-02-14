package com.shoppingsuite.service;

import com.shoppingsuite.persistence.model.Product;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.Set;

public interface ProductService {
    Product save(Product product);
    Optional<Product> getById(Long id);
    boolean delete(Product product);
    boolean deleteById(Long id);
    Page<Product> getAll(int pageNo, int pageSize);
    Page<Product> getAllByCategoryId(Long categoryId, int pageNo, int pageSize);
    Page<Product> search(String search, int pageNo, int pageSize);
}
