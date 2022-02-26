package com.shoppingsuite.web.controller.impl;

import com.shoppingsuite.persistence.dao.ProductCategoryRepo;
import com.shoppingsuite.persistence.model.ProductCategory;
import com.shoppingsuite.service.ProductCategoryService;
import com.shoppingsuite.web.controller.IAdminProdCategoryController;
import com.shoppingsuite.web.dto.ProdCategoryDto;
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
@RequestMapping(value = {"/admin/prodCategories/*", "/admin/prodCategories"})
public class AdminProdCategoryController implements IAdminProdCategoryController {
    @Autowired
    private ProductCategoryRepo productCategoryRepo;

    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("")
    @Override
    public String showProdCategories(Model model) {
        return findPaginated(1, "name", "asc",  model);
    }

    @GetMapping("/add")
    @Override
    public String showCreateProdCat(Model model) {
        model.addAttribute("prodCategoryDto",  new ProdCategoryDto());
        return "/admin/createProdCat";
    }

    @PostMapping("/add")
    @Override
    public String createProdCategory(@Valid @ModelAttribute("prodCategoryDto") ProdCategoryDto prodCategoryDto, BindingResult bindingResult) {
        //check if category with this name exists
        if (productCategoryRepo.existsByName(prodCategoryDto.getName())){
            bindingResult.addError(new FieldError("prodCategoryDto","name", "Product category name exist"));
        }

        if (bindingResult.hasErrors()){
            return "/admin/createProdCat";
        }

        productCategoryRepo.save(new ProductCategory(prodCategoryDto));

        return "redirect:/admin/prodCategories?add_success";
    }

    @GetMapping("/edit/{prodCatId}")
    @Override
    public String showEditProdCategory(@PathVariable("prodCatId") Long prodCategoryId, Model model) {
        Optional<ProductCategory> productCategory = productCategoryService.getById(prodCategoryId);

        if (productCategory.isPresent()){
            ProdCategoryDto prodCategoryDto = new ProdCategoryDto(productCategory.get());
            model.addAttribute("prodCategoryDto",  prodCategoryDto);
            return "/admin/createProdCat";
        }
        return "redirect:/admin/prodCategories?not_found";
    }

    @Override
    public String updateProdCategory(ProductCategory productCategory) {
        return null;
    }

    @GetMapping("/delete/{prodCatId}")
    @Override
    public String deleteProdCategory(@PathVariable("prodCatId") Long prodCategoryId) {
        if (productCategoryService.deleteById(prodCategoryId)){
            return "redirect:/admin/prodCategories?delete_success";
        }
        return "redirect:/admin/prodCategories?delete_error";
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
        return "/admin/prodCategories";
    }
}
