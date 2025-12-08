package com.example.cuahang.controller;

import com.example.cuahang.entity.SanPham;
import com.example.cuahang.service.SanPhamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

@Controller
@RequestMapping("/admin/san-pham")
@RequiredArgsConstructor
public class AdminProductController {

    private final SanPhamService sanPhamService;

    private final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("ds", sanPhamService.getAll(0));
        return "admin/sanpham_index";
    }

    @GetMapping("/them")
    public String them(Model model) {
        model.addAttribute("sp", new SanPham());
        return "admin/sanpham_form";
    }

    @PostMapping("/save")
    public String save(
            SanPham sp,
            @RequestParam("fileAnh") MultipartFile file
    ) throws IOException {

        // Nếu có file upload thì lưu file
        if (!file.isEmpty()) {

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path path = Paths.get(UPLOAD_DIR + fileName);

            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());

            sp.setAnh("/uploads/" + fileName);
        }

        sanPhamService.save(sp);
        return "redirect:/admin/san-pham";
    }

    @GetMapping("/sua/{id}")
    public String sua(@PathVariable Long id, Model model) {
        model.addAttribute("sp", sanPhamService.getById(id));
        return "admin/sanpham_form";
    }

    @GetMapping("/xoa/{id}")
    public String xoa(@PathVariable Long id) {
        sanPhamService.delete(id);
        return "redirect:/admin/san-pham";
    }
}
