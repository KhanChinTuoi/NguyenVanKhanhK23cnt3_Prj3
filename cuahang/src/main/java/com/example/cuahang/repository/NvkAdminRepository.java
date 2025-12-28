package com.example.cuahang.repository;

import com.example.cuahang.entity.NvkAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NvkAdminRepository extends JpaRepository<NvkAdmin, Integer> {
    Optional<NvkAdmin> findByUsername(String username);
}
