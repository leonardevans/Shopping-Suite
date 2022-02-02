package com.shoppingsuite.security;

import com.shoppingsuite.persistence.dao.UserRepo;
import com.shoppingsuite.persistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));
        return UserDetailsImpl.build(user);
    }

    @Transactional
    public UserDetails loadUserById(long id) throws Exception {
        User user = userRepo.findById(id).orElseThrow(
                () -> new Exception("No user with id: " + String.valueOf(id))
        );

        return UserDetailsImpl.build(user);
    }
}
