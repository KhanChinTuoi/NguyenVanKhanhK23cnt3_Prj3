package com.example.cuahang.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "chitiethoadon")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NvkCTHoaDon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaCTHD")
    private Integer maCTHD;

    @ManyToOne
    @JoinColumn(name = "ma_hd")
    private NvkHoaDon hoaDon;

    @ManyToOne
    @JoinColumn(name = "MaSP")
    private NvkProduct sanPham;

    @Column(name = "so_luong")
    private Integer soLuong = 0;

    @Column(name = "don_gia")
    private Double donGia = 0.0;
}