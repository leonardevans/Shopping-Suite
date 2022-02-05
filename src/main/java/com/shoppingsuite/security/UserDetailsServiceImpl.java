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
    UserRepo userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email : " + email)
                );
        return UserPrincipal.build(user);
    }

    @Transactional
    public UserDetails loadUserById(long id) throws Exception {
        User user = userRepository.findById(id).orElseThrow(
                () -> new Exception(String.valueOf(id))
        );

        return UserPrincipal.build(user);
    }
}
