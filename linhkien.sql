-- Tạo database
CREATE DATABASE IF NOT EXISTS linhkien;
USE linhkien;

-- Bảng danh mục sản phẩm
CREATE TABLE danhmucsp (
    MaDMSP INT AUTO_INCREMENT PRIMARY KEY,
    TenDMSP VARCHAR(100) NOT NULL,
    MoTa TEXT
);

-- Bảng sản phẩm
CREATE TABLE sanpham (
    MaSP INT AUTO_INCREMENT PRIMARY KEY,
    TenSP VARCHAR(150) NOT NULL,
    Gia DECIMAL(15,2) NOT NULL,
    so_luong INT DEFAULT 0,
    hinh_anh VARCHAR(255),
    MaDMSP INT,
    motaSP TEXT,
    FOREIGN KEY (MaDMSP) REFERENCES danhmucsp(MaDMSP)
);

CREATE TABLE khachhang (
    ma_kh INT AUTO_INCREMENT PRIMARY KEY,
    ho_ten VARCHAR(100) NOT NULL,
    SDT VARCHAR(20) NOT NULL,
    dia_chi VARCHAR(255),
    Email VARCHAR(100),
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- Bảng hóa đơn
CREATE TABLE hoadon (
    ma_hd INT AUTO_INCREMENT PRIMARY KEY,
    ma_kh INT,
    ngay_lap DATE NOT NULL,
    tong_tien DECIMAL(15,2) NOT NULL,
    FOREIGN KEY (ma_kh) REFERENCES khachhang(ma_kh)
);

-- Bảng chi tiết hóa đơn
CREATE TABLE chitiethoadon (
    MaCTHD INT AUTO_INCREMENT PRIMARY KEY,
    ma_hd INT,
    MaSP INT,
    so_luong INT NOT NULL,
    don_gia DECIMAL(15,2) NOT NULL,
    FOREIGN KEY (ma_hd) REFERENCES hoadon(ma_hd),
    FOREIGN KEY (MaSP) REFERENCES sanpham(MaSP)
);

-- Bảng admin
CREATE TABLE admin (
    ma_admin INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    ho_ten VARCHAR(100)
);

-- ========================
INSERT INTO danhmucsp (TenDMSP, MoTa) VALUES
('CPU', 'Bộ vi xử lý trung tâm, đóng vai trò như bộ não của máy tính.'),
('GPU', 'Card đồ họa/Bộ xử lý đồ họa, chịu trách nhiệm xử lý hình ảnh.'),
('RAM', 'Bộ nhớ truy cập ngẫu nhiên, dùng để lưu trữ dữ liệu tạm thời.'),
('SSD', 'Ổ cứng thể rắn, dùng để lưu trữ hệ điều hành và dữ liệu.'),
('Mainboard', 'Bo mạch chủ, kết nối tất cả các thành phần linh kiện.');

INSERT INTO sanpham (TenSP, Gia, so_luong, hinh_anh, MaDMSP) VALUES
('Intel Core i7-13700K', 9500000.00, 15, 'i713700k.jpg', 1), 
('NVIDIA RTX 4070 Ti', 25000000.00, 8, 'rtx4070.jpg', 2), 
('Kingston Fury Beast 16GB DDR4', 1800000.00, 30, 'kingston.jpg', 3), 
('Samsung 970 EVO Plus 1TB NVMe', 1500000.00, 22, 'SSD-Samsung-970-EVO-Plus-1TB.jpg', 4), 
('ASUS ROG Strix Z790-E Gaming WIFI', 10500000.00, 10, 'asus-rog-strix-z790-e-gaming-wifi-ii.jpg', 5);

INSERT INTO khachhang (ho_ten, SDT, dia_chi, Email, username, password) 
VALUES 
('Nguyễn Văn Tùng', '0912345678', 'Hà Nội', 'tungnv@gmail.com', 'nvktung', '$2a$10$3hjJSWiPLxopxCq6iZrlT.TPBiBBenOGc6JqlF5/zgQHn0EKexiBG'),
('Lê Thị Hoa', '0988777666', 'TP. Hồ Chí Minh', 'hoale@yahoo.com', 'nvkhoa', '$2a$10$3hjJSWiPLxopxCq6iZrlT.TPBiBBenOGc6JqlF5/zgQHn0EKexiBG'),
('Trần Quang Minh', '0905123456', 'Đà Nẵng', 'minhtran@outlook.com', 'nvkminh', '$2a$10$3hjJSWiPLxopxCq6iZrlT.TPBiBBenOGc6JqlF5/zgQHn0EKexiBG'),
('Phạm Phương Lan', '0333444555', 'Hải Phòng', 'lanpham@gmail.com', 'nvklan', '$2a$10$3hjJSWiPLxopxCq6iZrlT.TPBiBBenOGc6JqlF5/zgQHn0EKexiBG'),
('Hoàng Xuân Bách', '0977111222', 'Cần Thơ', 'bachhoang@gmail.com', 'nvkbach', '$2a$10$3hjJSWiPLxopxCq6iZrlT.TPBiBBenOGc6JqlF5/zgQHn0EKexiBG');

INSERT INTO hoadon (ma_kh, ngay_lap, tong_tien) VALUES
(1, '2025-12-10', 9500000.00), 
(2, '2025-12-12', 26800000.00), 
(3, '2025-12-13', 20000000.00), 
(4, '2025-12-14', 3000000.00),
(5, '2023-12-14', 58000000.00);

INSERT INTO chitiethoadon (ma_hd, MaSP, so_luong, don_gia) VALUES
(1, 1, 1, 9500000.00), 
(2, 2, 1, 25000000.00), 
(2, 3, 1, 1800000.00), 
(3, 5, 2, 10000000.00), 
(4, 4, 2, 1500000.00); 

INSERT INTO admin (username, password, ho_ten) VALUES 
('khan9t', '$2a$10$3hjJSWiPLxopxCq6iZrlT.TPBiBBenOGc6JqlF5/zgQHn0EKexiBG', 'Nguyễn Văn Khánh'),
('admin2', '$2a$10$3hjJSWiPLxopxCq6iZrlT.TPBiBBenOGc6JqlF5/zgQHn0EKexiBG', 'Nguyễn Xuân Vinh'),
('admin3', '$2a$10$3hjJSWiPLxopxCq6iZrlT.TPBiBBenOGc6JqlF5/zgQHn0EKexiBG', 'Bùi Đức Huy'),
('admin4', '$2a$10$3hjJSWiPLxopxCq6iZrlT.TPBiBBenOGc6JqlF5/zgQHn0EKexiBG', 'Dương Gia Đắc'),
('admin5', '$2a$10$3hjJSWiPLxopxCq6iZrlT.TPBiBBenOGc6JqlF5/zgQHn0EKexiBG', 'Lưu Trường Giang');




