package com.shoppingsuite.web.controller.impl;

import com.shoppingsuite.persistence.model.Deal;
import com.shoppingsuite.persistence.model.Product;
import com.shoppingsuite.security.AuthUtil;
import com.shoppingsuite.service.DealService;
import com.shoppingsuite.service.ProductCategoryService;
import com.shoppingsuite.service.ProductService;
import com.shoppingsuite.web.controller.IMainController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class MainController implements IMainController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    AuthUtil authUtil;

    @Autowired
    ProductService productService;

    @Autowired
    ProductCategoryService productCategoryService;

    @Autowired
    DealService dealService;

    @Override
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/testUserLogin")
    public String testUserLogin() {
        System.out.println("logginIn");
        try {
            UsernamePasswordAuthenticationToken authReq
                    = new UsernamePasswordAuthenticationToken("bizbedu@gmail.com","adminPass22");
            Authentication auth = authenticationManager.authenticate(authReq);
            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(auth);
            return "redirect:/";
        }catch (Exception e){
            System.out.println(e.getMessage());
            return "redirect:/login?failed";
        }

    }

    @Override
    @GetMapping("/")
    public String showIndexPage(Model model) {
        model.addAttribute("productCategories", productCategoryService.getAll());
        List<Deal> deals = dealService.getAll(1, 9, "endDate", "asc").getContent();
        model.addAttribute("deals", deals);
        return findPaginated(1, "name", "asc",  model, "/index");
    }

    @GetMapping("/product/{productId}")
    @Override
    public String showProductPage(Model model, @PathVariable Long productId) {
        Optional<Product> product = productService.getById(productId);

        if (product.isEmpty()){
            return "/index";
        }

        model.addAttribute("product", product.get());
        model.addAttribute("productCategories", productCategoryService.getAll());
        return "/product";
    }

    @GetMapping("/deals")
    @Override
    public String showDealsPage(Model model) {
        model.addAttribute("productCategories", productCategoryService.getAll());
        return null;
    }

    @Override
    public String showDealPage(Model model, Long dealId) {
        return null;
    }

    @Override
    public String showCartPage(Model model) {
        return null;
    }

    @Override
    public String showCheckoutPage(Model model) {
        return null;
    }


    @GetMapping("page/{pageNo}")
    @Override
    public String findPaginated(int pageNo, String sortField, String sortDir, Model model, String returnPage) {
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
        return returnPage;
    }
}
