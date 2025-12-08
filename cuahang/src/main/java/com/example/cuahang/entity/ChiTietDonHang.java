package com.example.cuahang.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "chi_tiet_don_hang")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ChiTietDonHang {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "san_pham_id")
    private SanPham sanPham;

    private Integer soLuong;
    private Double gia;

    @ManyToOne
    @JoinColumn(name = "don_hang_id")
    private DonHang donHang;
}
