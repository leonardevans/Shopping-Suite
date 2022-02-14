package com.shoppingsuite.persistence.dao;

import com.shoppingsuite.persistence.model.Deal;
import com.shoppingsuite.persistence.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DealRepo extends JpaRepository<Deal, Long> {
    Page<Deal> findAll(Pageable pageable);
    Page<Deal> findAllByEndDateBeforeOrderByEndDateDesc(Date endDate, Pageable pageable);
    Page<Deal> findAllByStartDateAfterOrderByEndDateDesc(Date startDate, Pageable pageable);
    List<Deal> findAllByProduct_Id(Long productId);
}
