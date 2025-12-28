package com.example.cuahang.repository;

import com.example.cuahang.entity.NvkDanhMuc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NvkDanhMucRepository extends JpaRepository<NvkDanhMuc, Integer> {
}