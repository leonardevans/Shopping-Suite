package com.shoppingsuite.service;

import com.shoppingsuite.persistence.model.User;
import com.shoppingsuite.web.dto.UserDto;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface UserService {
    Page<User> getAll(int pageNo, int pageSize, String sortField, String sortDirection);
    Optional<User> getUserById(Long id);
    Optional<User> getUserByEmail(String email);
    User saveUser(UserDto userDto);
    User saveUser(User user);
    boolean deleteUser(User user);
    boolean deleteUserById(Long id);
}
