package com.shoppingsuite;

import com.shoppingsuite.persistence.dao.RoleRepo;
import com.shoppingsuite.persistence.dao.UserRepo;
import com.shoppingsuite.persistence.model.Role;
import com.shoppingsuite.persistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ApplicationInitializer  implements CommandLineRunner {
    @Autowired
    private RoleRepo roleRepository;

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        //check if BIDDER role exists, if not create it and save
        Optional<Role> optionalRole3 = roleRepository.findByName("ROLE_USER");
        if (optionalRole3.isEmpty()){
            roleRepository.save(new Role("ROLE_USER"));
        }

        //check if ADMIN role exists, if not create it and save
        Optional<Role> optionalRole2 = roleRepository.findByName("ROLE_ADMIN");
        if (optionalRole2.isEmpty()){
            roleRepository.save(new Role("ROLE_ADMIN"));
        }

        try
        {
            Thread.sleep(3000);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }

        String adminEmail = "bizbedu@gmail.com";
        // check if there's admin user, if not add user with role admin
        if (!userRepository.existsByEmail(adminEmail)){
            User admin = new User("admin", "", adminEmail, passwordEncoder.encode("adminPass22"));
            admin.setEnabled(true);

            //            get user role and add this role to admin
            Optional<Role> userRole = roleRepository.findByName("ROLE_USER");
            userRole.ifPresent(role -> admin.getRoles().add(role));

//            get admin role and add this role to admin
            Optional<Role> adminRole = roleRepository.findByName("ROLE_ADMIN");
            adminRole.ifPresent(role -> admin.getRoles().add(role));

//            save this user
            userRepository.save(admin);
        }
    }
}
