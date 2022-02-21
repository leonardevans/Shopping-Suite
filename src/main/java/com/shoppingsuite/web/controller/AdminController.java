package com.shoppingsuite.web.controller;

import com.shoppingsuite.persistence.dao.*;
import com.shoppingsuite.service.UserService;
import com.shoppingsuite.web.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController implements IAdminController{
    @Autowired
    UserRepo userRepo;

    @Autowired
    UserService userService;

    @Autowired
    ProductRepo productRepo;

    @Autowired
    DealRepo dealRepo;

    @Autowired
    OrderRepo orderRepo;

    @Autowired
    RatingRepo ratingRepo;

    @Autowired
    ReviewRepo reviewRepo;

    @Autowired
    ProductCategoryRepo productCategoryRepo;

    @GetMapping(value = {"/admin/dashboard", "/admin/", "/admin/home"})
    @Override
    public String showDashboard(Model model) {
        //get total user count
        model.addAttribute("totalUsers", userRepo.getTotalUsers());

        //get total deals count
        model.addAttribute("totalDeals", dealRepo.getTotalDeals());

        //get total orders count
        model.addAttribute("totalOrders", orderRepo.getTotalOrders());

        //get total products count
        model.addAttribute("totalProducts", productRepo.getTotalProducts());

        //get total product categories count
        model.addAttribute("totalProductCategories", productCategoryRepo.getTotalProductCategories());

        //get total reviews count
        model.addAttribute("totalReviews", reviewRepo.getTotalReviews());

        //get total ratings count
        model.addAttribute("totalRatings", ratingRepo.getTotalRatings());

        return "/admin/dashboard";
    }

    @Override
    public String showDeals(Model model) {
        model.addAttribute("deals", dealRepo.findAll());
        return "/admin/deals";
    }

    @Override
    public String showProducts(Model model) {
        model.addAttribute("products", productRepo.findAll());
        return "/admin/products";
    }

    @Override
    public String showProductCategories(Model model) {
        model.addAttribute("p-categories", productCategoryRepo.findAll());
        return "/admin/p-categories";
    }

    @Override
    public String showOrders(Model model) {
        model.addAttribute("orders", orderRepo.findAll());
        return "/admin/orders";
    }
}
