package com.example.cuahang.controller;

import com.example.cuahang.entity.NvkProduct;
import com.example.cuahang.repository.NvkProductRepository;
import com.example.cuahang.repository.NvkDanhMucRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.nio.file.*;

@Controller
@RequestMapping("/admin/products")
public class NvkProductAdminController {

    @Autowired private NvkProductRepository spRepo;
    @Autowired private NvkDanhMucRepository dmRepo;

    @GetMapping
    public String list(Model model){
        model.addAttribute("products", spRepo.findAll());
        return "admin/products/list";
    }

    @GetMapping("/create")
    public String createForm(Model model){
        model.addAttribute("maSP", new NvkProduct());
        model.addAttribute("dms", dmRepo.findAll());
        return "admin/products/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("maSP") NvkProduct product, @RequestParam("imageFile") MultipartFile file){
        try {
            if(file != null && !file.isEmpty()){
                String uploadDir = System.getProperty("user.dir") + "/uploads/";
                File dir = new File(uploadDir);
                if (!dir.exists()) dir.mkdirs();

                String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path path = Paths.get(uploadDir + filename);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

                product.setHinhAnh(filename);
            } else if (product.getMaSP() != null) {
                // Giữ ảnh cũ nếu edit mà không chọn ảnh mới
                NvkProduct old = spRepo.findById(product.getMaSP()).orElse(null);
                if(old != null) product.setHinhAnh(old.getHinhAnh());
            }
            spRepo.save(product);
        } catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/admin/products";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){
        model.addAttribute("maSP", spRepo.findById(id).orElse(new NvkProduct()));
        model.addAttribute("dms", dmRepo.findAll());
        return "admin/products/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){
        spRepo.deleteById(id);
        return "redirect:/admin/products";
    }
}