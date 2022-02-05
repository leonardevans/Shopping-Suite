package com.shoppingsuite.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    //show login page
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/")
    public String showIndexPage(Model model){
        return "index";
    }
}
