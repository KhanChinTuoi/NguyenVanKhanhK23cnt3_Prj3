package com.example.cuahang.controller;

import com.example.cuahang.entity.NvkDanhMuc;
import com.example.cuahang.repository.NvkDanhMucRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/categories")
public class NvkDanhMucAdminController {

    @Autowired
    private NvkDanhMucRepository repo;

    @GetMapping
    public String list(Model model) {
        List<NvkDanhMuc> dms = repo.findAll();
        model.addAttribute("dms", dms);
        return "admin/danhmuc/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        // Gửi đối tượng rỗng sang View với tên "dm" để dùng th:object
        model.addAttribute("dm", new NvkDanhMuc());
        return "admin/danhmuc/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("dm") NvkDanhMuc dm, RedirectAttributes ra) {
        try {
            // repo.save() sẽ tự động kiểm tra:
            // Nếu maDMSP đã tồn tại thì nó UPDATE, nếu chưa có thì nó INSERT
            repo.save(dm);
            ra.addFlashAttribute("success", "Lưu danh mục thành công!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Lỗi khi lưu dữ liệu: " + e.getMessage());
        }
        return "redirect:/admin/categories";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        return repo.findById(id).map(dm -> {
            model.addAttribute("dm", dm);
            return "admin/danhmuc/form";
        }).orElseGet(() -> {
            ra.addFlashAttribute("error", "Không tìm thấy danh mục ID: " + id);
            return "redirect:/admin/categories";
        });
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            if (repo.existsById(id)) {
                repo.deleteById(id);
                ra.addFlashAttribute("success", "Đã xóa danh mục thành công!");
            } else {
                ra.addFlashAttribute("error", "Danh mục không tồn tại để xóa.");
            }
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Không thể xóa danh mục này (có thể đang có sản phẩm thuộc danh mục này).");
        }
        return "redirect:/admin/categories";
    }
}