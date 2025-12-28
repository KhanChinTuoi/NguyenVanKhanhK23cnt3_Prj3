package com.example.cuahang.service;

import com.example.cuahang.entity.NvkKhachHang;
import java.util.List;

public interface NvkKhachHangService {
    List<NvkKhachHang> findAll();
    NvkKhachHang findById(Integer id);
    NvkKhachHang save(NvkKhachHang kh);
    void delete(Integer id);
}
