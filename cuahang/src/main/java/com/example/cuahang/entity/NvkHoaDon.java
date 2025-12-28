package com.example.cuahang.entity;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Date;

@Entity
@Table(name = "hoadon")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NvkHoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_hd")
    private Integer maHD;

    @ManyToOne
    @JoinColumn(name = "ma_kh")
    private NvkKhachHang khachHang;

    @Column(name = "ngay_lap", nullable = false)
    private Date ngayLap;

    @Column(name = "tong_tien")
    private Double tongTien;

    private Integer trangThai = 0; // Giá trị mặc định là 0

    @PrePersist
    protected void onCreate() {
        if (this.ngayLap == null) {
            this.ngayLap = new Date(System.currentTimeMillis());
        }
    }
}