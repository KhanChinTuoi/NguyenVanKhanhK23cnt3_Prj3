package com.example.cuahang.controller;

import com.example.cuahang.entity.NvkAdmin;
import com.example.cuahang.repository.NvkAdminRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class NvkAdminController {

    @Autowired
    private NvkAdminRepository adminRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 1. TRANG DASHBOARD (CHÍNH)
     */
    @GetMapping({"", "/"})
    public String adminIndex(HttpSession session) {
        // Nếu chưa có session ADMIN, không cho vào dashboard
        if (session.getAttribute("ADMIN") == null) {
            return "redirect:/admin/login";
        }
        return "admin/index";
    }

    /**
     * 2. TRANG LOGIN
     */
    @GetMapping("/login")
    public String loginPage(HttpSession session,
                            @RequestParam(value = "success", required = false) String success,
                            Model model) {
        // Nếu đã login rồi, vào thẳng trang chủ admin
        if (session.getAttribute("ADMIN") != null) {
            return "redirect:/admin";
        }
        // Nếu có tham số ?success từ trang register gửi qua
        if (success != null) {
            model.addAttribute("message", "Đăng ký thành công! Vui lòng đăng nhập.");
        }
        return "admin/login";
    }

    /**
     * 3. XỬ LÝ LOGIN
     */
    @PostMapping("/login")
    public String doLogin(@RequestParam String username,
                          @RequestParam String password,
                          HttpSession session,
                          Model model) {

        // Tìm admin theo username
        Optional<NvkAdmin> adminOpt = adminRepo.findByUsername(username.trim());

        if (adminOpt.isPresent()) {
            NvkAdmin admin = adminOpt.get();
            // So sánh password nhập vào với password hash trong DB
            if (passwordEncoder.matches(password.trim(), admin.getPassword())) {
                session.setAttribute("ADMIN", admin);
                return "redirect:/admin";
            }
        }

        model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu không chính xác!");
        return "admin/login";
    }

    /**
     * 4. TRANG REGISTER
     */
    @GetMapping("/register")
    public String registerPage() {
        return "admin/register";
    }

    /**
     * 5. XỬ LÝ REGISTER
     */
    @PostMapping("/register")
    public String doRegister(@RequestParam String username,
                             @RequestParam String password,
                             @RequestParam String hoTen,
                             Model model) {

        // Kiểm tra xem username đã tồn tại chưa
        if (adminRepo.findByUsername(username.trim()).isPresent()) {
            model.addAttribute("error", "Tên đăng nhập '" + username + "' đã tồn tại!");
            return "admin/register";
        }

        try {
            NvkAdmin admin = new NvkAdmin();
            admin.setUsername(username.trim());
            // Mã hóa mật khẩu
            admin.setPassword(passwordEncoder.encode(password.trim()));
            admin.setHoTen(hoTen.trim());

            adminRepo.save(admin);

            // Redirect về trang login kèm theo thông báo thành công
            return "redirect:/admin/login?success=true";
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi hệ thống: " + e.getMessage());
            return "admin/register";
        }
    }

    /**
     * 6. ĐĂNG XUẤT
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("ADMIN");
        session.invalidate(); // Xóa sạch session
        return "redirect:/admin/login";
    }
}