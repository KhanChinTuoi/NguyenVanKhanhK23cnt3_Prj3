package com.example.lab06.service;

import com.example.lab06.entity.NvkBook;
import com.example.lab06.repository.NvkBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service

public class NvkBookService {
    @Autowired
    private NvkBookRepository nvkBookRepository;

    // Lấy toàn bộ danh sách các book
    public List<NvkBook> getAllNvkBooks(){
        return nvkBookRepository.findAll();
    }

    // Lấy 1 cuốn sách theo id
    public  NvkBook getNvkBookById(Long nvkId){
        return nvkBookRepository.findById(nvkId).orElse(null);
    }

    // Cập nhật thông tin sách
    public  NvkBook saveNvkBook(NvkBook nvkBook){
        return nvkBookRepository.save(nvkBook);
    }

    // xóa
    public  void  deleteNvkBook(Long nvkId){
        nvkBookRepository.deleteById(nvkId);
    }
}
