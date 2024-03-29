
CREATE DATABASE QuanLyLSP
go
use QuanLyLSP
go

CREATE TABLE BoPhan (
	maBP VARCHAR(50) PRIMARY KEY, 
	tenBP NVARCHAR(30), 
);
CREATE TABLE NhanVien (

    maNV VARCHAR(50) PRIMARY KEY,
	maBP VARCHAR(50),
    tenNV NVARCHAR(50),
    gioiTinh BIT,
    ngaySinh DATE,
    ngayBatDauLam DATE,
    CCCD VARCHAR(30),
    luongCoBan FLOAT(10),
    phuCap FLOAT(10),
    trangThai BIT,
    dienThoai VARCHAR(20),
    hinhAnh VARBINARY(MAX), 
	FOREIGN KEY (maBP) REFERENCES BoPhan(maBP)
);

CREATE TABLE BangDanhGiaNhanVien (
	maBDG VARCHAR(50) PRIMARY KEY,
    nam INT,
    maNV VARCHAR(50),
    diemChuyenCan FLOAT,
    diemThaiDo FLOAT,
	diemChuyenMon FLOAT,
    diemHieuSuat FLOAT,
    bac CHAR,
    FOREIGN KEY (maNV) REFERENCES NhanVien(maNV)
);


CREATE TABLE PhieuChamCongHanhChinh (
	maPCCHC VARCHAR(50),
    ngayChamCong DATE,
    maNV VARCHAR(50),
    vang BIT,
    diTre BIT,
    gioTangCa INT,
    tienPhat FLOAT(10),
    noiDungPhat NVARCHAR(20),
    PRIMARY KEY (maPCCHC),
	FOREIGN KEY (maNV) REFERENCES NhanVien(maNV)
);

CREATE TABLE TaiKhoan (
    maNV VARCHAR(50),
    userName VARCHAR(50),
    matKhau VARCHAR(30),
    vaiTro INT,
    trangThai BIT,
    PRIMARY KEY (maNV),
	CONSTRAINT PK_userName UNIQUE (userName),
    FOREIGN KEY (maNV) REFERENCES NhanVien(maNV)
);

CREATE TABLE CongNhan (
    maCN VARCHAR(50) PRIMARY KEY,
	maBP VARCHAR(50),
    tenCN NVARCHAR(50),
    gioiTinh BIT,
    ngaySinh DATE,
    ngayBatDauLam DATE,
    CCCD VARCHAR(30),
    trangThai BIT,
    choPhanCong BIT,
	dienThoai VARCHAR(20),
    hinhAnh VARBINARY(MAX), 
	FOREIGN KEY (maBP) REFERENCES BoPhan(maBP)
);

CREATE TABLE BangDanhGiaCongNhan (
	maBDG VARCHAR(50) PRIMARY KEY,
    nam INT,
    maCN VARCHAR(50),
    diemChuyenCan FLOAT,
    diemThaiDo FLOAT,
	diemChuyenMon FLOAT,
    diemHieuSuat FLOAT,
    bac CHAR,
    FOREIGN KEY (maCN) REFERENCES CongNhan(maCN)
);

CREATE TABLE KhachHang (
    maKH VARCHAR(50) PRIMARY KEY,
    tenKH NVARCHAR(50),
    toChuc BIT,
    email VARCHAR(50),
    dienThoai VARCHAR(20),
    hinhAnh VARBINARY(MAX)
);

CREATE TABLE HopDong (
    maHD VARCHAR(50) PRIMARY KEY,
    maKH VARCHAR(50),
    tenHD NVARCHAR(50),
    donGia FLOAT(10),
    ngayBatDau DATE,
    ngayKetThucDukien DATE,
    trangThai NVARCHAR(20),
    FOREIGN KEY (maKH) REFERENCES KhachHang(maKH)
);

CREATE TABLE SanPham (
    maSP VARCHAR(50) PRIMARY KEY,
    maHD VARCHAR(50),
    tenSP NVARCHAR(50),
    soLuong INT,
    donGia FLOAT(10),
    hinhAnh VARBINARY(MAX),
    FOREIGN KEY (maHD) REFERENCES HopDong(maHD)
);

CREATE TABLE CongDoan (
    maCD VARCHAR(50) PRIMARY KEY,
    maSP VARCHAR(50),
	maBP VARCHAR(50),
    tenCD NVARCHAR(50),
    donGia FLOAT(10),
    trangThai BIT,
	soLuongChuanBi int, 
	soLuongChuanBiToiThieu int, 
	soLuongHoanThanh int, 
    FOREIGN KEY (maSP) REFERENCES SanPham(maSP),
	FOREIGN KEY (maBP) REFERENCES BoPhan(maBP)
);

CREATE TABLE CongDoanTienQuyet (
    maCDTQ VARCHAR(50),
	maCD VARCHAR(50),
	PRIMARY KEY (maCD, maCDTQ),
    FOREIGN KEY (maCDTQ) REFERENCES CongDoan(maCD),
    FOREIGN KEY (maCD) REFERENCES CongDoan(maCD)
);

CREATE TABLE ChiTietPhanCong (
	maCTPC VARCHAR(50),
    maCD VARCHAR(50),
    maCN VARCHAR(50),
    ngay DATE,
    soLuongCDGiao INT,
    PRIMARY KEY (maCTPC),
    FOREIGN KEY (maCD) REFERENCES CongDoan(maCD),
    FOREIGN KEY (maCN) REFERENCES CongNhan(maCN)
);

CREATE TABLE PhieuLuongNhanVien (
	maPL VARCHAR(50) PRIMARY KEY,
    thang INT,
    nam INT,
    maNV VARCHAR(50),
    luong FLOAT(10),
    thuong FLOAT(10),
	phat FLOAT(10),
	phuCap FLOAT(10),
    soNgayLam INT,
    luongThucNhan FLOAT(10),
    FOREIGN KEY (maNV) REFERENCES NhanVien(maNV),
);

CREATE TABLE PhieuLuongCongNhan (
	maPL VARCHAR(50) PRIMARY KEY,
    thang INT,
    nam INT,
    maCN VARCHAR(50),
    luong FLOAT(10),
    thuong FLOAT(10),
	phat FLOAT(10),
    soNgayLam INT,
    luongThucNhan FLOAT(10),
    FOREIGN KEY (maCN) REFERENCES CongNhan(maCN)
);

CREATE TABLE PhieuChamCongCongNhan (
	maPCCCN VARCHAR(50) PRIMARY KEY,
	maCTPC VARCHAR(50), 
	ngayChamCong DATE,
	vang BIT,
    soLuongCD INT,
    soLuongCDTangCa INT,	
    noiDungPhat NVARCHAR(255),
	tienCong FLOAT(10),
    tienPhat FLOAT(10),
	tienThuong FLOAT(10),
	FOREIGN KEY (maCTPC) REFERENCES ChiTietPhanCong(maCTPC)
);

