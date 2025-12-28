package com.example.cuahang.service.impl;

import com.example.cuahang.entity.NvkDanhMuc;
import com.example.cuahang.repository.NvkDanhMucRepository;
import com.example.cuahang.service.NvkDanhMucService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class NvkDanhMucServiceImpl implements NvkDanhMucService {

    @Autowired
    private NvkDanhMucRepository repo;

    @Override
    public List<NvkDanhMuc> findAll() { return repo.findAll(); }

    @Override
    public NvkDanhMuc findById(Integer id) { return repo.findById(id).orElse(null); }

    @Override
    public NvkDanhMuc save(NvkDanhMuc dm) { return repo.save(dm); }

    @Override
    public void delete(Integer id) { repo.deleteById(id); }
}
