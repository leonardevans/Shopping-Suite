package com.shoppingsuite.service.impl;

import com.shoppingsuite.persistence.dao.ProductCategoryRepo;
import com.shoppingsuite.persistence.model.ProductCategory;
import com.shoppingsuite.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    ProductCategoryRepo productCategoryRepo;

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return productCategoryRepo.save(productCategory);
    }

    @Override
    public Optional<ProductCategory> getById(Long id) {
        return productCategoryRepo.findById(id);
    }

    @Override
    public boolean delete(ProductCategory productCategory) {
        try{
            productCategoryRepo.delete(productCategory);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteById(Long id) {
        try{
            productCategoryRepo.deleteById(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public Set<ProductCategory> getAll() {
        return new HashSet<>(productCategoryRepo.findAll());
    }

    @Override
    public Page<ProductCategory> getAll(int pageNo, int pageSize, String sortField, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo-1, pageSize, sort);
        return productCategoryRepo.findAll(pageable);
    }
}
