package com.example.lab06.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "nvk_author")
public class NvkAuthor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long nvkId;

    String nvkCode;
    String nvkName;
    String nvkDescription;
    String nvkImgUrl;
    String nvkEmail;
    String nvkPhone;
    String nvkAddress;
    Boolean nvkActive;

    @ManyToMany(mappedBy = "nvkAuthors")
    List<NvkBook> nvkBooks = new ArrayList<>();
}