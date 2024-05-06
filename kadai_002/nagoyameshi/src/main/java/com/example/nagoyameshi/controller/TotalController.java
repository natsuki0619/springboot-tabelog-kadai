package com.example.nagoyameshi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.nagoyameshi.service.ReservationService;
import com.example.nagoyameshi.service.UserService;

@Controller
@RequestMapping("/admin/total")
public class TotalController {
    private final UserService userService;
    private final ReservationService reservationService;

    public TotalController(UserService userService, ReservationService reservationService) {
        this.userService = userService;
        this.reservationService = reservationService;
    }

    @GetMapping
    public String index(Model model) {
        int generalUserCount = userService.getGeneralUserCount();
        int premiumUsercount = userService.getPremiumUserCount();
        long totalReservationCount = reservationService.getTotalReservationCount();

        model.addAttribute("generalUserCount", generalUserCount);
        model.addAttribute("premiumUsercount", premiumUsercount);
        model.addAttribute("totalReservationCount", totalReservationCount);
        return "admin/total/index";
    }
}