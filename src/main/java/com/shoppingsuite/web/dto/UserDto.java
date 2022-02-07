package com.shoppingsuite.web.dto;

import com.shoppingsuite.persistence.model.Role;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private boolean enabled;
    private Set<Role> roles = new HashSet<>();
}
