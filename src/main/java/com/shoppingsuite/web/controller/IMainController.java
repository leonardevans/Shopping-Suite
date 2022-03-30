package com.shoppingsuite.web.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

public interface IMainController {
    public String showLoginPage();
    String showIndexPage(Model model, HttpSession httpSession);
    public String showProductPage(Model model, Long productId );

    @GetMapping("/product/{productCatId}")
    String showProductsPage(Model model, @PathVariable Long productCatId);

    public String showDealsPage(Model model);
    public String showDealPage(Model model, Long dealId);
    public String showCartPage(Model model, HttpSession httpSession);
    public String showCheckoutPage(Model model);

    @GetMapping("page/{pageNo}")
    String findPaginated(int pageNo, String sortField, String sortDir, Model model, String returnPage);
}
