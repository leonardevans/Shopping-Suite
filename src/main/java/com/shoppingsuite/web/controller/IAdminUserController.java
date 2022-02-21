package com.shoppingsuite.web.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface IAdminUserController {
    public String showUsers(Model model);
    public String findPaginated(int pageNo, String sortField, String sortDir, Model model);
}
