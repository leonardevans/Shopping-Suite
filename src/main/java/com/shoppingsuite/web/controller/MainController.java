package com.shoppingsuite.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController implements IMainController{
    @Override
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
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
