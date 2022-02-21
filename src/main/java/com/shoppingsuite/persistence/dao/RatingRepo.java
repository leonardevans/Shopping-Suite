package com.shoppingsuite.persistence.dao;

import com.shoppingsuite.persistence.model.Product;
import com.shoppingsuite.persistence.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RatingRepo extends JpaRepository<Rating, Long> {
    Set<Rating> findAllByProduct_Id(Long productId);

    @Query("SELECT COUNT (r) FROM Rating r")
    Object getTotalRatings();
}
