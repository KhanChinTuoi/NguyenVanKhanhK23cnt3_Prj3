package com.example.cuahang.controller;

import com.example.cuahang.entity.NvkProduct;
import com.example.cuahang.service.NvkProductService;
import com.example.cuahang.service.NvkDanhMucService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class NvkProductController {

    @Autowired
    private NvkProductService spService;

    @Autowired
    private NvkDanhMucService dmService;

    // Trang danh sách sản phẩm
    @GetMapping
    public String list(@RequestParam(required=false) Integer cat, Model model) {
        List<NvkProduct> list = (cat != null) ? spService.findByDanhMuc(cat) : spService.findAll();
        model.addAttribute("products", list);
        model.addAttribute("dms", dmService.findAll());
        return "products/list";
    }

    // Sửa lại path để khớp với link trong cart.html: /products/detail/{id}
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        NvkProduct sp = spService.findById(id);
        if (sp == null) return "redirect:/products";
        model.addAttribute("product", sp);
        return "products/detail";
    }

    @GetMapping("/search")
    public String search(@RequestParam String q, Model model) {
        model.addAttribute("q", q);
        model.addAttribute("products", spService.search(q));
        model.addAttribute("dms", dmService.findAll());
        return "products/list";
    }
}