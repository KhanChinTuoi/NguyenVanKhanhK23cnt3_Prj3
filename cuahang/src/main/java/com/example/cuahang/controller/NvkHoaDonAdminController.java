package com.example.cuahang.controller;

import com.example.cuahang.entity.NvkCTHoaDon;
import com.example.cuahang.entity.NvkHoaDon;
import com.example.cuahang.service.NvkHoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/orders")
public class NvkHoaDonAdminController {

    @Autowired
    private NvkHoaDonService hdService;

    @GetMapping("")
    public String list(@RequestParam(value = "search", required = false) String search, Model model) {
        List<NvkHoaDon> orders = (search != null && !search.isEmpty())
                ? hdService.findByCustomerName(search)
                : hdService.findAll();
        model.addAttribute("orders", orders);
        model.addAttribute("searchValue", search);
        return "admin/orders/list";
    }

    // Sửa mapping từ /detail/{id} thành /view/{id} để khớp với file list.html của bạn
    @GetMapping("/view/{id}")
    public String view(@PathVariable Integer id, Model model) {
        NvkHoaDon order = hdService.findById(id);
        if (order == null) return "redirect:/admin/orders";

        List<NvkCTHoaDon> details = hdService.findDetailsByOrderId(id);
        Double calculatedTotal = hdService.calculateActualTotal(details);

        // Gửi các biến sang HTML với tên chuẩn
        model.addAttribute("order", order);
        model.addAttribute("details", details);
        model.addAttribute("calculatedTotal", calculatedTotal);
        return "admin/orders/detail";
    }
}