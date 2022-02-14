package com.shoppingsuite.service.impl;

import com.shoppingsuite.persistence.dao.ReviewRepo;
import com.shoppingsuite.persistence.model.Review;
import com.shoppingsuite.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    ReviewRepo reviewRepo;

    @Override
    public Review save(Review review) {
        return reviewRepo.save(review);
    }

    @Override
    public Optional<Review> getById(Long id) {
        return reviewRepo.findById(id);
    }

    @Override
    public boolean deleteById(Long id) {
        try{
            reviewRepo.deleteById(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Review review) {
        try{
            reviewRepo.delete(review);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public Set<Review> getByProductId(Long productId) {
        return reviewRepo.findAllByProduct_Id(productId);
    }
}
