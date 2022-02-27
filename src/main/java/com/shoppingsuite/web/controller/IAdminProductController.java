package com.shoppingsuite.web.controller;

import com.shoppingsuite.persistence.model.Product;
import com.shoppingsuite.web.dto.ProductDto;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.validation.Valid;
import java.io.IOException;

public interface IAdminProductController {
    public String showProducts(Model model);

    @GetMapping("/add")
    String showCreateProduct(Model model);

    String createProduct(@Valid @ModelAttribute("productDto") ProductDto productDto, BindingResult bindingResult, Model model) throws IOException;

    public String updateProduct(Product product);
    public String deleteProduct(Long prodId) throws IOException;
    public String findPaginated(int pageNo, String sortField, String sortDir, Model model);
}
