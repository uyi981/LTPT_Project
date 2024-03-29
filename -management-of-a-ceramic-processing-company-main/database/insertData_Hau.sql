
 --them value khachhang mau vao
INSERT INTO KhachHang(maKH,tenKH,email,dienThoai,toChuc) values('230001',N'Võ Phước Hậu','vohau115@gmail.com','0899530610',0)
INSERT INTO KhachHang(maKH,tenKH,email,dienThoai,toChuc) values('231002',N'Công ty TNHH Uy Vũ','vovuCompany@gmail.com','0899530600',1)
--them value hopdong mau vao
--khachhang1
INSERT INTO HopDong(maHD,maKH,tenHD,ngayBatDau,ngayKetThucDukien,trangThai,donGia) values('0511202301','230001',N'Hợp đồng gia công chén sứ','2023-11-05','2023-12-05',N'chờ xác nhận',15000000.0)
INSERT INTO HopDong(maHD,maKH,tenHD,ngayBatDau,ngayKetThucDukien,trangThai,donGia) values('0511202302','230001',N'Hợp đồng gia công ly sứ','2023-11-05','2024-1-05',N'chờ xác nhận',20000000.0)
INSERT INTO HopDong(maHD,maKH,tenHD,ngayBatDau,ngayKetThucDukien,trangThai,donGia) values('0511202303','230001',N'Hợp đồng gia công tô sứ','2023-11-05','2024-1-05',N'chờ xác nhận',30000000.0)
--khachhang2
INSERT INTO HopDong(maHD,maKH,tenHD,ngayBatDau,ngayKetThucDukien,trangThai,donGia) values('0511202304','231002',N'Hợp đồng gia công bình gốm sứ','2023-11-05','2024-2-05',N'chờ xác nhận',50000000.0)
-- them value sanpham mau
--hopdong 1
INSERT INTO SanPham(maHD,maSP,tenSP,soLuong,donGia) values('0511202301','051120230101',N'Chén sứ viền xanh lam',500,5000.0)
INSERT INTO SanPham(maHD,maSP,tenSP,soLuong,donGia) values('0511202301','051120230102',N'Chén sứ họa tiết hình rồng',500,6000.0)
INSERT INTO SanPham(maHD,maSP,tenSP,soLuong,donGia) values('0511202301','051120230103',N'Chén sứ viền cam',500,4000.0)
--hopdong 2
INSERT INTO SanPham(maHD,maSP,tenSP,soLuong,donGia) values('0511202302','051120230201',N'ly sứ viền xanh lam',1000,4000.0)
INSERT INTO SanPham(maHD,maSP,tenSP,soLuong,donGia) values('0511202302','051120230202',N'ly sứ họa tiết hình rồng',1000,6000.0)
--hopdong 3
INSERT INTO SanPham(maHD,maSP,tenSP,soLuong,donGia) values('0511202303','051120230301',N'tô sứ viền cam',500,4000.0)
INSERT INTO SanPham(maHD,maSP,tenSP,soLuong,donGia) values('0511202303','051120230302',N'tô sứ viền xanh lam',500,5000.0)
--hopdong 4
INSERT INTO SanPham(maHD,maSP,tenSP,soLuong,donGia) values('0511202304','051120230401',N'bình sứ họa tiết thanh hoa',500,6000.0)
INSERT INTO SanPham(maHD,maSP,tenSP,soLuong,donGia) values('0511202304','051120230402',N'bình sứ viền cam',500,4000.0)


-- bộ phận
INSERT INTO BoPhan (maBP, tenBP) VALUES ('HC01', N'Kế toán');
INSERT INTO BoPhan (maBP, tenBP) VALUES ('HC02', N'Kỹ thuật');
INSERT INTO BoPhan (maBP, tenBP) VALUES ('HC03', N'Quản lý');
INSERT INTO BoPhan (maBP, tenBP) VALUES ('HC04', N'Nhân sự');
INSERT INTO BoPhan (maBP, tenBP) VALUES ('HC05', N'Tiếp thị');

INSERT INTO BoPhan (maBP, tenBP) VALUES ('SX01', N'Kiểm tra chất lượng');
INSERT INTO BoPhan (maBP, tenBP) VALUES ('SX02', N'Giao hàng và Vận chuyển');
INSERT INTO BoPhan (maBP, tenBP) VALUES ('SX03', N'Lưu trữ và Logistik');
INSERT INTO BoPhan (maBP, tenBP) VALUES ('SX04', N'đóng gói');
INSERT INTO BoPhan (maBP, tenBP) VALUES ('SX05', N'in ấn');

-- nhân viên
-- thay lại bằng path đến assets của dự án
INSERT INTO NhanVien (maNV, maBP, tenNV, gioiTinh, ngaySinh, ngayBatDauLam, CCCD, luongCoBan, phuCap, trangThai, dienThoai, hinhAnh)
VALUES 
    ('HC01181001', 'HC01', N'Lê Thị Bích', 1, '1992-08-25', '2018-03-15', '000022221111', 5000000.0, 1000000.0, 1, '0123456789', (SELECT BulkColumn FROM OPENROWSET(BULK N'C:\Users\vohau\OneDrive\Desktop\PTUD\-management-of-a-ceramic-processing-company\src\assets\images\avatarNV\nv2.jpg', SINGLE_BLOB) as ImageData))
INSERT INTO NhanVien (maNV, maBP, tenNV, gioiTinh, ngaySinh, ngayBatDauLam, CCCD, luongCoBan, phuCap, trangThai, dienThoai, hinhAnh)
VALUES 
    ('HC02201002', 'HC02', N'Đỗ Văn Tấn', 0, '1996-08-25', '2020-06-20', '111100002222', 7000000.0, 1000000.0, 1, '9876543210', (SELECT BulkColumn FROM OPENROWSET(BULK N'C:\Users\vohau\OneDrive\Desktop\PTUD\-management-of-a-ceramic-processing-company\src\assets\images\avatarNV\nv1.jpg', SINGLE_BLOB) as ImageData))
INSERT INTO NhanVien (maNV, maBP, tenNV, gioiTinh, ngaySinh, ngayBatDauLam, CCCD, luongCoBan, phuCap, trangThai, dienThoai, hinhAnh)
VALUES 
    ('HC01181003', 'HC01', N'Lê Tấn Đào', 0, '1992-08-25', '2018-03-15', '000022221111', 5000000.0, 1000000.0, 1, '0123456789', (SELECT BulkColumn FROM OPENROWSET(BULK N'C:\Users\vohau\OneDrive\Desktop\PTUD\-management-of-a-ceramic-processing-company\src\assets\images\avatarNV\nv2.jpg', SINGLE_BLOB) as ImageData))
INSERT INTO NhanVien (maNV, maBP, tenNV, gioiTinh, ngaySinh, ngayBatDauLam, CCCD, luongCoBan, phuCap, trangThai, dienThoai, hinhAnh)
VALUES 
    ('HC02201004', 'HC02', N'Nguyễn Bá Toàn', 0, '1996-08-25', '2020-06-20', '111100002222', 7000000.0, 1000000.0, 1, '9876543210', (SELECT BulkColumn FROM OPENROWSET(BULK N'C:\Users\vohau\OneDrive\Desktop\PTUD\-management-of-a-ceramic-processing-company\src\assets\images\avatarNV\nv1.jpg', SINGLE_BLOB) as ImageData))
INSERT INTO NhanVien (maNV, maBP, tenNV, gioiTinh, ngaySinh, ngayBatDauLam, CCCD, luongCoBan, phuCap, trangThai, dienThoai, hinhAnh)
VALUES 
    ('HC01181005', 'HC01', N'Đỗ Thị Tuyết Ngân', 1, '1992-08-25', '2018-03-15', '000022221111', 5000000.0, 1000000.0, 1, '0123456789', (SELECT BulkColumn FROM OPENROWSET(BULK N'C:\Users\vohau\OneDrive\Desktop\PTUD\-management-of-a-ceramic-processing-company\src\assets\images\avatarNV\nv2.jpg', SINGLE_BLOB) as ImageData))
INSERT INTO NhanVien (maNV, maBP, tenNV, gioiTinh, ngaySinh, ngayBatDauLam, CCCD, luongCoBan, phuCap, trangThai, dienThoai, hinhAnh)
VALUES 
    ('HC02201006', 'HC02', N'Mai Thị Tuyết', 1, '1999-08-25', '2020-06-20', '111100002222', 7000000.0, 1000000.0, 1, '9876543210', (SELECT BulkColumn FROM OPENROWSET(BULK N'C:\Users\vohau\OneDrive\Desktop\PTUD\-management-of-a-ceramic-processing-company\src\assets\images\avatarNV\nv1.jpg', SINGLE_BLOB) as ImageData))
INSERT INTO NhanVien (maNV, maBP, tenNV, gioiTinh, ngaySinh, ngayBatDauLam, CCCD, luongCoBan, phuCap, trangThai, dienThoai, hinhAnh)
VALUES 
    ('HC02201007', 'HC02', N'Trần Thị Tố My', 1, '1999-08-25', '2020-06-20', '111100002222', 7000000.0, 1000000.0, 1, '9876543210', (SELECT BulkColumn FROM OPENROWSET(BULK N'C:\Users\vohau\OneDrive\Desktop\PTUD\-management-of-a-ceramic-processing-company\src\assets\images\avatarNV\nv1.jpg', SINGLE_BLOB) as ImageData))
    ('NV1810001', 'HC01', N'Lê Thị Bích', 1, '1992-08-25', '2018-03-15', '000022221111', 5000000.0, 1000000.0, 1, '0123456789', (SELECT BulkColumn FROM OPENROWSET(BULK N'C:\Users\vohau\OneDrive\Desktop\PTUD\-management-of-a-ceramic-processing-company\src\assets\images\avatarNV\nv2.jpg', SINGLE_BLOB) as ImageData))
INSERT INTO CongNhan(maCN, maBP, tenCN, gioiTinh, ngaySinh, ngayBatDauLam, CCCD, trangThai,choPhanCong, dienThoai, hinhAnh)
VALUES 
    ('SX01051001', 'SX01', N'Nguyễn Văn Bạch', 1, '1986-01-15', '2005-05-01', '0222211110000', 1, 1, '01357924680', 
        (SELECT BulkColumn FROM OPENROWSET(BULK N'C:\Users\vohau\OneDrive\Desktop\PTUD\-management-of-a-ceramic-processing-company\src\assets\images\avatarCN\cn1.jpg', SINGLE_BLOB) as ImageData)),
    ('SX02181002', 'SX02', N'Đỗ Nhật Anh', 1, '1999-01-15', '2018-05-01', '0111122220000', 1, 1, '02468013579', 
        (SELECT BulkColumn FROM OPENROWSET(BULK N'C:\Users\vohau\OneDrive\Desktop\PTUD\-management-of-a-ceramic-processing-company\src\assets\images\avatarCN\cn2.jpg', SINGLE_BLOB) as ImageData)),
    ('SX0310003', 'SX03', N'Phạm Thị Hương', 0, '1988-02-20', '2010-06-01', '0333322220000', 1, 1, '09876543210', 
        (SELECT BulkColumn FROM OPENROWSET(BULK N'C:\Users\vohau\OneDrive\Desktop\PTUD\-management-of-a-ceramic-processing-company\src\assets\images\avatarCN\cn3.jpeg', SINGLE_BLOB) as ImageData)),
    ('SX0417004', 'SX04', N'Nguyễn Thị Mai', 0, '1995-09-10', '2017-06-01', '0444411110000', 1, 1, '01234567890', 
        (SELECT BulkColumn FROM OPENROWSET(BULK N'C:\Users\vohau\OneDrive\Desktop\PTUD\-management-of-a-ceramic-processing-company\src\assets\images\avatarCN\cn4.jpeg', SINGLE_BLOB) as ImageData)),
    ('SX0506005', 'SX05', N'Trần Văn Tú', 1, '1988-06-18', '2006-07-01', '0555522220000', 1, 1, '09876123450', 
        (SELECT BulkColumn FROM OPENROWSET(BULK N'C:\Users\vohau\OneDrive\Desktop\PTUD\-management-of-a-ceramic-processing-company\src\assets\images\avatarCN\cn5.jpeg', SINGLE_BLOB) as ImageData)),
    ('SX0120006', 'SX01', N'Vũ Thị Lan', 0, '1997-04-05', '2020-07-01', '0666611110000', 1, 1, '01234987650', 
        (SELECT BulkColumn FROM OPENROWSET(BULK N'C:\Users\vohau\OneDrive\Desktop\PTUD\-management-of-a-ceramic-processing-company\src\assets\images\avatarCN\cn6.jpeg', SINGLE_BLOB) as ImageData)),
    ('SX0221007', 'SX02', N'Hoàng Văn Hải', 1, '1994-11-12', '2021-08-01', '0777722220000', 1, 1, '09876123450', 
        (SELECT BulkColumn FROM OPENROWSET(BULK N'C:\Users\vohau\OneDrive\Desktop\PTUD\-management-of-a-ceramic-processing-company\src\assets\images\avatarCN\cn7.jpeg', SINGLE_BLOB) as ImageData)),
    ('SX0322008', 'SX03', N'Nguyễn Thị Anh', 0, '1993-08-25', '2022-08-01', '0888811110000', 1, 1, '01234987650', 
        (SELECT BulkColumn FROM OPENROWSET(BULK N'C:\Users\vohau\OneDrive\Desktop\PTUD\-management-of-a-ceramic-processing-company\src\assets\images\avatarCN\cn8.jpeg', SINGLE_BLOB) as ImageData)),
    ('SX0421009', 'SX04', N'Lê Văn Hoàng', 1, '1991-03-30', '2021-09-01', '0999922220000', 1, 1, '09876123450', 
        (SELECT BulkColumn FROM OPENROWSET(BULK N'C:\Users\vohau\OneDrive\Desktop\PTUD\-management-of-a-ceramic-processing-company\src\assets\images\avatarCN\cn9.jpeg', SINGLE_BLOB) as ImageData)),
    ('SX0500010', 'SX05', N'Trương Thị Phương', 0, '1980-12-03', '2000-09-01', '101010101010', 1, 1, '01234987650', 
        (SELECT BulkColumn FROM OPENROWSET(BULK N'C:\Users\vohau\OneDrive\Desktop\PTUD\-management-of-a-ceramic-processing-company\src\assets\images\avatarCN\cn10.jpeg', SINGLE_BLOB) as ImageData)),
    ('SX0111011', 'SX01', N'Nguyễn Văn Quang', 1, '1992-07-22', '2011-10-01', '121212121212', 1, 1, '09876123450', 
        (SELECT BulkColumn FROM OPENROWSET(BULK N'C:\Users\vohau\OneDrive\Desktop\PTUD\-management-of-a-ceramic-processing-company\src\assets\images\avatarCN\cn11.jpeg', SINGLE_BLOB) as ImageData)),
    ('SX0220012', 'SX02', N'Phạm Thị Tâm', 0, '1993-05-28', '2020-10-01', '131313131313', 1, 1, '01234987650', 
        (SELECT BulkColumn FROM OPENROWSET(BULK N'C:\Users\vohau\OneDrive\Desktop\PTUD\-management-of-a-ceramic-processing-company\src\assets\images\avatarCN\cn12.jpeg', SINGLE_BLOB) as ImageData)),
    ('SX0321013', 'SX03', N'Lê Thị Hương', 0, '1998-02-15', '2021-11-01', '141414141414', 1, 1, '09876123450', 
        (SELECT BulkColumn FROM OPENROWSET(BULK N'C:\Users\vohau\OneDrive\Desktop\PTUD\-management-of-a-ceramic-processing-company\src\assets\images\avatarCN\cn13.jpeg', SINGLE_BLOB) as ImageData)),
    ('SX0415014', 'SX04', N'Đỗ Thị Hà', 0, '1995-10-10', '2015-11-01', '151515151515', 1, 1, '01234987650', 
        (SELECT BulkColumn FROM OPENROWSET(BULK N'C:\Users\vohau\OneDrive\Desktop\PTUD\-management-of-a-ceramic-processing-company\src\assets\images\avatarCN\cn14.jpeg', SINGLE_BLOB) as ImageData)),
    ('SX0521015', 'SX05', N'Nguyễn Văn Hùng', 1, '1992-06-18', '2021-12-01', '161616161616', 1, 1, '09876123450', 
        (SELECT BulkColumn FROM OPENROWSET(BULK N'C:\Users\vohau\OneDrive\Desktop\PTUD\-management-of-a-ceramic-processing-company\src\assets\images\avatarCN\cn15.jpeg', SINGLE_BLOB) as ImageData)),
    ('SX0120016', 'SX01', N'Lê Thị Lan', 0, '1997-04-05', '2020-12-01', '171717171717', 1, 1, '01234987650', 
        (SELECT BulkColumn FROM OPENROWSET(BULK N'C:\Users\vohau\OneDrive\Desktop\PTUD\-management-of-a-ceramic-processing-company\src\assets\images\avatarCN\cn16.jpeg', SINGLE_BLOB) as ImageData)),
    ('SX0221017', 'SX02', N'Trần Văn Hải', 1, '1994-11-12', '2022-01-01', '181818181818', 1, 1, '09876123450', 
        (SELECT BulkColumn FROM OPENROWSET(BULK N'C:\Users\vohau\OneDrive\Desktop\PTUD\-management-of-a-ceramic-processing-company\src\assets\images\avatarCN\cn17.jpeg', SINGLE_BLOB) as ImageData)),
    ('SX0320018', 'SX03', N'Phạm Thị Anh', 0, '1993-08-25', '2022-01-02', '191919191919', 1, 1, '01234987650', 
        (SELECT BulkColumn FROM OPENROWSET(BULK N'C:\Users\vohau\OneDrive\Desktop\PTUD\-management-of-a-ceramic-processing-company\src\assets\images\avatarCN\cn18.jpeg', SINGLE_BLOB) as ImageData)),
    ('SX0421019', 'SX04', N'Lê Văn Hoàng', 1, '1991-03-30', '2022-01-03', '202020202020', 1, 1, '09876123450', 
        (SELECT BulkColumn FROM OPENROWSET(BULK N'C:\Users\vohau\OneDrive\Desktop\PTUD\-management-of-a-ceramic-processing-company\src\assets\images\avatarCN\cn19.jpeg', SINGLE_BLOB) as ImageData)),
    ('SX0520020', 'SX05', N'Trương Thị Phương', 0, '1989-12-03', '2022-01-04', '212121212121', 1, 1, '01234987650', 
        (SELECT BulkColumn FROM OPENROWSET(BULK N'C:\Users\vohau\OneDrive\Desktop\PTUD\-management-of-a-ceramic-processing-company\src\assets\images\avatarCN\cn20.jpeg', SINGLE_BLOB) as ImageData));


-- công đoạn 
-- Công đoạn cho sản phẩm 051120230101
INSERT INTO CongDoan (maCD, maSP, maBP, tenCD, donGia, trangThai, soLuongChuanBi, soLuongChuanBiToiThieu, soLuongHoanThanh)
VALUES
('05112023010101', '051120230101', 'SX01', N'Gia công nền chén', 1000.0, 1, 50, 30, 120),
('05112023010102', '051120230101', 'SX02', N'Sơn viền xanh lam', 2000.0, 1, 50, 30, 120),
('05112023010103', '051120230101', 'SX03', N'Kiểm tra chất lượng', 500.0, 1, 50, 30, 120)

-- Công đoạn cho sản phẩm 051120230201
INSERT INTO CongDoan (maCD, maSP, maBP, tenCD, donGia, trangThai, soLuongChuanBi, soLuongChuanBiToiThieu, soLuongHoanThanh)
VALUES
('05112023010201', '051120230102', 'SX01', N'Kiểm tra sản phẩm', 1000.0, 1, 80, 60, 240),
('05112023010202', '051120230102', 'SX02', N'Đóng gói', 2000.0, 1, 80, 60, 240),
('05112023010203', '051120230102', 'SX03', N'Vận chuyển', 500.0, 1, 80, 60, 240)
-- Công đoạn cho sản phẩm 051120230301
INSERT INTO CongDoan (maCD, maSP, maBP, tenCD, donGia, trangThai, soLuongChuanBi, soLuongChuanBiToiThieu, soLuongHoanThanh)
VALUES
('05112023010301', '051120230103', 'SX01', N'Kiểm tra sản phẩm', 1500.0, 1, 70, 50, 220),
('05112023010302', '051120230103', 'SX02', N'Giao hàng', 2500.0, 1, 70, 50, 220),
('05112023010303', '051120230103', 'SX03', N'Đóng gói', 600.0, 1, 70, 50, 220)
-- Thêm 7 công đoạn khác cho sản phẩm 0511202303 tại đây

-- Công đoạn cho sản phẩm 051120230401
INSERT INTO CongDoan (maCD, maSP, maBP, tenCD, donGia, trangThai, soLuongChuanBi, soLuongChuanBiToiThieu, soLuongHoanThanh)
VALUES
('05112023020101', '051120230201', 'SX01', N'Kiểm tra sản phẩm', 2000.0, 1, 90, 70, 280),
('05112023020102', '051120230201', 'SX02', N'Đóng gói', 3000.0, 1, 90, 70, 280),
('05112023020103', '051120230201', 'SX03', N'Giao hàng', 700.0, 1, 90, 70, 280)


--Chèn tài khoản
--TK001: Full quyền
--TK002: QLTK
--TK003: QLNS
--TK004: Thông tin HD & SP
--TK005 SP & CD
--TK006: Tính lương
--TK007: Chấm công
--Password chung: 123456
USE [QuanLyLSP]
GO
INSERT [dbo].[TaiKhoan] ([maNV], [userName], [matKhau], [vaiTro], [trangThai]) VALUES (N'HC01181001', N'TK001', N'8d969eef6ecad3c2', 1, 1)
GO
INSERT [dbo].[TaiKhoan] ([maNV], [userName], [matKhau], [vaiTro], [trangThai]) VALUES (N'HC02201002', N'TK002', N'8d969eef6ecad3c2', 2, 1)
GO
INSERT [dbo].[TaiKhoan] ([maNV], [userName], [matKhau], [vaiTro], [trangThai]) VALUES (N'HC01181003', N'TK003', N'8d969eef6ecad3c2', 3, 1)
GO
INSERT [dbo].[TaiKhoan] ([maNV], [userName], [matKhau], [vaiTro], [trangThai]) VALUES (N'HC02201004', N'TK004', N'8d969eef6ecad3c2', 4, 1)
GO
INSERT [dbo].[TaiKhoan] ([maNV], [userName], [matKhau], [vaiTro], [trangThai]) VALUES (N'HC01181005', N'TK005', N'8d969eef6ecad3c2', 5, 1)
GO
INSERT [dbo].[TaiKhoan] ([maNV], [userName], [matKhau], [vaiTro], [trangThai]) VALUES (N'HC02201006', N'TK006', N'8d969eef6ecad3c2', 6, 1)
GO
INSERT [dbo].[TaiKhoan] ([maNV], [userName], [matKhau], [vaiTro], [trangThai]) VALUES (N'HC02201007', N'TK007', N'8d969eef6ecad3c2', 7, 1)
GO
