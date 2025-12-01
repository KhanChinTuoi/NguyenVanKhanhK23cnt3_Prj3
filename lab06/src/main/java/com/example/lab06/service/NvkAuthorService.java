package com.example.lab06.service;

import com.example.lab06.entity.NvkAuthor;
import com.example.lab06.repository.NvkAuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service

public class NvkAuthorService {
    @Autowired
    private NvkAuthorRepository nvkAuthorRepository;

    // Lấy toàn bộ danh sách tác giả
    public  List<NvkAuthor> getAllNvkAuthors(){
        return nvkAuthorRepository.findAll();
    }
    // lấy ra một tác giả
    public NvkAuthor getNvkAuthorById(long nvkId){
        return  nvkAuthorRepository.findById(nvkId).orElse(null);
    }

    // Cập nhât thông tin
    public NvkAuthor saveNvkAuthor(NvkAuthor nvkAuthor){
        return nvkAuthorRepository.save(nvkAuthor);
    }

    // xóa
    public  void deleteNvkAuthorById(long nvkId){
        nvkAuthorRepository.deleteById(nvkId);
    }

    public List<NvkAuthor> findNvkAuthorById(List<Long> nvkIds){
        return nvkAuthorRepository.findAllById(nvkIds);
    }
}
