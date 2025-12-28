package com.example.cuahang.controller;

import com.example.cuahang.service.NvkProductService;
import com.example.cuahang.service.NvkDanhMucService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NvkHomeController {

    @Autowired
    private NvkProductService spService;

    @Autowired
    private NvkDanhMucService dmService;

    @GetMapping({"/", "/index"})
    public String index(Model model) {
        model.addAttribute("products", spService.findAll());
        model.addAttribute("dms", dmService.findAll());
        return "index";
    }
}
