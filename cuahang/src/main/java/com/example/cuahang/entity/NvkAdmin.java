package com.example.cuahang.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "admin")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NvkAdmin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_admin")
    private Integer maAdmin;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "ho_ten")
    private String hoTen;
}
