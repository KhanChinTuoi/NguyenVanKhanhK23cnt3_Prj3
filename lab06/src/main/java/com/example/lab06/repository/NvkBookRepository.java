package com.example.lab06.repository;

import com.example.lab06.entity.NvkBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NvkBookRepository extends JpaRepository<NvkBook, Long> {

}
