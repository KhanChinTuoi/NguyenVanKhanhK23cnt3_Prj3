package com.example.lab06.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.List;
import java.util.ArrayList;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "nvk_book")
public class NvkBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long nvkId;

    String nvkCode;
    String nvkName;
    String nvkDescription;
    String nvkImgUrl;
    Integer nvkQuantity;
    Double nvkPrice;
    Boolean nvkActive;

    @ManyToMany
    @JoinTable(
            name = "nvk_book_author",
            joinColumns = @JoinColumn(name = "nvkBookId"),
            inverseJoinColumns = @JoinColumn(name = "nvkAuthorId")
    )
    List<NvkAuthor> nvkAuthors = new ArrayList<>();
}
