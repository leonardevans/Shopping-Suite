package com.shoppingsuite.persistence.dao;

import com.shoppingsuite.persistence.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
    Page<Order> findAll(Pageable pageable);
    Set<Order> findAllByCart_User_IdOrderByOrderDateDesc(Long id);

    @Query("SELECT COUNT (o) FROM Order o")
    Object getTotalOrders();
}
