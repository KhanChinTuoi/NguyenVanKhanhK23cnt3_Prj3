package com.example.cuahang.controller;

import com.example.cuahang.entity.*;
import com.example.cuahang.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping("/checkout")
public class NvkCheckoutController {

    @Autowired private NvkHoaDonRepository hdRepo;
    @Autowired private NvkCTHoaDonRepository cthdRepo;
    @Autowired private NvkProductRepository spRepo;
    @Autowired private NvkKhachHangRepository khRepo;

    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    public String checkout(@RequestParam Integer customerId, HttpSession session, RedirectAttributes ra) {
        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("CART");

        if (cart == null || cart.isEmpty()) {
            ra.addFlashAttribute("error", "Giỏ hàng trống!");
            return "redirect:/cart";
        }

        try {
            // 1. Kiểm tra khách hàng
            NvkKhachHang kh = khRepo.findById(customerId)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy thông tin khách hàng!"));

            // 2. Tạo hóa đơn tạm
            NvkHoaDon hd = new NvkHoaDon();
            hd.setKhachHang(kh);
            hd.setNgayLap(new java.sql.Date(System.currentTimeMillis()));
            hd.setTrangThai(0);
            hd.setTongTien(0.0); // Khởi tạo 0
            NvkHoaDon savedHd = hdRepo.save(hd);

            double total = 0;
            for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
                NvkProduct sp = spRepo.findById(entry.getKey())
                        .orElseThrow(() -> new RuntimeException("Sản phẩm ID " + entry.getKey() + " không tồn tại!"));

                // 3. Kiểm tra kho
                if (sp.getSoLuong() < entry.getValue())
                    throw new RuntimeException("Sản phẩm [" + sp.getTenSP() + "] vừa hết hàng hoặc không đủ số lượng!");

                // 4. Trừ kho
                sp.setSoLuong(sp.getSoLuong() - entry.getValue());
                spRepo.save(sp);

                // 5. Lưu chi tiết
                NvkCTHoaDon ct = new NvkCTHoaDon();
                ct.setHoaDon(savedHd);
                ct.setSanPham(sp);
                ct.setSoLuong(entry.getValue());
                ct.setDonGia(sp.getGia());
                cthdRepo.save(ct);

                total += sp.getGia() * entry.getValue();
            }

            // 6. Cập nhật tổng tiền cuối cùng
            savedHd.setTongTien(total);
            hdRepo.save(savedHd);

            // 7. Thành công: Xóa giỏ hàng
            session.removeAttribute("CART");
            return "redirect:/checkout/success";

        } catch (Exception e) {
            // QUAN TRỌNG: Đánh dấu Rollback thủ công vì đã catch Exception
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

            ra.addFlashAttribute("error", "Thanh toán thất bại: " + e.getMessage());
            return "redirect:/cart";
        }
    }

    @GetMapping("/success")
    public String success() {
        return "success";
    }
}