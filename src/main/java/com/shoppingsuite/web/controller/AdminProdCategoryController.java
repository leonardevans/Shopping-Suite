package com.shoppingsuite.web.controller;

import com.shoppingsuite.persistence.dao.ProductCategoryRepo;
import com.shoppingsuite.persistence.model.ProductCategory;
import com.shoppingsuite.service.ProductCategoryService;
import com.shoppingsuite.web.dto.ProdCategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(value = {"/admin/prodCategories/*", "/admin/prodCategories"})
public class AdminProdCategoryController implements IAdminProdCategoryController{
    @Autowired
    private ProductCategoryRepo productCategoryRepo;

    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("")
    @Override
    public String showProdCategories(Model model) {
        return findPaginated(1, "name", "asc",  model);
    }

    @Override
    public String createProdCategory(ProdCategoryDto prodCategoryDto) {
        return null;
    }

    @Override
    public String updateProdCategory(ProductCategory productCategory) {
        return null;
    }

    @Override
    public String deleteProdCategory(Long prodCategoryId) {
        return null;
    }

    @Override
    @GetMapping("page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 10;

        Page<ProductCategory> page = productCategoryService.getAll(pageNo, pageSize, sortField, sortDir);
        List<ProductCategory> productCategoryList = page.getContent();

        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("productCategories", productCategoryList);
        return "/admin/users";
    }
}
