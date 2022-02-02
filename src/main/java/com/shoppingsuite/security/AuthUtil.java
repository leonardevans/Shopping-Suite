package com.shoppingsuite.security;

import com.shoppingsuite.persistence.dao.UserRepo;
import com.shoppingsuite.persistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthUtil {
    @Autowired
    private UserRepo userRepo;

    public User getLoggedInUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ( authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        UserDetails loggedInUser;
        try {
            loggedInUser = (UserDetails) principal;
        }
        catch (Exception e){
            return null;
        }

        Optional<User> optionalUser = userRepo.findByEmail(loggedInUser.getUsername());
        return optionalUser.orElse(null);
    }
}
