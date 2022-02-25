package com.shoppingsuite.web.controller;

import com.shoppingsuite.persistence.model.ProductCategory;
import com.shoppingsuite.web.dto.ProdCategoryDto;
import org.springframework.ui.Model;

public interface IAdminProdCategoryController {
    public String showProdCategories(Model model);
    public String createProdCategory(ProdCategoryDto prodCategoryDto);
    public String updateProdCategory(ProductCategory productCategory);
    public String deleteProdCategory(Long prodCategoryId);
    public String findPaginated(int pageNo, String sortField, String sortDir, Model model);
}
