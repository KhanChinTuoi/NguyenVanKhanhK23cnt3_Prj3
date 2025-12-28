package com.example.cuahang.repository;

import com.example.cuahang.entity.NvkProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NvkProductRepository extends JpaRepository<NvkProduct, Integer> {
    // Tìm theo danh mục (Đã có)
    List<NvkProduct> findByDanhMuc_MaDMSP(Integer maDMSP);

    // THÊM: Tìm kiếm sản phẩm theo tên để hỗ trợ method search trong Service
    List<NvkProduct> findByTenSPContainingIgnoreCase(String q);
}