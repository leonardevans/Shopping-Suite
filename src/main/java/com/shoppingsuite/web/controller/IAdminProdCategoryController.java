package com.shoppingsuite.web.controller;

import com.shoppingsuite.persistence.model.ProductCategory;
import com.shoppingsuite.web.dto.ProdCategoryDto;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;

public interface IAdminProdCategoryController {
    public String showProdCategories(Model model);

    String showCreateProdCat(Model model);
    public String createProdCategory(ProdCategoryDto prodCategoryDto, BindingResult bindingResult);

    @GetMapping("/edit/{prodCatId}")
    String showEditProdCategory(@PathVariable("prodCatId") Long prodCategoryId, Model model);

    public String updateProdCategory(@Valid @ModelAttribute("prodCategoryDto") ProdCategoryDto prodCategoryDto, BindingResult bindingResult);
    public String deleteProdCategory(Long prodCategoryId);
    public String findPaginated(int pageNo, String sortField, String sortDir, Model model);
}
