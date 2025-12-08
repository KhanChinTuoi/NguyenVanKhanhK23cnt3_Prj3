package com.example.cuahang.controller;

import com.example.cuahang.service.SanPhamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class SanPhamController {

    private final SanPhamService sanPhamService;

    @GetMapping("/")
    public String danhSachSanPham(Model model,
                                  @RequestParam(defaultValue = "0") int page) {
        model.addAttribute("ds", sanPhamService.getAll(page));
        model.addAttribute("currentPage", page);
        return "khach_hang/danh_sach_san_pham";
    }
}
