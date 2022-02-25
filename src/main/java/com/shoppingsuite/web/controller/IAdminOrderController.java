package com.shoppingsuite.web.controller;

import com.shoppingsuite.persistence.model.Order;
import org.springframework.ui.Model;

public interface IAdminOrderController {
    public String showOrders(Model model);
    public String updateOrder(Order order);
    public String deleteOrder(Long dealId);
    public String findPaginated(int pageNo, String sortField, String sortDir, Model model);
}
