package com.shoppingsuite.web.controller;

import com.shoppingsuite.persistence.model.Deal;
import com.shoppingsuite.web.dto.DealDto;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

public interface IAdminDealController {
    public String showDeals(Model model);

    @GetMapping("/add/{prodId}")
    String showCreateDeal(@PathVariable Long prodId, Model model);

    @PostMapping("/add")
    String createDeal(@ModelAttribute DealDto dealDto, BindingResult bindingResult, Model model);

    @GetMapping("/edit/{dealId}")
    String showEditDeal(@PathVariable Long dealId, Model model);

    public String updateDeal(Deal deal);
    public String deleteDeal(Long dealId);
    public String findPaginated(int pageNo, String sortField, String sortDir, Model model);
}
