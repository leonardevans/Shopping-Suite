package com.shoppingsuite.persistence.dao;

import com.shoppingsuite.persistence.model.Product;
import com.shoppingsuite.persistence.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepo extends JpaRepository<Rating, Long> {
    List<Rating> findAllByProduct(Product product);
}
