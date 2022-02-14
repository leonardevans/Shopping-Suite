package com.shoppingsuite.service.impl;

import com.shoppingsuite.persistence.dao.RatingRepo;
import com.shoppingsuite.persistence.model.Rating;
import com.shoppingsuite.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class RatingServiceImpl implements RatingService {
    @Autowired
    RatingRepo ratingRepo;

    @Override
    public Rating saveRating(Rating rating) {
        return ratingRepo.save(rating);
    }

    @Override
    public Optional<Rating> getById(Long id) {
        return ratingRepo.findById(id);
    }

    @Override
    public boolean deleteById(Long id) {
        try{
            ratingRepo.deleteById(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Rating rating) {
        try{
            ratingRepo.delete(rating);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public Set<Rating> getByProductId(Long productId) {
        return ratingRepo.findAllByProduct_Id(productId);
    }
}
