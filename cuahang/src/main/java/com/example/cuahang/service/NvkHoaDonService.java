package com.example.cuahang.service;

import com.example.cuahang.entity.NvkCTHoaDon;
import com.example.cuahang.entity.NvkHoaDon;
import java.util.List;

public interface NvkHoaDonService {
    List<NvkHoaDon> findAll();
    NvkHoaDon findById(Integer id);
    NvkHoaDon save(NvkHoaDon hd);
    List<NvkHoaDon> findByCustomerName(String name);
    List<NvkCTHoaDon> findDetailsByOrderId(Integer id);
    Double calculateActualTotal(List<NvkCTHoaDon> details);
    void updateStatus(Integer maHD, Integer status); // Đảm bảo có dòng này
}