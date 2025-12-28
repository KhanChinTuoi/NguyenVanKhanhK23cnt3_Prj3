package com.example.cuahang.repository;

import com.example.cuahang.entity.NvkCTHoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NvkCTHoaDonRepository extends JpaRepository<NvkCTHoaDon, Integer> {
    List<NvkCTHoaDon> findByHoaDon_MaHD(Integer maHD);
}
