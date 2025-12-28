package com.example.cuahang.service;

import com.example.cuahang.entity.NvkProduct;
import java.util.List;

public interface NvkProductService {
    List<NvkProduct> findAll();
    NvkProduct findById(Integer id);
    NvkProduct save(NvkProduct sp);
    void deleteById(Integer id);
    List<NvkProduct> findByDanhMuc(Integer maDMSP);
    List<NvkProduct> search(String q);
}