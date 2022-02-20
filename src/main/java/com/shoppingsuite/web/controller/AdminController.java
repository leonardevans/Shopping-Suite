package com.shoppingsuite.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/*")
public class AdminController implements IAdminController{

    @GetMapping("dashboard")
    @Override
    public String showDashboard() {
        return "admin/dashboard";
    }
}
