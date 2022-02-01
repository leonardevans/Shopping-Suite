package com.shoppingsuite.persistence.dao;

import com.shoppingsuite.persistence.model.Deal;
import com.shoppingsuite.persistence.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DealRepo extends JpaRepository<Deal, Long> {
    List<Deal> findAllByEndDateBefore(Date endDate);
    List<Deal> findAllByStartDateAfter(Date startDate);
    List<Deal> findAllByProduct(Product product);
}
