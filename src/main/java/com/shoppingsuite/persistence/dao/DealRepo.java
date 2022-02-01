package com.shoppingsuite.persistence.dao;

import com.shoppingsuite.persistence.model.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DealRepo extends JpaRepository<Deal, Long> {
}
