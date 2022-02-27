package com.shoppingsuite.web.controller.impl;

import com.shoppingsuite.persistence.dao.ProductRepo;
import com.shoppingsuite.persistence.model.Product;
import com.shoppingsuite.service.FileUploadService;
import com.shoppingsuite.service.ProductService;
import com.shoppingsuite.web.controller.IAdminProductController;
import com.shoppingsuite.web.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.awt.*;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(value = {"/admin/products/*", "/admin/products"})
public class AdminProductController implements IAdminProductController {
    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ProductService productService;

    @Autowired
    FileUploadService fileUploadService;

    @Override
    @GetMapping("")
    public String showProducts(Model model) {
        return findPaginated(1, "name", "asc",  model);
    }

    @GetMapping("/add")
    @Override
    public String showCreateProduct(Model model) {
        model.addAttribute("productDto",  new ProductDto());
        return "/admin/createProdCat";
    }

    @Override
    public String createProduct(@Valid @ModelAttribute("productDto") ProductDto productDto, BindingResult bindingResult) throws IOException {
        if (  productDto.getImage().isEmpty() && productDto.getImage() != null){
            bindingResult.addError(new FieldError("productDto","image", "Product image is required"));
        }

        if (bindingResult.hasErrors()){
            return "admin/createProdCat";
        }

        String filename =  fileUploadService.uploadToLocal(productDto.getImage(),"uploads/images/product/");
        if (filename == null){
            return  "redirect:/admin/products?add_error";
        }

        //set image url
        productDto.setImageUrl("/" +filename);

        productRepo.save(new Product(productDto));

        return "redirect:/admin/products?add_success";
    }

    @Override
    public String updateProduct(Product product) {
        return null;
    }

    @Override
    public String deleteProduct(Long prodId) {
        return null;
    }

    @GetMapping("page/{pageNo}")
    @Override
    public String findPaginated(int pageNo, String sortField, String sortDir, Model model) {
        int pageSize = 10;

        Page<Product> page = productService.getAll(pageNo, pageSize, sortField, sortDir);
        List<Product> products = page.getContent();

        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("products", products);
        return "/admin/products";
    }
}
