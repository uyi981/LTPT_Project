/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ptud.Entity;

/**
 *
 * @author TomTom
 */
public class BangDanhGiaNhanVien {

    private String id;
    private NhanVien nhanVien;
    private int nam;
    private float diemChuyenCan;
    private float diemChuyenMon;
    private float diemThaiDo;
    private float diemHieuSuat;
    private char bac;

    public String getId() {
        return id;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public int getNam() {
        return nam;
    }

    public float getDiemChuyenCan() {
        return diemChuyenCan;
    }

    public float getDiemChuyenMon() {
        return diemChuyenMon;
    }

    public float getDiemThaiDo() {
        return diemThaiDo;
    }

    public float getDiemHieuSuat() {
        return diemHieuSuat;
    }

    public char getBac() {
        return bac;
    }

    public void setId(String maBDGid) {
        this.id = id;
    }

    public void setNhanVien(NhanVien thanhVien) {
        this.nhanVien = thanhVien;
    }

    public void setNam(int nam) {
        this.nam = nam;
    }

    public void setDiemChuyenCan(float diemChuyenCan) {
        this.diemChuyenCan = diemChuyenCan;
    }

    public void setDiemChuyenMon(float diemchuyenMon) {
        this.diemChuyenMon = diemchuyenMon;
    }

    public void setDiemThaiDo(float diemThaiDo) {
        this.diemThaiDo = diemThaiDo;
    }

    public void setDiemHieuSuat(float diemHieuSuat) {
        this.diemHieuSuat = diemHieuSuat;
    }

    public float tinhTongDiem() {
        return this.diemChuyenCan + this.diemHieuSuat + this.diemThaiDo + this.diemChuyenMon;
    }

    public void tinhBac() {
        float tong = tinhTongDiem();
        if (tong < 20) {
            this.bac = 'D';
        } else if (tong < 25) {
            this.bac = 'C';
        } else if (tong < 35) {
            this.bac = 'B';
        } else {
            this.bac = 'A';
        }
    }

    public BangDanhGiaNhanVien() {
    }

    public BangDanhGiaNhanVien(String id, NhanVien nhanVien, int nam, float diemChuyenCan, float diemChuyenMon, float diemThaiDo, float diemHieuSuat, char bac) {
        this.id = id;
        this.nhanVien = nhanVien;
        this.nam = nam;
        this.diemChuyenCan = diemChuyenCan;
        this.diemChuyenMon = diemChuyenMon;
        this.diemThaiDo = diemThaiDo;
        this.diemHieuSuat = diemHieuSuat;
        this.bac = bac;
    }

    public BangDanhGiaNhanVien(NhanVien nhanVien, int nam, float diemChuyenCan, float diemChuyenMon, float diemThaiDo, float diemHieuSuat, char bac) {
        this.id = genMaBDG(nhanVien.getMaNV(), nam);
        this.nhanVien = nhanVien;
        this.nam = nam;
        this.diemChuyenCan = diemChuyenCan;
        this.diemChuyenMon = diemChuyenMon;
        this.diemThaiDo = diemThaiDo;
        this.diemHieuSuat = diemHieuSuat;
        this.bac = bac;
    }

    public BangDanhGiaNhanVien(NhanVien nhanVien, int nam, float diemChuyenCan, float diemChuyenMon, float diemThaiDo, float diemHieuSuat) {
        this.id = genMaBDG(nhanVien.getMaNV(), nam);
        this.nhanVien = nhanVien;
        this.nam = nam;
        this.diemChuyenCan = diemChuyenCan;
        this.diemChuyenMon = diemChuyenMon;
        this.diemThaiDo = diemThaiDo;
        this.diemHieuSuat = diemHieuSuat;
        tinhBac();
    }

    private String genMaBDG(String maNhanVien, int nam) {
        return maNhanVien + nam;
    }

    @Override
    public String toString() {
        return "BangDanhGia{" + "id=" + id + ", nhanVien=" + nhanVien + ", nam=" + nam + ", diemChuyenCan=" + diemChuyenCan + ", diemchuyenMon=" + diemChuyenMon + ", diemThaiDo=" + diemThaiDo + ", diemHieuSuat=" + diemHieuSuat + ", bac=" + bac + '}';
    }

}
