package com.example.cuahang.entity;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "hoa_don")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class HoaDon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime ngayLap;

    private Double tongTien;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
