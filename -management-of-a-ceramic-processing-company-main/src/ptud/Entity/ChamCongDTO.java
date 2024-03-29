/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ptud.Entity;

/**
 *
 * @author KHANH PC
 */
public class ChamCongDTO {
    private String maCD;
    private int tongSoLuongCD;
    private int tongSoLuongCDTangCa;

    public ChamCongDTO(String maCD, int tongSoLuongCD, int tongSoLuongCDTangCa) {
        this.maCD = maCD;
        this.tongSoLuongCD = tongSoLuongCD;
        this.tongSoLuongCDTangCa = tongSoLuongCDTangCa;
    }

    public ChamCongDTO() {
    }
    
    public String getMaCD() {
        return maCD;
    }

    public void setMaCD(String maCD) {
        this.maCD = maCD;
    }

    public int getTongSoLuongCD() {
        return tongSoLuongCD;
    }

    public void setTongSoLuongCD(int tongSoLuongCD) {
        this.tongSoLuongCD = tongSoLuongCD;
    }

    public int getTongSoLuongCDTangCa() {
        return tongSoLuongCDTangCa;
    }

    public void setTongSoLuongCDTangCa(int tongSoLuongCDTangCa) {
        this.tongSoLuongCDTangCa = tongSoLuongCDTangCa;
    }
    
}
