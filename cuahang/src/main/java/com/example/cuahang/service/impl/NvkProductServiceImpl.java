package com.example.cuahang.service.impl;

import com.example.cuahang.entity.NvkProduct;
import com.example.cuahang.repository.NvkProductRepository;
import com.example.cuahang.service.NvkProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NvkProductServiceImpl implements NvkProductService {

    @Autowired
    private NvkProductRepository repo;

    @Override
    public List<NvkProduct> findAll() {
        return repo.findAll();
    }

    @Override
    public NvkProduct findById(Integer id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public NvkProduct save(NvkProduct product) {
        // Method này sẽ lưu vĩnh viễn số lượng tồn kho vào DB mỗi khi gọi
        return repo.save(product);
    }

    @Override
    public void deleteById(Integer id) {
        repo.deleteById(id); // Hoàn thiện method này
    }

    @Override
    public List<NvkProduct> findByDanhMuc(Integer maDMSP) {
        // SỬA LỖI HÌNH 4: Đảm bảo repo đã được @Autowired và method tồn tại trong Repository
        return repo.findByDanhMuc_MaDMSP(maDMSP);
    }

    @Override
    public List<NvkProduct> search(String q) {
        // Thay vì trả về List.of(), hãy gọi Repository để tìm kiếm thật
        return repo.findByTenSPContainingIgnoreCase(q);
    }
}