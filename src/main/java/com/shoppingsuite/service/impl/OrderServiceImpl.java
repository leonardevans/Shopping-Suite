package com.shoppingsuite.service.impl;

import com.shoppingsuite.persistence.dao.OrderRepo;
import com.shoppingsuite.persistence.model.Order;
import com.shoppingsuite.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepo orderRepo;

    @Override
    public Order save(Order order) {
        return orderRepo.save(order);
    }

    @Override
    public Set<Order> getByUserId(Long userId) {
        return orderRepo.findAllByCart_User_IdOrderByOrderDateDesc(userId);
    }

    @Override
    public Page<Order> getAll(int pageNo, int pageSize, String sortField, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo-1, pageSize, sort);
        return orderRepo.findAll(pageable);
    }
}
