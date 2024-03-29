/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ptud.Entity;

/**
 *
 * @author KHANH PC
 */
public class BoPhan {
    private String maBP; 
    private String tenBP; 

    public BoPhan() {
    }

    public BoPhan(String maBP, String tenBP) {
        this.maBP = maBP;
        this.tenBP = tenBP;
    }

    public String getMaBP() {
        return maBP;
    }

    public String getTenBP() {
        return tenBP;
    }

    public void setMaBP(String maBP) {
        this.maBP = maBP;
    }

    public void setTenBP(String tenBP) {
        this.tenBP = tenBP;
    }

    @Override
    public String toString() {
        return super.toString(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
}
