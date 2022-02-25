package com.shoppingsuite.web.controller;

import com.shoppingsuite.persistence.model.Deal;
import com.shoppingsuite.web.dto.DealDto;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

public interface IAdminDealController {
    public String showDeals(Model model);
    public String createDeal(DealDto dealDto, BindingResult bindingResult);
    public String updateDeal(Deal deal);
    public String deleteDeal(Long dealId);
    public String findPaginated(int pageNo, String sortField, String sortDir, Model model);
}
