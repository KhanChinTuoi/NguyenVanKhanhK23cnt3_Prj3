package com.example.cuahang.service;

import com.example.cuahang.entity.SanPham;
import org.springframework.data.domain.Page;

public interface SanPhamService {
    Page<SanPham> getAll(int page);
    SanPham getById(Long id);
    SanPham save(SanPham sanPham);
    void delete(Long id);
}
