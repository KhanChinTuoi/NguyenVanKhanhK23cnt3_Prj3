package com.example.cuahang.repository;

import com.example.cuahang.entity.NvkKhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NvkKhachHangRepository extends JpaRepository<NvkKhachHang, Integer> {
    Optional<NvkKhachHang> findByUsername(String username);
    List<NvkKhachHang> findByHoTenContainingOrEmailContaining(String hoTen, String email);
}