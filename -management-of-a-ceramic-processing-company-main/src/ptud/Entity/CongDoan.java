/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ptud.Entity;

import java.util.ArrayList;
import java.util.Objects;

import ptud.DAO.DAO_ChiTietPhanCong;
import ptud.DAO.DAO_CongDoan;
import ptud.DAO.DAO_SanPham;

/**
 *  
 * @author KHANH PC
 */
public class CongDoan {
    private String maCD; 
    private String maSP; 
    private String maBP;
    private String tenCD; 
    private double donGia; 
    private boolean trangThai; 
    private int soLuongChuanBiToiThieu;    
    // private int soLuongChuanBi;     
    // private int soLuongHoanThanh; 
    private ArrayList<String> dsCDTQ; 

    public CongDoan() {
    }

    public CongDoan(String maCD, String maSP, String maBP, String tenCD, double donGia, boolean trangThai, int soLuongChuanBiToiThieu, ArrayList<String> dsCDTQ) {
        setMaCD(maCD);
        setMaSP(maSP);
        setMaBP(maBP);
        setTenCD(tenCD);
        setDonGia(donGia);
        setTrangThai(trangThai);
        setSoLuongChuanBiToiThieu(soLuongChuanBiToiThieu);
        setDsCDTQ(dsCDTQ);
    }

    public String getMaCD() {
        return maCD;
    }

    public String getMaSP() {
        return maSP;
    }

    public String getMaBP() {
        return maBP;
    }

    public String getTenCD() {
        return tenCD;
    }

    public double getDonGia() {
        return donGia;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public int getSoLuongChuanBi() {
        int soLuongChuanBi = 0;

        DAO_CongDoan dao = DAO_CongDoan.getInstance();
        if( dao.getDsCDTQ(maCD).isEmpty() ) {
            // nếu không có cđtq, số lượng chuẩn bị là slsp
            SanPham sp = DAO_SanPham.getInstance().get(this.maSP);
            soLuongChuanBi=sp.getSoLuong(); 
        } else {
            // Lấy số lượng hoàn thành nhỏ nhất của công đoạn tiên quyết làm số lượng chuẩn bị
            ArrayList<CongDoan> dsCDTQ = dao.getDsCDTQ(maCD);
            int sum = 99999;
            for (CongDoan cd : dsCDTQ) {
                int slht = cd.getSoLuongHoanThanh(); 
                sum = Math.min(sum, slht);
            }
            soLuongChuanBi=sum; 
        }

        // trừ đi số lượng đã hoàn thành
        soLuongChuanBi-=this.getSoLuongHoanThanh();

        // trừ đi số lượng đã được giao trong hôm nay
        soLuongChuanBi-=DAO_ChiTietPhanCong.getInstance().getSoLuongCongDoanDuocGiaoHomNay(this.maCD); 

        return soLuongChuanBi;
    }

    public int getSoLuongChuanBiToiThieu() {
        return soLuongChuanBiToiThieu;
    }

    public int getSoLuongHoanThanh() {
        int soLuongHoanThanh = 0;
        // xử lý tính toán 
        soLuongHoanThanh = DAO_CongDoan.getInstance().getSoLuongHoanThanh(this.maCD);
        return soLuongHoanThanh;
    }

    public ArrayList<String> getDsCDTQ() {
        return dsCDTQ;
    }

    public void setMaCD(String maCD) {
        this.maCD = maCD;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public void setMaBP(String maBP) {
        this.maBP = maBP;
    }

    public void setTenCD(String tenCD) {
        this.tenCD = tenCD;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public void setSoLuongChuanBiToiThieu(int soLuongChuanBiToiThieu) {
        this.soLuongChuanBiToiThieu = soLuongChuanBiToiThieu;
    }


    public void setDsCDTQ(ArrayList<String> dsCDTQ) {
        this.dsCDTQ = dsCDTQ;
    }
    
    public void addCDTQ(String maCDTQ) {
        if (dsCDTQ == null) {
            dsCDTQ = new ArrayList<String>();
        }
        dsCDTQ.add(maCDTQ);
    }

    public void deleteCDTQ(String maCDTQ) {
        if (dsCDTQ != null) {
            dsCDTQ.remove(maCDTQ);
        }
    }

    public void updateCDTQ(String maCDTQ, String maCDTQNew) {
        if (dsCDTQ != null) {
            int index = dsCDTQ.indexOf(maCDTQ);
            if (index != -1) {
                dsCDTQ.set(index, maCDTQNew);
            }
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.maCD);
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
        final CongDoan other = (CongDoan) obj;
        return Objects.equals(this.maCD, other.maCD);
    }
    
    
    
}
