package com.example.nagoyameshi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    /*@GetMapping("/admin/index")
    public String admin(Model model) {
        return "admin/index";
    }*/

    @GetMapping("/admin/shops/index")
    public String shops(Model model) {
        return "admin/shops/index";
    }

    @GetMapping("/admin/category/index")
    public String category(Model model) {
        return "admin/category/index";
    }

    @GetMapping("/company")
    public String profile(Model model) {
        return "company";
    }
}