package com.shoppingsuite.service;

import com.shoppingsuite.persistence.model.Review;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ReviewService {
    Review save(Review review);
    Optional<Review> getById(Long id);
    boolean deleteById(Long id);
    boolean delete(Review review);
    Set<Review> getByProductId(Long productId);
}
