package com.shoppingsuite.service;

import com.shoppingsuite.persistence.model.Deal;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

public interface DealService {
    Deal save(Deal deal);
    Optional<Deal> getById(Long id);
    boolean delete(Deal deal);
    boolean deleteById(Long id);
    Set<Deal> getAll(int pageNo, int pageSize);
    Set<Deal> getAllByStartDate(int pageNo, int pageSize, Date startDate);
    Set<Deal> getAllByEndDate(int pageNo, int pageSize, Date endDate);
}
