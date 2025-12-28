package com.example.cuahang.repository;

import com.example.cuahang.entity.NvkHoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NvkHoaDonRepository extends JpaRepository<NvkHoaDon, Integer> {

    // 1. Lấy danh sách đơn hàng của một khách hàng (Sắp xếp mới nhất lên đầu)
    List<NvkHoaDon> findByKhachHang_MaKhOrderByNgayLapDesc(Integer maKh);

    // 2. Lấy tất cả hóa đơn sắp xếp theo ngày lập (Dùng cho trang Admin)
    List<NvkHoaDon> findAllByOrderByNgayLapDesc();

    // 3. Lọc hóa đơn theo trạng thái (Dùng để Admin quản lý đơn Chờ duyệt, Đã giao...)
    List<NvkHoaDon> findByTrangThaiOrderByNgayLapDesc(Integer trangThai);

    // 4. Tính tổng doanh thu từ các đơn hàng đã giao thành công (Giả sử trạng thái 2 là Đã giao)
    @Query("SELECT SUM(h.tongTien) FROM NvkHoaDon h WHERE h.trangThai = 2")
    Double sumTotalRevenue();

    // 5. Thống kê doanh thu theo từng khách hàng
    @Query("SELECT SUM(h.tongTien) FROM NvkHoaDon h WHERE h.khachHang.maKh = :maKh AND h.trangThai = 2")
    Double sumRevenueByCustomer(@Param("maKh") Integer maKh);
}