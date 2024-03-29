/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ptud.Entity;

import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author TranLoc
 */
public class PhieuChamCongCongNhan {
    private String maPCCCN;
    private LocalDate ngay;
    private boolean vang;
    private int soLuongSanPham;
    private String maCTPC;
    private String noiDungPhat;
    private double tienCong;
    private double tienThuong;
    private double tienPhat;
    private int soLuongSanPhamTangCa;

    public PhieuChamCongCongNhan() {
    }

    public PhieuChamCongCongNhan(String maPCCCN, String maCN, LocalDate ngay, boolean vang, int soLuongSanPham, String maCTPC, String noiDungPhat, double tienCong, double tienThuong, double tienPhat, int soLuongSanPhamTangCa) {
        this.maPCCCN = maPCCCN;
        this.ngay = ngay;
        this.vang = vang;
        this.soLuongSanPham = soLuongSanPham;
        this.maCTPC = maCTPC;
        this.noiDungPhat = noiDungPhat;
        this.tienCong = tienCong;
        this.tienThuong = tienThuong;
        this.tienPhat = tienPhat;
        this.soLuongSanPhamTangCa = soLuongSanPhamTangCa;
    }

    public String getMaPCCCN() {
        return maPCCCN;
    }

    public void setMaPCCCN(String maPCCCN) {
        this.maPCCCN = maPCCCN;
    }

    

    public boolean isVang() {
        return vang;
    }

    public void setVang(boolean vang) {
        this.vang = vang;
    }

    public LocalDate getNgay() {
        return ngay;
    }

    public void setNgay(LocalDate ngay) {
        this.ngay = ngay;
    }

    public int getSoLuongSanPham() {
        return soLuongSanPham;
    }

    public void setSoLuongSanPham(int soLuongSanPham) {
        this.soLuongSanPham = soLuongSanPham;
    }

    public String getMaCTPC() {
        return maCTPC;
    }

    public void setMaCTPC(String maCTPC) {
        this.maCTPC = maCTPC;
    }

    public String getNoiDungPhat() {
        return noiDungPhat;
    }

    public void setNoiDungPhat(String noiDungPhat) {
        this.noiDungPhat = noiDungPhat;
    }

    public double getTienCong() {
        return tienCong;
    }

    public void setTienCong(double tienCong) {
        this.tienCong = tienCong;
    }

    public double getTienThuong() {
        return tienThuong;
    }

    public void setTienThuong(double tienThuong) {
        this.tienThuong = tienThuong;
    }

    public double getTienPhat() {
        return tienPhat;
    }

    public void setTienPhat(double tienPhat) {
        this.tienPhat = tienPhat;
    }

    public int getSoLuongSanPhamTangCa() {
        return soLuongSanPhamTangCa;
    }

    public void setSoLuongSanPhamTangCa(int soLuongSanPhamTangCa) {
        this.soLuongSanPhamTangCa = soLuongSanPhamTangCa;
    }      

    @Override
    public String toString() {
        return "PhieuChamCongCongNhan{" + "maPCCCN=" + maPCCCN + ", ngay=" + ngay + ", vang=" + vang + ", soLuongSanPham=" + soLuongSanPham + ", maCTPC=" + maCTPC + ", noiDungPhat=" + noiDungPhat + ", tienCong=" + tienCong + ", tienThuong=" + tienThuong + ", tienPhat=" + tienPhat + ", soLuongSanPhamTangCa=" + soLuongSanPhamTangCa + '}';
    }
    
}
