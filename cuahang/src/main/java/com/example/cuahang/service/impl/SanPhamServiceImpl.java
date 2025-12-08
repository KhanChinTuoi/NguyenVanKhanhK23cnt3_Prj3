package com.example.cuahang.service.impl;

import com.example.cuahang.entity.SanPham;
import com.example.cuahang.repository.SanPhamRepository;
import com.example.cuahang.service.SanPhamService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SanPhamServiceImpl implements SanPhamService {

    private final SanPhamRepository repo;

    @Override
    public Page<SanPham> getAll(int page) {
        return repo.findAll(PageRequest.of(page, 10));
    }

    @Override
    public SanPham getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public SanPham save(SanPham sanPham) {
        return repo.save(sanPham);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
