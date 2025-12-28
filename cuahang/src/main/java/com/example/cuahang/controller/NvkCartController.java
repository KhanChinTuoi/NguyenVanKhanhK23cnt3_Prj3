package com.example.cuahang.controller;

import com.example.cuahang.entity.NvkProduct;
import com.example.cuahang.service.NvkProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping("/cart")
public class NvkCartController {

    @Autowired
    private NvkProductService spService;

    // 1. Xem giỏ hàng
    @GetMapping
    public String viewCart(HttpSession session, Model model) {
        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("CART");
        List<Map<String, Object>> cartItems = new ArrayList<>();
        long totalMoney = 0;

        if (cart != null) {
            for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
                NvkProduct sp = spService.findById(entry.getKey());
                if (sp != null) {
                    Map<String, Object> item = new HashMap<>();
                    item.put("product", sp);
                    item.put("quantity", entry.getValue());
                    long subTotal = (long) (sp.getGia() * entry.getValue());
                    item.put("subTotal", subTotal);
                    cartItems.add(item);
                    totalMoney += subTotal;
                }
            }
        }
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalMoney", totalMoney);
        return "cart";
    }

    // 2. Thêm vào giỏ hàng (Hỗ trợ cả GET và POST để tránh lỗi 405)
    @RequestMapping(value = "/add/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String addToCart(@PathVariable("id") Integer id,
                            @RequestParam(name = "qty", defaultValue = "1") Integer qty,
                            HttpSession session,
                            RedirectAttributes ra) {

        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("CART");
        if (cart == null) cart = new HashMap<>();

        NvkProduct sp = spService.findById(id);
        if (sp != null) {
            int currentQty = cart.getOrDefault(id, 0);
            int newQty = currentQty + qty;

            if (newQty > sp.getSoLuong()) {
                ra.addFlashAttribute("error", "Sản phẩm không đủ số lượng trong kho!");
                return "redirect:/products";
            }
            cart.put(id, newQty);
            session.setAttribute("CART", cart);
            ra.addFlashAttribute("success", "Đã thêm vào giỏ hàng!");
        }
        return "redirect:/cart";
    }

    // 3. Cập nhật số lượng qua Ajax (Khi nhấn +/-)
    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<?> updateCart(@RequestParam("id") Integer id,
                                        @RequestParam("qty") Integer qty,
                                        HttpSession session) {
        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("CART");
        if (cart != null && cart.containsKey(id)) {
            NvkProduct sp = spService.findById(id);
            if (sp != null) {
                if (qty > sp.getSoLuong())
                    return ResponseEntity.badRequest().body("Trong kho chỉ còn " + sp.getSoLuong() + " sản phẩm.");

                if (qty <= 0) cart.remove(id);
                else cart.put(id, qty);
                session.setAttribute("CART", cart);
            }
        }
        return ResponseEntity.ok("Success");
    }

    // 4. Xóa sản phẩm khỏi giỏ
    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id, HttpSession session) {
        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("CART");
        if (cart != null) {
            cart.remove(id);
            session.setAttribute("CART", cart);
        }
        return "redirect:/cart";
    }
}