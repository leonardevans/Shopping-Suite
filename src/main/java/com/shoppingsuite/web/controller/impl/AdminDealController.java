package com.shoppingsuite.web.controller.impl;

import com.shoppingsuite.persistence.dao.DealRepo;
import com.shoppingsuite.persistence.model.Deal;
import com.shoppingsuite.persistence.model.Product;
import com.shoppingsuite.service.DealService;
import com.shoppingsuite.service.ProductService;
import com.shoppingsuite.web.controller.IAdminDealController;
import com.shoppingsuite.web.dto.DealDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = {"/admin/deals/*", "/admin/deals"})
public class AdminDealController implements IAdminDealController {
    @Autowired
    private DealRepo dealRepo;

    @Autowired
    private DealService dealService;

    @Autowired
    private ProductService productService;

    @GetMapping("")
    @Override
    public String showDeals(Model model) {
        return findPaginated(1, "endDate", "asc",  model);
    }

    @GetMapping("/add/{prodId}")
    @Override
    public String showCreateDeal(@PathVariable Long prodId, Model model) {
        //get the product
        Optional<Product> product = productService.getById(prodId);
        if (product.isEmpty()){
            return "redirect:/admin/deals?product_not_found";
        }

        model.addAttribute("dealDto", new DealDto(product.get()));

        return "/admin/createDeal";
    }

    @PostMapping("/add")
    @Override
    public String createDeal(@Valid @ModelAttribute("dealDto") DealDto dealDto, BindingResult bindingResult) {
        Optional<Product> product = productService.getById(dealDto.getProductId());
        dealDto.setProduct(product.get());

        if (dealDto.getDealPrice() >= dealDto.getProduct().getPrice()){
            bindingResult.addError(new FieldError("dealDto","dealPrice", "Deal Price should be less than the product price"));
        }

        if (bindingResult.hasErrors()){
            return "/admin/createDeal";
        }

        dealService.save(new Deal(dealDto));
        return "redirect:/admin/deals/?add_success";
    }

    @GetMapping("/edit/{dealId}")
    @Override
    public String showEditDeal(@PathVariable Long dealId, Model model){
        Optional<Deal> deal = dealService.getById(dealId);

        model.addAttribute("dealDto", new DealDto(deal.get()));

        return "/admin/createDeal";
    }

    @PostMapping("/update")
    @Override
    public String updateDeal(@Valid @ModelAttribute("dealDto") DealDto dealDto, BindingResult bindingResult) {
        Optional<Product> product = productService.getById(dealDto.getProductId());
        dealDto.setProduct(product.get());

        if (dealDto.getDealPrice() >= dealDto.getProduct().getPrice()){
            bindingResult.addError(new FieldError("dealDto","dealPrice", "Deal Price should be less than the product price"));
        }

        if (bindingResult.hasErrors()){
            return "/admin/createDeal";
        }

        dealService.save(new Deal(dealDto));
        return "redirect:/admin/deals/?update_success";
    }

    @GetMapping("/delete/{dealId}")
    @Override
    public String deleteDeal(@PathVariable("dealId") Long dealId) {
        if (dealService.deleteById(dealId)){
            return "redirect:/admin/deals/?delete_success";
        }
        return "redirect:/admin/deals/?delete_error";
    }

    @GetMapping("page/{pageNo}")
    @Override
    public String findPaginated(@PathVariable int pageNo,@RequestParam String sortField, @RequestParam String sortDir, Model model) {
        int pageSize = 10;

        Page<Deal> page = dealService.getAll(pageNo, pageSize, sortField, sortDir);
        List<Deal> deals = page.getContent();

        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("deals", deals);
        return "/admin/deals";
    }
}
