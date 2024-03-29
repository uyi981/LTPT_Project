package ptud.Entity;

import ptud.Entity.CongNhan;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author TomTom
 */
public class BangDanhGiaCongNhan {

    private String id;
    private CongNhan congNhan;
    private int nam;
    private float diemChuyenCan;
    private float diemChuyenMon;
    private float diemThaiDo;
    private float diemHieuSuat;
    private char bac;

    public String getId() {
        return id;
    }

    public CongNhan getCongNhan() {
        return congNhan;
    }

    public int getNam() {
        return nam;
    }

    public float getDiemChuyenCan() {
        return diemChuyenCan;
    }

    public float getDiemchuyenMon() {
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

    public void setId(String id) {
        this.id = id;
    }

    public void setThanhVien(CongNhan congNhan) {
        this.congNhan = congNhan;
    }

    public void setNam(int nam) {
        this.nam = nam;
    }

    public void setDiemChuyenCan(float diemChuyenCan) {
        this.diemChuyenCan = diemChuyenCan;
    }

    public void setDiemchuyenMon(float diemchuyenMon) {
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

    public BangDanhGiaCongNhan() {
    }

    public BangDanhGiaCongNhan(CongNhan congNhan, int nam, float diemChuyenCan, float diemChuyenMon, float diemThaiDo, float diemHieuSuat) {
        this.id = genMaBDG(congNhan.getMaCN(), nam);
        this.congNhan = congNhan;
        this.nam = nam;
        this.diemChuyenCan = diemChuyenCan;
        this.diemChuyenMon = diemChuyenMon;
        this.diemThaiDo = diemThaiDo;
        this.diemHieuSuat = diemHieuSuat;
        tinhBac();
    }

    public BangDanhGiaCongNhan(String id, CongNhan congNhan, int nam, float diemChuyenCan, float diemchuyenMon, float diemThaiDo, float diemHieuSuat, char bac) {
        this.id = id;
        this.congNhan = congNhan;
        this.nam = nam;
        this.diemChuyenCan = diemChuyenCan;
        this.diemChuyenMon = diemchuyenMon;
        this.diemThaiDo = diemThaiDo;
        this.diemHieuSuat = diemHieuSuat;
        this.bac = bac;
    }

    public BangDanhGiaCongNhan(CongNhan congNhan, int nam, float diemChuyenCan, float diemchuyenMon, float diemThaiDo, float diemHieuSuat, char bac) {
        this.id = genMaBDG(congNhan.getMaCN(), nam);
        this.congNhan = congNhan;
        this.nam = nam;
        this.diemChuyenCan = diemChuyenCan;
        this.diemChuyenMon = diemchuyenMon;
        this.diemThaiDo = diemThaiDo;
        this.diemHieuSuat = diemHieuSuat;
        this.bac = bac;
    }

    private String genMaBDG(String maCongNhan, int nam) {
        return maCongNhan + nam;
    }

    @Override
    public String toString() {
        return "BangDanhGia{" + "id=" + id + ", congNhan=" + congNhan + ", nam=" + nam + ", diemChuyenCan=" + diemChuyenCan + ", diemchuyenMon=" + diemChuyenMon + ", diemThaiDo=" + diemThaiDo + ", diemHieuSuat=" + diemHieuSuat + ", bac=" + bac + '}';
    }
}
