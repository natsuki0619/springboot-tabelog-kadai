package com.example.nagoyameshi.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.nagoyameshi.entity.Profile;
import com.example.nagoyameshi.repository.ProfileRepository;

@Controller
@RequestMapping("/company")
public class ProfileController {
    private final ProfileRepository profileRepository;

    public ProfileController(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        List<Profile> profile = profileRepository.findAll();

        model.addAttribute("profile", profile);

        return "company/profile";
    }
}