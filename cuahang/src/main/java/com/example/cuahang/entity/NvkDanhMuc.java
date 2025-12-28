package com.example.cuahang.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "danhmucsp")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NvkDanhMuc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaDMSP")
    private Integer maDMSP;

    @Column(name = "TenDMSP", nullable = false, length = 100)
    private String tenDMSP;

    @Column(name = "MoTa")
    private String moTa;
}