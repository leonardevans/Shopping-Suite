package com.shoppingsuite.persistence.dao;

import com.shoppingsuite.persistence.model.Cart;
import com.shoppingsuite.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserAndAndOrdered(User user, boolean ordered);
}
