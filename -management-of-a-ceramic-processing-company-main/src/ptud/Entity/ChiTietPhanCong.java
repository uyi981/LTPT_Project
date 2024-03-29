package ptud.Entity;

import java.time.LocalDate;
import java.util.Objects;

/**
 * 
 * @author KHANH
 */
public class ChiTietPhanCong {
    private String maCTPC; 
    private String maCD;    
    private String maCN;
    private LocalDate ngay;
    private int soLuongCDGiao;

    public ChiTietPhanCong(){}; 

    public ChiTietPhanCong(String maCTPC, String maCD, String maCN, LocalDate ngay, int soLuongCDGiao) {
        setMaCD(maCD);
        setMaCN(maCN);
        setMaCTPC(maCTPC);
        setNgay(ngay);
        setSoLuongCDGiao(soLuongCDGiao);
    }

    public String getMaCTPC() {
        return maCTPC;
    }

    public void setMaCTPC(String maCTPC) {
        this.maCTPC = maCTPC;
    }

    public String getMaCD() {
        return maCD;
    }

    public void setMaCD(String maCD) {
        this.maCD = maCD;
    }

    public String getMaCN() {
        return maCN;
    }

    public void setMaCN(String maCN) {
        this.maCN = maCN;
    }

    public LocalDate getNgay() {
        return ngay;
    }

    public void setNgay(LocalDate ngay) {
        this.ngay = ngay;
    }

    public int getSoLuongCDGiao() {
        return soLuongCDGiao;
    }

    public void setSoLuongCDGiao(int soLuongCDGiao) {
        this.soLuongCDGiao = soLuongCDGiao;
    }

    @Override
    public String toString() {
        return "ChiTietPhanCong{" +
                "maCTPC='" + maCTPC + '\'' +
                ", maCD='" + maCD + '\'' +
                ", maCN='" + maCN + '\'' +
                ", ngay=" + ngay +
                ", soLuongCDGiao=" + soLuongCDGiao +
                '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + Objects.hashCode(this.maCTPC);
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
        final ChiTietPhanCong other = (ChiTietPhanCong) obj;
        return Objects.equals(this.maCTPC, other.maCTPC);
    }

    
}
    


