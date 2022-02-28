package com.shoppingsuite.web.controller.impl;

import com.shoppingsuite.security.AuthUtil;
import com.shoppingsuite.web.controller.IMainController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController implements IMainController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    AuthUtil authUtil;

    @Override
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/testUserLogin")
    public String testUserLogin() {
        System.out.println("logginIn");
        try {
            UsernamePasswordAuthenticationToken authReq
                    = new UsernamePasswordAuthenticationToken("bizbedu@gmail.com","adminPass22");
            Authentication auth = authenticationManager.authenticate(authReq);
            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(auth);
            return "redirect:/";
        }catch (Exception e){
            System.out.println(e.getMessage());
            return "redirect:/login?failed";
        }

    }

    @Override
    @GetMapping("/")
    public String showIndexPage() {
        return "index";
    }

    @Override
    public String showProductPage() {
        return null;
    }

    @Override
    public String showDealsPage() {
        return null;
    }

    @Override
    public String showDealPage() {
        return null;
    }

    @Override
    public String showCartPage() {
        return null;
    }

    @Override
    public String showCheckoutPage() {
        return null;
    }
}
