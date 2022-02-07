package com.shoppingsuite.service;

import com.shoppingsuite.persistence.model.User;
import com.shoppingsuite.web.dto.UserDto;

import java.util.Optional;

public interface UserService {
    Optional<User> getUserById(Long id);
    Optional<User> getUserByEmail(String email);
    User saveUser(UserDto userDto);
    User saveUser(User user);
    boolean deleteUser(User user);
    boolean deleteUserById(Long id);
}
