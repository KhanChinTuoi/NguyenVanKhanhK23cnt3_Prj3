package com.example.lab06.repository;

import com.example.lab06.entity.NvkAuthor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NvkAuthorRepository extends JpaRepository<NvkAuthor, Long> {

}
