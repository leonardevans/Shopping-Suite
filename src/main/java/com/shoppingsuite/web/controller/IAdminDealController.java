package com.shoppingsuite.web.controller;

import com.shoppingsuite.persistence.model.Deal;
import com.shoppingsuite.web.dto.DealDto;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface IAdminDealController {
    public String showDeals(Model model);

    @GetMapping("/add/{prodId}")
    String showCreateDeal(@PathVariable Long prodId, Model model);

    public String createDeal(DealDto dealDto, BindingResult bindingResult);
    public String updateDeal(Deal deal);
    public String deleteDeal(Long dealId);
    public String findPaginated(int pageNo, String sortField, String sortDir, Model model);
}
