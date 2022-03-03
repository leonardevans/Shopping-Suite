package com.shoppingsuite.web.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public interface IMainController {
    public String showLoginPage();
    @GetMapping("/")
    String showIndexPage(Model model);

    public String showProductPage();
    public String showDealsPage();
    public String showDealPage();
    public String showCartPage();
    public String showCheckoutPage();

    @GetMapping("page/{pageNo}")
    String findPaginated(int pageNo, String sortField, String sortDir, Model model, String returnPage);
}
