package com.shoppingsuite.service;

import com.shoppingsuite.persistence.model.Deal;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

public interface DealService {
    Deal save(Deal deal);
    Optional<Deal> getById(Long id);
    boolean delete(Deal deal);
    boolean deleteById(Long id);
    Page<Deal> getAll(int pageNo, int pageSize, String sortField, String sortDir);
    Page<Deal> getAllByStartDateAfter(int pageNo, int pageSize, Date startDate);
    Page<Deal> getAllByEndDateBefore(int pageNo, int pageSize, Date endDate);
}
