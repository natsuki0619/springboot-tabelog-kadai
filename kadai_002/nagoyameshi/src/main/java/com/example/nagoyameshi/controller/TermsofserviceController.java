package com.example.nagoyameshi.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.nagoyameshi.entity.Termsofservice;
import com.example.nagoyameshi.repository.TermsofserviceRepository;

@Controller
@RequestMapping("/terms")
public class TermsofserviceController {
    private final TermsofserviceRepository termsofserviceRepository;

    public TermsofserviceController(TermsofserviceRepository termsofserviceRepository) {
        this.termsofserviceRepository = termsofserviceRepository;
    }

    @GetMapping("/termsofservice")
    public String Termsofservice(Model model) {
        List<Termsofservice> termsofservice = termsofserviceRepository.findAll();

        model.addAttribute("termsofservice", termsofservice);

        return "terms/termsofservice";
    }
}