package com.shoppingsuite.service.impl;

import com.shoppingsuite.persistence.dao.ProductRepo;
import com.shoppingsuite.persistence.model.Product;
import com.shoppingsuite.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepo productRepo;

    @Override
    public Product save(Product product) {
        return productRepo.save(product);
    }

    @Override
    public Optional<Product> getById(Long id) {
        return productRepo.findById(id);
    }

    @Override
    public boolean delete(Product product) {
        try{
            productRepo.delete(product);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteById(Long id) {
        try{
            productRepo.deleteById(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public Page<Product> getAll(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return productRepo.findAll(pageable);
    }

    @Override
    public Page<Product> getAllByCategoryId(Long categoryId, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return productRepo.findAllByProductCategoryId(categoryId, pageable);
    }
}
