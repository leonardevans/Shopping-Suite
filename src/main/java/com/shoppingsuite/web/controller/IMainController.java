package com.shoppingsuite.web.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public interface IMainController {
    public String showLoginPage();
    String showIndexPage(Model model);
    public String showProductPage(Model model, Long productId );
    public String showDealsPage(Model model);
    public String showDealPage(Model model, Long dealId);
    public String showCartPage(Model model);
    public String showCheckoutPage(Model model);

    @GetMapping("page/{pageNo}")
    String findPaginated(int pageNo, String sortField, String sortDir, Model model, String returnPage);
}
