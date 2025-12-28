package com.example.cuahang.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sanpham")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NvkProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maSP")
    private Integer maSP;

    @Column(name = "tenSP", nullable = false)
    private String tenSP;

    @Column(name = "hinhAnh")
    private String hinhAnh;

    @Column(name = "gia")
    private Double gia;

    @Column(name = "motaSP", columnDefinition = "TEXT")
    private String motaSP;

    @Column(name = "so_luong")
    private Integer soLuong = 0; // Luôn để mặc định là 0 để tránh lỗi Null

    @ManyToOne
    @JoinColumn(name = "maDMSP")
    private NvkDanhMuc danhMuc;
}