package com.example.cuahang.service;

import com.example.cuahang.entity.NvkDanhMuc;
import java.util.List;

public interface NvkDanhMucService {
    List<NvkDanhMuc> findAll();
    NvkDanhMuc findById(Integer id);
    NvkDanhMuc save(NvkDanhMuc dm);
    void delete(Integer id);
}
