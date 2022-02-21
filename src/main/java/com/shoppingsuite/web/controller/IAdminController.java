package com.shoppingsuite.web.controller;

import org.springframework.ui.Model;

public interface IAdminController {
    public String showDashboard(Model model);
    public String showDeals(Model model);
    public String showProducts(Model model);
    public String showProductCategories(Model model);
    public String showOrders(Model model);
}
