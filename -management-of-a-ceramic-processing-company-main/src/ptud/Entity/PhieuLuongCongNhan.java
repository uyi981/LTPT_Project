/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ptud.Entity;

import java.sql.SQLException;
import java.util.Objects;

import ptud.DAO.DAO_PhieuChamCongCongNhan;

/**
 * 
 * @author KHANH PC
 */


public class PhieuLuongCongNhan {
    private String maPL;     
    private int thang; 
    private int nam; 
    private String maCN; 
    private double phat; 
    // private double luong; 
    // private double thuong; 
    // private int soNgayLam; 
    // private double luongThucNhan; 

    public PhieuLuongCongNhan() {
    }

    public PhieuLuongCongNhan(String maPL, int thang, int nam, String maCN, double phat) {
        setMaPL(maPL);
        setThang(thang);
        setNam(nam);
        setMaCN(maCN);
        setPhat(phat);
    }

    public String getMaPL() {
        return maPL;
    }

    public void setMaPL(String maPL) {
        this.maPL = maPL;
    }

    public int getThang() {
        return thang;
    }

    public void setThang(int thang) {
        this.thang = thang;
    }

    public int getNam() {
        return nam;
    }

    public void setNam(int nam) {
        this.nam = nam;
    }

    public String getMaCN() {
        return maCN;
    }

    public void setMaCN(String maCN) {
        this.maCN = maCN;
    }

    public double getPhat() {
        double phat2 = 0; 
        try {
            phat2 = DAO_PhieuChamCongCongNhan.getInstance().getTongTienPhatTrongThang(maCN, thang, nam);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // xử lý tính toán
        return phat2;
    }

    public void setPhat(double phat) {
        this.phat = phat;
    }
    

    public double getLuong() {
        double luong = 0;
        // xử lý tính toán
        try {
            luong = DAO_PhieuChamCongCongNhan.getInstance().getTongTienCongTrongThang(maCN, thang, nam);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return luong;
    }

    public double getThuong() {
        double thuong = 0;
        // xử lý tính toán
        try {
            thuong = DAO_PhieuChamCongCongNhan.getInstance().getTongTienCongTrongThang(maCN, thang, nam)*0.15;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        return thuong;
    }


    public int getSoNgayLam() {
        int soNgayLam = 0; 
        // xử lý tính toán
        try {
            soNgayLam = DAO_PhieuChamCongCongNhan.getInstance().getSoNgayLam(maCN,  thang, nam);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return soNgayLam;
    }

    public double getLuongThucNhan() {
        double luongThucNhan = 0;
        // xử lý tính toán
        try {
            luongThucNhan = DAO_PhieuChamCongCongNhan.getInstance().getTongTienCongTrongThang(maCN, thang, nam) + getThuong() - getPhat();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return luongThucNhan;
    }


    @Override
    public String toString() {
        return "PhieuLuong{" +
                "maPL='" + maPL + '\'' +
                ", thang=" + thang +
                ", nam=" + nam +
                ", maCN='" + maCN + '\'' +
                ", phat=" + phat +
                ", luong=" + getLuong() +
                ", thuong=" + getThuong() +
                ", soNgayLam=" + getSoNgayLam() +
                ", luongThucNhan=" + getLuongThucNhan() +
                '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + Objects.hashCode(this.maPL);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PhieuLuongCongNhan other = (PhieuLuongCongNhan) obj;
        return Objects.equals(this.maPL, other.maPL);
    }    
}
