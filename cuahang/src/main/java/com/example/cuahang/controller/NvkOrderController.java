package com.example.cuahang.controller;

import com.example.cuahang.entity.NvkCTHoaDon;
import com.example.cuahang.entity.NvkHoaDon;
import com.example.cuahang.entity.NvkKhachHang;
import com.example.cuahang.repository.NvkCTHoaDonRepository;
import com.example.cuahang.repository.NvkHoaDonRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class NvkOrderController {

    @Autowired
    private NvkHoaDonRepository hdRepo;

    @Autowired
    private NvkCTHoaDonRepository ctRepo;

    // 1. Hiển thị danh sách lịch sử mua hàng (Sắp xếp mới nhất lên đầu)
    @GetMapping("/history")
    public String history(HttpSession session, Model model, RedirectAttributes ra) {
        NvkKhachHang user = (NvkKhachHang) session.getAttribute("USER");

        if (user == null) {
            ra.addFlashAttribute("error", "Vui lòng đăng nhập để xem lịch sử đơn hàng!");
            return "redirect:/user/login";
        }

        // Sử dụng hàm đã viết lại trong Repository để có thứ tự đơn hàng mới nhất
        List<NvkHoaDon> list = hdRepo.findByKhachHang_MaKhOrderByNgayLapDesc(user.getMaKh());

        model.addAttribute("hoadons", list);
        return "orders/history";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, HttpSession session, Model model, RedirectAttributes ra) {
        NvkKhachHang user = (NvkKhachHang) session.getAttribute("USER");

        if (user == null) {
            return "redirect:/user/login";
        }

        NvkHoaDon hd = hdRepo.findById(id).orElse(null);

        // Kiểm tra bảo mật đơn hàng
        if (hd == null || !hd.getKhachHang().getMaKh().equals(user.getMaKh())) {
            ra.addFlashAttribute("error", "Đơn hàng không tồn tại hoặc bạn không có quyền xem!");
            return "redirect:/orders/history";
        }

        // Lấy danh sách chi tiết sản phẩm
        List<NvkCTHoaDon> details = ctRepo.findByHoaDon_MaHD(id);

        // TÍNH TỔNG TIỀN (Quan trọng để hiện ở Tổng thanh toán)
        Double calculatedTotal = details.stream()
                .mapToDouble(d -> (d.getSoLuong() != null ? d.getSoLuong() : 0) * (d.getDonGia() != null ? d.getDonGia() : 0))
                .sum();

        // Đồng bộ tên biến với file detail.html
        model.addAttribute("order", hd); // Đổi từ "hoadon" thành "order"
        model.addAttribute("details", details);
        model.addAttribute("calculatedTotal", calculatedTotal); // Thêm dòng này

        return "orders/detail";
    }
}