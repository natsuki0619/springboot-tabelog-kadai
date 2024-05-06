package com.example.nagoyameshi.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.nagoyameshi.entity.Shop;
import com.example.nagoyameshi.repository.ProfileRepository;
import com.example.nagoyameshi.repository.ShopRepository;

@Controller
public class HomeController {
    private final ShopRepository shopRepository;
    private final ProfileRepository profileRepository;

    public HomeController(ShopRepository shopRepository, ProfileRepository profileRepository) {
        this.shopRepository = shopRepository;
        this.profileRepository = profileRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Shop> newShops = shopRepository.findTop10ByOrderByCreatedAtDesc();
        model.addAttribute("newShops", newShops);
        return "index";
    }

    @GetMapping("admin/index")

    public String admintop(Model model) {
        List<Shop> newShops = shopRepository.findTop10ByOrderByCreatedAtDesc();
        model.addAttribute("newShops", newShops);
        return "admin/index";
    }

    @GetMapping("/member/index")
    public String membertop(Model model) {
        List<Shop> newShops = shopRepository.findTop10ByOrderByCreatedAtDesc();
        model.addAttribute("newShops", newShops);
        return "member/index";
    }
}
