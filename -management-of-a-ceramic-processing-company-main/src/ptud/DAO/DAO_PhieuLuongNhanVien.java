/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ptud.DAO;

import ptud.Entity.PhieuLuongNhanVien;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import static ptud.Mang.connection;

/**
 *
 * @author KHANH PC
 */
public class DAO_PhieuLuongNhanVien implements DAOInterface<PhieuLuongNhanVien> {

    
    public static DAO_PhieuLuongNhanVien getInstance() {
        return new DAO_PhieuLuongNhanVien();
    }
    
    
    @Override
    public PhieuLuongNhanVien get(String id) {
        // let code to get PhieuLuongNhanVien t from database
        PhieuLuongNhanVien phieuLuongNhanVien = null;
        try {
            String query = "SELECT * FROM PhieuLuongNhanVien WHERE maPL = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                String maPL = resultSet.getString("maPL");
                int thang = resultSet.getInt("thang");
                int nam = resultSet.getInt("nam");
                String maNV = resultSet.getString("maNV");
                double phat = resultSet.getDouble("phat");
                
                phieuLuongNhanVien = new PhieuLuongNhanVien(maPL, thang, nam, maNV, phat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return phieuLuongNhanVien;
    }

    @Override
    public ArrayList<PhieuLuongNhanVien> getAll() {
        // let code to get all PhieuLuongNhanVien from database
        ArrayList<PhieuLuongNhanVien> phieuLuongNhanViens = new ArrayList<>();
        try {
            String query = "SELECT * FROM PhieuLuongNhanVien";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                String maPL = resultSet.getString("maPL");
                int thang = resultSet.getInt("thang");
                int nam = resultSet.getInt("nam");
                String maNV = resultSet.getString("maNV");
                double phat = resultSet.getDouble("phat");
                
                PhieuLuongNhanVien phieuLuongNhanVien = new PhieuLuongNhanVien(maPL, thang, nam, maNV, phat);
                phieuLuongNhanViens.add(phieuLuongNhanVien);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return phieuLuongNhanViens;
    }

    @Override
    public boolean insert(PhieuLuongNhanVien t) {
        //let code to insert PhieuLuongNhanVien t to database sqlserver 
        try {
            // Create a PreparedStatement to insert the data  
            String query = "INSERT INTO PhieuLuongNhanVien (maPL, thang, nam, maNV, luong, thuong, phat, phuCap, soNgayLam, luongThucNhan) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            
            // Set the values of the parameters
            statement.setString(1, t.getMaPL());
            statement.setInt(2, t.getThang());
            statement.setInt(3, t.getNam());
            statement.setString(4, t.getMaNV());
            statement.setDouble(5, t.getLuong());
            statement.setDouble(6, t.getThuong());
            statement.setDouble(7, t.getPhat());
            statement.setDouble(8, t.getPhuCap());
            statement.setInt(9, t.getSoNgayLam());
            statement.setDouble(10, t.getLuongThucNhan());

            
            // Execute the query
            int rowsAffected = statement.executeUpdate();
          
            // Check if the insert was successful
            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    @Override
    public boolean update(PhieuLuongNhanVien t) {
        try {
            // Create a PreparedStatement to update the data  
            String query = "UPDATE PhieuLuongNhanVien SET thang = ?, nam = ?, maNV = ?, luong = ?, thuong = ?, phat = ?, phuCap = ?, soNgayLam = ?, luongThucNhan = ? WHERE maPL = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            
            // Set the values of the parameters
            statement.setInt(1, t.getThang());
            statement.setInt(2, t.getNam());
            statement.setString(3, t.getMaNV());
            statement.setDouble(4, t.getLuong());
            statement.setDouble(5, t.getThuong());
            statement.setDouble(6, t.getPhat());
            statement.setDouble(7, t.getPhuCap());
            statement.setInt(8, t.getSoNgayLam());
            statement.setDouble(9, t.getLuongThucNhan());
            statement.setString(10, t.getMaPL());

            
            // Execute the query
            int rowsAffected = statement.executeUpdate();
          
            // Check if the update was successful
            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    @Override
    public boolean deleteById(String id) {
        // let code to delete PhieuLuongNhanVien t from database
        try {
            // Create a PreparedStatement to delete the data  
            String query = "DELETE FROM PhieuLuongNhanVien WHERE maPL = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            
            // Set the value of the parameter
            statement.setString(1, id);
            
            // Execute the query
            int rowsAffected = statement.executeUpdate();
          
            // Check if the delete was successful
            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<PhieuLuongNhanVien> getAllByMaPBThangNam(String maBP, int thang, int nam) {

        ArrayList<PhieuLuongNhanVien> phieuLuongNhanViens = new ArrayList<>();
        try {
            String query = "SELECT * FROM PhieuLuongNhanVien WHERE maNV IN (SELECT maNV FROM NhanVien WHERE maBP = ?) AND thang = ? AND nam = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, maBP);
            statement.setInt(2, thang);
            statement.setInt(3, nam);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                String maPL = resultSet.getString("maPL");
                int thang1 = resultSet.getInt("thang");
                int nam1 = resultSet.getInt("nam");
                String maNV = resultSet.getString("maNV");
                double phat = resultSet.getDouble("phat");
                
                PhieuLuongNhanVien phieuLuongNhanVien = new PhieuLuongNhanVien(maPL, thang1, nam1, maNV, phat);
                phieuLuongNhanViens.add(phieuLuongNhanVien);
            }
            return phieuLuongNhanViens;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<PhieuLuongNhanVien>();
    }

    public ArrayList<PhieuLuongNhanVien> getAllByThangNam(int thang, int nam) {
        ArrayList<PhieuLuongNhanVien> phieuLuongNhanViens = new ArrayList<>();
        try {
            String query = "SELECT * FROM PhieuLuongNhanVien WHERE thang = ? AND nam = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, thang);
            statement.setInt(2, nam);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                String maPL = resultSet.getString("maPL");
                int thang1 = resultSet.getInt("thang");
                int nam1 = resultSet.getInt("nam");
                String maNV = resultSet.getString("maNV");
                double phat = resultSet.getDouble("phat");
                
                PhieuLuongNhanVien phieuLuongNhanVien = new PhieuLuongNhanVien(maPL, thang1, nam1, maNV, phat);
                phieuLuongNhanViens.add(phieuLuongNhanVien);
            }
            return phieuLuongNhanViens;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<PhieuLuongNhanVien>();
    }
}


    
