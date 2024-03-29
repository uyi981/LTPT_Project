/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ptud.Entity;

/**
 *
 * @author vohau
 */
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import javax.swing.JOptionPane;
import ptud.DAO.DAO_SanPham;
// lưu ý khi đổ dữ liệu vào table thì phải set oldMaHD = maHD nằm cuối danh sách.
public class HopDong {       
    // khai bao thuoc tinh
   private String maHD;
   private String tenHD;
   private LocalDate ngayBatDau;
   private LocalDate ngayKetThucDuKien;
   private String trangThai;
   public static String oldMaHD = null;
   private double donGia; 
   private String maKH; 

    public int getSoLuongSanPham() {
        return soLuongSanPham;
    }

    public void setSoLuongSanPham() 
    {
       
       soLuongSanPham =  sanPhams.size();
    }
   private int soLuongSanPham;
   ArrayList<SanPham> sanPhams = new ArrayList<>();

    public ArrayList<SanPham> getSanPhams() {
        return sanPhams;
    }

    public void setSanPhams(ArrayList<SanPham> sanPhams) {
        this.sanPhams = sanPhams;
    }
    DAO_SanPham daosp = null;
   
   
    public void setMaKH(String maKH) {
        if (maKH.trim().equals("")) 
        {
            maKH = "Khong xac dinh!";
        }
        this.maKH = maKH;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public void setMaHD(int stt) {
        maHD = generateMaHD(stt);
    }

    public void setTenHD(String tenHD) {
        if (tenHD.trim().equals("")) {
            this.tenHD = "Không xác định";
        } else {
            this.tenHD = tenHD;
        }
    }

    public void setNgayBatDau(LocalDate ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public void setNgayKetThucDuKien(LocalDate ngayKetThucDuKien) {
        this.ngayKetThucDuKien = ngayKetThucDuKien;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
      public void xacNhanHopDong(String trangThai) 
    {
        this.trangThai = trangThai;
    }

    String generateMaHD(int stt) {
        
                if (stt <= 9) 
                {
                    maHD = generateNgayBatDau() + "0" + stt;
                } 
                else 
                {
                    maHD = generateNgayBatDau() + stt;
                }

        return maHD;
    }

    public String getMaKH() {
        return maKH;
    }

    public double getDonGia() {
        return donGia;
    }

    public String getDonGiaString() 
    {
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat vn = NumberFormat.getInstance(localeVN);   
        return vn.format(donGia);
    }

    public void setDonGia(double donGia) {
        if (donGia < 0) {
            this.donGia = 0;
        } else {
            this.donGia = donGia;
        }
    }

    String generateNgayBatDau() {
        int day = ngayBatDau.getDayOfMonth();
        int month = ngayBatDau.getMonthValue();
        int year = ngayBatDau.getYear();
        if (day<=9) {
            return "0" + day + month + "0" + year;
        } 
        else
        {
            return "" + day +""+ month +""+ year;
        }

    }

    public LocalDate getNgayBatDau() {
        return ngayBatDau;
    }

    public LocalDate getNgayKetThuc() {
        return ngayKetThucDuKien;
    }

    public String getNgayBatDauString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return ngayBatDau.format(formatter);
    }

    public String getNgayKetThucString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return ngayKetThucDuKien.format(formatter);
    }

    public String getMaHD() {
        return maHD;
    }

    public String getTenHD() {
        return tenHD;
    }

    public LocalDate getNgayKetThucDuKien() {
        return ngayKetThucDuKien;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public HopDong(int stt, String tenHD, LocalDate ngayBatDau, LocalDate ngayKetThucDuKien, double donGia, String maKH,String trangThai) {
        super();
        this.setNgayBatDau(ngayBatDau);
        this.setNgayKetThucDuKien(ngayKetThucDuKien);
        this.setTenHD(tenHD);
        this.setTrangThai(trangThai);
        this.setDonGia(donGia);
        this.setMaHD(stt);
        this.setMaKH(maKH);
    }

    public void updateListSanPham() {
        daosp = new DAO_SanPham();
        sanPhams = null;
        sanPhams = new ArrayList<>();
        for (SanPham sanPham : daosp.getAll()) {
            if (sanPham.getMaHD().compareToIgnoreCase(this.maHD) == 0) {  
                if(!sanPhams.contains(sanPham))
                {
                     sanPhams.add(sanPham);
                }              
            }
        }
    }

    public HopDong(String maHD, String tenHD, LocalDate ngayBatDau, LocalDate ngayKetThucDuKien, double donGia, String maKH,String trangThai) {
        super();
        this.setNgayBatDau(ngayBatDau);
        this.setNgayKetThucDuKien(ngayKetThucDuKien);
        this.setTenHD(tenHD);
        this.setTrangThai(trangThai);
        this.setDonGia(donGia);
        this.setMaHD(maHD);
        this.setMaKH(maKH);
    }

    public String ToString() {
        String a = "";
        a += this.getMaHD() + "\n";
        a += this.getTenHD() + "\n";
        a += this.getNgayBatDau() + "\n";
        a += this.getNgayKetThuc() + "\n";
        a += this.getTrangThai() + "\n";
        a += this.getDonGiaString() + "\n";
        return a;
    }

    public HopDong() {

    }

    public void setNgayBatDau(String ngaybdString) {
        int day, month, year;
        day = Integer.parseInt(ngaybdString.substring(0, 2));
        month = Integer.parseInt(ngaybdString.substring(3, 5));
        year = Integer.parseInt(ngaybdString.substring(6, 10));
        setNgayBatDau(LocalDate.of(year, month, day));
    }

    public void setNgayKetThuc(String ngayKT) {
        int day, month, year;
        day = Integer.parseInt(ngayKT.substring(0, 2));
        month = Integer.parseInt(ngayKT.substring(3, 5));
        year = Integer.parseInt(ngayKT.substring(6, 10));
        setNgayKetThucDuKien(LocalDate.of(year, month, day));
    }
    public void setDonGia(String donGiaString)
    {
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat vn = NumberFormat.getInstance(localeVN);   
        try 
        {
         double donGia = Float.parseFloat(vn.parse(donGiaString).toString());
         setDonGia(donGia);
        } 
        catch (Exception e) 
        {
            
        }            
    }
}
