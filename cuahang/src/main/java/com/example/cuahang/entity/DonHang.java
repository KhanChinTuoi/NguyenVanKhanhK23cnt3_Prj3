package com.example.cuahang.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "don_hang")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class DonHang {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tenKhach;
    private String diaChi;
    private String sdt;
    private Double tongTien;
    private LocalDateTime ngayTao;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "donHang")
    private List<ChiTietDonHang> items;
}
