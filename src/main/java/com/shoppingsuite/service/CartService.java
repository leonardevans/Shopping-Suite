package com.shoppingsuite.service;

import com.shoppingsuite.persistence.model.Cart;
import com.shoppingsuite.persistence.model.User;

import java.util.Optional;

public interface CartService {
    Cart save(Cart deal);
    boolean delete(Cart cart);
    boolean deleteById(Long id);
    Optional<Cart> getByUserAndOrdered(User user, boolean ordered);
}
