package com.shoppingsuite.service;

import com.shoppingsuite.persistence.model.Rating;

import java.util.Optional;
import java.util.Set;

public interface RatingService {
    Rating saveRating(Rating review);
    Optional<Rating> getById(Long id);
    boolean deleteById(Long id);
    boolean delete(Rating review);
    Set<Rating> getByProductId(Long productId);
}
