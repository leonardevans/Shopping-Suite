package com.shoppingsuite.persistence.dao;

import com.shoppingsuite.persistence.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    Page<User> findAll(Pageable pageable);

    @Query("SELECT COUNT (u) FROM User u")
    Object getTotalUsers();
}
