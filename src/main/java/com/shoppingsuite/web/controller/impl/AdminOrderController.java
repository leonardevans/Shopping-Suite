package com.shoppingsuite.web.controller.impl;

import com.shoppingsuite.persistence.dao.OrderRepo;
import com.shoppingsuite.persistence.enums.OrderStatus;
import com.shoppingsuite.persistence.model.Order;
import com.shoppingsuite.service.OrderService;
import com.shoppingsuite.web.controller.IAdminOrderController;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/edit/{orderId}")
    public String showEditOrder(Model model, @PathVariable Long orderId) throws NotFoundException {
        Order order = orderRepo.findById(orderId).orElseThrow(() -> new NotFoundException("No order found with id: " + orderId));
        model.addAttribute("order", order);
        model.addAttribute("orderStatuses", OrderStatus.values());
        return "/admin/order";
    }

    @PostMapping("/update")
    @Override
    public String updateOrder(@ModelAttribute Order order) throws NotFoundException {
        Order orderToUpdate = orderRepo.findById(order.getId()).orElseThrow(() -> new NotFoundException("No order found with id: " + order.getId()));
        orderToUpdate.setOrderStatus(order.getOrderStatus());
        orderRepo.save(orderToUpdate);
        return "redirect:/admin/orders/?update_success";
    }

    @GetMapping("/delete/{orderId}")
    @Override
    public String deleteOrder(@PathVariable Long orderId) {
        try {
            orderRepo.deleteById(orderId);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return "redirect:/admin/orders?delete_error";
        }
        return "redirect:/admin/orders?delete_success";
    }

    @GetMapping("page/{pageNo}")
    @Override
    public String findPaginated(@PathVariable int pageNo, @RequestParam String sortField, @RequestParam String sortDir, Model model) {
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
