package com.example.cuahang.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "khachhang")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NvkKhachHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_kh")
    private Integer maKh;

    @Column(name = "ho_ten")
    private String hoTen;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "sdt")
    private String sdt;

    @Column(name = "dia_chi")
    private String diaChi;
}