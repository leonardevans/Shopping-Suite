package com.shoppingsuite.web.controller;

import com.shoppingsuite.persistence.dao.UserRepo;
import com.shoppingsuite.persistence.model.User;
import com.shoppingsuite.service.UserService;
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
@RequestMapping(value = {"/admin/users/*", "/admin/users"})
public class AdminUserController implements IAdminUserController{
    @Autowired
    UserRepo userRepo;

    @Autowired
    UserService userService;

    @GetMapping("")
    @Override
    public String showUsers(Model model) {

        return findPaginated(1, "firstName", "asc",  model);
    }

    @GetMapping("page/{pageNo}")
    @Override
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 1;

        Page<User> page = userService.getAll( pageNo, pageSize, sortField, sortDir);
        List< User > userList = page.getContent();

        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("users", userList);
        return "/admin/users";
    }
}
