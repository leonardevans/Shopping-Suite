package com.shoppingsuite.persistence.dao;

import com.shoppingsuite.persistence.embed.CartProductId;
import com.shoppingsuite.persistence.joins.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartProductDao extends JpaRepository<CartProduct, CartProductId> {
}
