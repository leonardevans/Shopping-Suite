package com.shoppingsuite.web.controller;

import com.shoppingsuite.persistence.model.Product;
import com.shoppingsuite.web.dto.ProductDto;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

public interface IAdminProductController {
    public String showProducts(Model model);
    public String createProduct(ProductDto productDto, BindingResult bindingResult);
    public String updateProduct(Product product);
    public String deleteProduct(Long prodId);
    public String findPaginated(int pageNo, String sortField, String sortDir, Model model);
}
