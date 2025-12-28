package com.example.cuahang.service.impl;

import com.example.cuahang.entity.NvkCTHoaDon;
import com.example.cuahang.entity.NvkHoaDon;
import com.example.cuahang.entity.NvkProduct;
import com.example.cuahang.repository.NvkCTHoaDonRepository;
import com.example.cuahang.repository.NvkHoaDonRepository;
import com.example.cuahang.repository.NvkProductRepository;
import com.example.cuahang.service.NvkHoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NvkHoaDonServiceImpl implements NvkHoaDonService {

    @Autowired private NvkHoaDonRepository hdRepo;
    @Autowired private NvkCTHoaDonRepository cthdRepo;
    @Autowired private NvkProductRepository spRepo;

    @Override
    public List<NvkHoaDon> findAll() {
        return hdRepo.findAll();
    }

    @Override
    public NvkHoaDon findById(Integer id) {
        return hdRepo.findById(id).orElse(null);
    }

    @Override
    public NvkHoaDon save(NvkHoaDon hd) {
        return hdRepo.save(hd);
    }

    @Override
    public List<NvkHoaDon> findByCustomerName(String name) {
        // Nên viết Query trong Repository thay vì stream để tối ưu hiệu năng
        return hdRepo.findAll().stream()
                .filter(hd -> hd.getKhachHang() != null &&
                        hd.getKhachHang().getHoTen().toLowerCase().contains(name.toLowerCase()))
                .toList();
    }

    @Override
    public List<NvkCTHoaDon> findDetailsByOrderId(Integer id) {
        List<NvkCTHoaDon> details = cthdRepo.findByHoaDon_MaHD(id);
        System.out.println("DEBUG: Số lượng mặt hàng tìm thấy cho mã HĐ " + id + " là: " + details.size());
        for (NvkCTHoaDon item : details) {
            if (item.getSoLuong() == null || item.getSoLuong() <= 0) item.setSoLuong(1);
            if ((item.getDonGia() == null || item.getDonGia() == 0) && item.getSanPham() != null) {
                item.setDonGia(item.getSanPham().getGia());
            }
        }
        return details;
    }

    @Override
    public Double calculateActualTotal(List<NvkCTHoaDon> details) {
        if (details == null) return 0.0;
        return details.stream().mapToDouble(d -> d.getSoLuong() * d.getDonGia()).sum();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Integer maHD, Integer status) {
        NvkHoaDon hd = hdRepo.findById(maHD)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn"));

        Integer oldStatus = hd.getTrangThai();

        // Nếu trạng thái mới là HỦY (3) và trạng thái cũ khác HỦY
        if (status == 3 && (oldStatus == null || oldStatus != 3)) {
            List<NvkCTHoaDon> details = cthdRepo.findByHoaDon_MaHD(maHD);
            for (NvkCTHoaDon ct : details) {
                NvkProduct sp = ct.getSanPham();
                if (sp != null) {
                    // HOÀN KHO VÀ LƯU VĨNH VIỄN
                    int currentStock = (sp.getSoLuong() != null) ? sp.getSoLuong() : 0;
                    sp.setSoLuong(currentStock + ct.getSoLuong());
                    spRepo.save(sp);
                }
            }
        }

        hd.setTrangThai(status);
        hdRepo.save(hd);
    }
}