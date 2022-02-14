package com.shoppingsuite.service;

import com.shoppingsuite.persistence.model.Order;

import java.util.Set;

public interface OrderService {
    Order save(Order order);
    Set<Order> getAll(int pageNo, int pageSize);
    Set<Order> getByUserId(Long id);
}
