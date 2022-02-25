package com.shoppingsuite.service;

import com.shoppingsuite.persistence.model.Order;
import org.springframework.data.domain.Page;

import java.util.Set;

public interface OrderService {
    Order save(Order order);
    Set<Order> getByUserId(Long id);
    Page<Order> getAll(int pageNo, int pageSize, String sortField, String sortDir);
}
