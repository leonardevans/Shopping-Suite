package com.shoppingsuite.web.controller.impl;

import com.shoppingsuite.persistence.dao.OrderRepo;
import com.shoppingsuite.persistence.model.Order;
import com.shoppingsuite.service.OrderService;
import com.shoppingsuite.web.controller.IAdminOrderController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = {"/admin/orders/*", "/admin/orders"})
public class AdminOrderController implements IAdminOrderController {
    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private OrderService orderService;

    @GetMapping("")
    @Override
    public String showOrders(Model model) {
        return findPaginated(1, "orderDate", "desc",  model);
    }

    @Override
    public String updateOrder(Order order) {
        return null;
    }

    @Override
    public String deleteOrder(Long dealId) {
        return null;
    }

    @Override
    public String findPaginated(int pageNo, String sortField, String sortDir, Model model) {
        int pageSize = 10;

        Page<Order> page = orderService.getAll(pageNo, pageSize, sortField, sortDir);
        List<Order> orders = page.getContent();

        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("orders", orders);
        return "/admin/orders";
    }
}
