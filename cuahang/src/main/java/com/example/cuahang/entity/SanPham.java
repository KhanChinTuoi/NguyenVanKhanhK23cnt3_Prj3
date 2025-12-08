package com.example.cuahang.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "san_pham")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class SanPham {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String ten;

    private Double gia;

    private Integer soLuong;

    private String anh;

    @ManyToOne
    @JoinColumn(name = "loai_id")
    private LoaiSanPham loai;
}
