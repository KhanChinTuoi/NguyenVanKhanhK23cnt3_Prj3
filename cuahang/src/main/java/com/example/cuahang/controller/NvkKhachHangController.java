package com.example.cuahang.controller;

import com.example.cuahang.entity.NvkKhachHang;
import com.example.cuahang.repository.NvkKhachHangRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/user")
public class NvkKhachHangController {

    @Autowired private NvkKhachHangRepository khRepo;
    @Autowired private PasswordEncoder passwordEncoder;

    // --- ĐĂNG KÝ ---
    @GetMapping("/register")
    public String registerPage() { return "user/register"; }

    @PostMapping("/register")
    public String doRegister(@RequestParam String hoTen, @RequestParam String sdt,
                             @RequestParam String diaChi, @RequestParam String email,
                             @RequestParam String username, @RequestParam String password, Model model) {
        if (khRepo.findByUsername(username.trim()).isPresent()) {
            model.addAttribute("error", "Tên đăng nhập đã tồn tại!");
            return "user/register";
        }
        try {
            NvkKhachHang kh = new NvkKhachHang();
            kh.setHoTen(hoTen.trim()); kh.setSdt(sdt.trim());
            kh.setDiaChi(diaChi.trim()); kh.setEmail(email.trim());
            kh.setUsername(username.trim());
            kh.setPassword(passwordEncoder.encode(password.trim()));
            khRepo.save(kh);
            return "redirect:/user/login?registerSuccess=true";
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi: " + e.getMessage());
            return "user/register";
        }
    }

    // --- ĐĂNG NHẬP ---
    @GetMapping("/login")
    public String loginPage(HttpSession session) {
        if (session.getAttribute("USER") != null) return "redirect:/";
        return "user/login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String username, @RequestParam String password,
                          HttpSession session, Model model) {
        Optional<NvkKhachHang> khOpt = khRepo.findByUsername(username.trim());
        if (khOpt.isPresent() && passwordEncoder.matches(password.trim(), khOpt.get().getPassword())) {
            NvkKhachHang kh = khOpt.get();
            kh.setPassword(null); // Bảo mật: xóa pass trước khi lưu session
            session.setAttribute("USER", kh);
            return "redirect:/";
        }
        model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng!");
        return "user/login";
    }

    // --- ĐĂNG XUẤT ---
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/user/login";
    }

    // --- THÔNG TIN CÁ NHÂN (PROFILE) ---
    @GetMapping("/profile")
    public String viewProfile(HttpSession session, Model model) {
        NvkKhachHang user = (NvkKhachHang) session.getAttribute("USER");
        if (user == null) return "redirect:/user/login";
        model.addAttribute("user", khRepo.findById(user.getMaKh()).get());
        return "user/profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute NvkKhachHang userUpdate, HttpSession session, RedirectAttributes ra) {
        NvkKhachHang sessionUser = (NvkKhachHang) session.getAttribute("USER");
        try {
            NvkKhachHang kh = khRepo.findById(sessionUser.getMaKh()).orElseThrow();
            kh.setHoTen(userUpdate.getHoTen());
            kh.setSdt(userUpdate.getSdt());
            kh.setEmail(userUpdate.getEmail());
            kh.setDiaChi(userUpdate.getDiaChi());
            khRepo.save(kh);
            session.setAttribute("USER", kh);
            ra.addFlashAttribute("success", "Cập nhật thành công!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Lỗi cập nhật!");
        }
        return "redirect:/user/profile";
    }

    // --- ĐỔI MẬT KHẨU ---
    @PostMapping("/profile/change-password")
    public String changePassword(@RequestParam String oldPassword, @RequestParam String newPassword,
                                 @RequestParam String confirmPassword, HttpSession session, RedirectAttributes ra) {
        NvkKhachHang sessionUser = (NvkKhachHang) session.getAttribute("USER");
        try {
            NvkKhachHang kh = khRepo.findById(sessionUser.getMaKh()).orElseThrow();
            if (!passwordEncoder.matches(oldPassword, kh.getPassword())) {
                ra.addFlashAttribute("pwdError", "Mật khẩu cũ không đúng!");
            } else if (!newPassword.equals(confirmPassword)) {
                ra.addFlashAttribute("pwdError", "Mật khẩu mới không khớp!");
            } else {
                kh.setPassword(passwordEncoder.encode(newPassword));
                khRepo.save(kh);
                ra.addFlashAttribute("pwdSuccess", "Đổi mật khẩu thành công!");
            }
        } catch (Exception e) {
            ra.addFlashAttribute("pwdError", "Lỗi hệ thống!");
        }
        return "redirect:/user/profile#security";
    }
}