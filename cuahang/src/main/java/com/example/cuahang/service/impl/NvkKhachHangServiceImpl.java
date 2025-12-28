package com.example.cuahang.service.impl;

import com.example.cuahang.entity.NvkKhachHang;
import com.example.cuahang.repository.NvkKhachHangRepository;
import com.example.cuahang.service.NvkKhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NvkKhachHangServiceImpl implements NvkKhachHangService {

    @Autowired
    private NvkKhachHangRepository khRepo;

    @Override
    public List<NvkKhachHang> findAll() {
        return khRepo.findAll();
    }

    @Override
    public NvkKhachHang findById(Integer id) {
        return khRepo.findById(id).orElse(null);
    }

    @Override
    public NvkKhachHang save(NvkKhachHang kh) {
        return khRepo.save(kh);
    }

    @Override
    public void delete(Integer id) {
        khRepo.deleteById(id);
    }
}