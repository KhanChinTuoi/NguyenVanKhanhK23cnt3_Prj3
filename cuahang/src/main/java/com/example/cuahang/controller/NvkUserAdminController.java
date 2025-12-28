package com.example.cuahang.controller;

import com.example.cuahang.entity.NvkKhachHang;
import com.example.cuahang.repository.NvkKhachHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
public class NvkUserAdminController {

    @Autowired
    private NvkKhachHangRepository khRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("")
    public String index(Model model, @RequestParam(name = "keyword", required = false) String keyword) {
        List<NvkKhachHang> list = (keyword != null && !keyword.isEmpty())
                ? khRepo.findByHoTenContainingOrEmailContaining(keyword, keyword)
                : khRepo.findAll();
        model.addAttribute("users", list);
        model.addAttribute("keyword", keyword);
        return "admin/users/index";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute NvkKhachHang kh, RedirectAttributes ra) {
        try {
            kh.setPassword(passwordEncoder.encode(kh.getPassword()));
            khRepo.save(kh);
            ra.addFlashAttribute("success", "Thêm khách hàng thành công!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Lỗi: Username đã tồn tại!");
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/edit/{id}")
    @ResponseBody
    public NvkKhachHang getOne(@PathVariable("id") Integer id) {
        return khRepo.findById(id).orElse(null);
    }

    @PostMapping("/update")
    public String update(@ModelAttribute NvkKhachHang formUser, RedirectAttributes ra) {
        try {
            // 1. Tìm dữ liệu thật đang có trong Database
            NvkKhachHang dbUser = khRepo.findById(formUser.getMaKh()).orElse(null);

            if (dbUser != null) {
                // 2. Chỉ cập nhật những trường cho phép sửa từ Form
                dbUser.setHoTen(formUser.getHoTen());
                dbUser.setEmail(formUser.getEmail());
                dbUser.setSdt(formUser.getSdt());
                dbUser.setDiaChi(formUser.getDiaChi());

                khRepo.save(dbUser);
                ra.addFlashAttribute("success", "Cập nhật thành công!");
            }
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Có lỗi xảy ra khi cập nhật!");
        }
        return "redirect:/admin/users";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            khRepo.deleteById(id);
            ra.addFlashAttribute("success", "Đã xóa khách hàng!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Không thể xóa khách hàng đang có đơn hàng!");
        }
        return "redirect:/admin/users";
    }
}