package com.shoppingsuite.service.impl;

import com.shoppingsuite.persistence.dao.CartRepo;
import com.shoppingsuite.persistence.model.Cart;
import com.shoppingsuite.persistence.model.User;
import com.shoppingsuite.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    CartRepo cartRepo;

    @Override
    public Cart save(Cart cart) {
        return cartRepo.save(cart);
    }

    @Override
    public boolean delete(Cart cart) {
        try{
            cartRepo.delete(cart);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteById(Long id) {
        try{
            cartRepo.deleteById(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public Optional<Cart> getByUserAndOrdered(User user, boolean ordered) {
        return cartRepo.findByUserAndAndOrdered(user, ordered);
    }
}
