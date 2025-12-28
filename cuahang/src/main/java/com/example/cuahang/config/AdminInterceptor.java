package com.example.cuahang.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Nếu session không có ADMIN, chặn lại và đá về trang login
        if (request.getSession().getAttribute("ADMIN") == null) {
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return false;
        }
        return true; // Cho phép đi tiếp
    }
}